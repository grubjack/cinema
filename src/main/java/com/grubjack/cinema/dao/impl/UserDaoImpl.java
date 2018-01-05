package com.grubjack.cinema.dao.impl;

import com.grubjack.cinema.dao.UserDao;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Role;
import com.grubjack.cinema.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.grubjack.cinema.dao.DaoFactory.getConnection;


/**
 * Created by Urban Aleksandr
 */
public class UserDaoImpl implements UserDao {

    private static Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public void create(User user) throws DaoException {
        log.info("Creating new user");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("INSERT INTO users (firstname, lastname, email, password) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                log.info("User is created with id = " + user.getId());
            }
            statement = connection.prepareStatement("INSERT INTO user_roles (role,user_id) VALUES (?,?)");
            for (Role role : user.getRoles()) {
                statement.setString(1, role.toString());
                statement.setInt(2, user.getId());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            log.error("User with this email already exist", e);
            throw new DaoException("User with this email already exist", e);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("Can't rollback connection", e1);
            }
            log.error("Can't create user", e);
            throw new DaoException("Can't create user", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
    }

    @Override
    public void update(User user) throws DaoException {
        log.info("Updating user with id " + user.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE users SET firstname=?,lastname=?,email=?,password=? WHERE id=?");
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't update user", e);
            throw new DaoException("Can't update user", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        log.info("Deleting user with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can't delete user", e);
            throw new DaoException("Can't delete user", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
    }

    @Override
    public User find(int id) throws DaoException {
        log.info("Finding user with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setFirstName(resultSet.getString("firstname"));
                user.setLastName(resultSet.getString("lastname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            log.error("Can't find user", e);
            throw new DaoException("Can't find user", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
        return user;
    }

    @Override
    public List<User> findAll() throws DaoException {
        log.info("Finding all users with roles");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Map<Integer, User> userById = new HashMap<>();
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM users LEFT JOIN user_roles ON users.id=user_roles.user_id");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                User user = userById.get(id);
                String role = resultSet.getString("role");
                if (user == null) {
                    user = new User();
                    user.setId(id);
                    user.setFirstName(resultSet.getString("firstname"));
                    user.setLastName(resultSet.getString("lastname"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    userById.put(id, user);
                }
                if (role != null && !role.isEmpty()) {
                    user.addRole(Role.valueOf(role));
                }
            }
        } catch (SQLException e) {
            log.error("Can't find users", e);
            throw new DaoException("Can't find users", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
        return new ArrayList<>(userById.values());
    }

    @Override
    public User getByEmail(String email) throws DaoException {
        log.info("Finding user with email " + email);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Map<Integer, User> userById = new HashMap<>();
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM users LEFT JOIN user_roles ON users.id=user_roles.user_id WHERE LOWER(email) LIKE LOWER(?)");
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                User user = userById.get(id);
                String role = resultSet.getString("role");
                if (user == null) {
                    user = new User();
                    user.setId(id);
                    user.setFirstName(resultSet.getString("firstname"));
                    user.setLastName(resultSet.getString("lastname"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    userById.put(id, user);
                }
                if (role != null && !role.isEmpty()) {
                    user.addRole(Role.valueOf(role));
                }
            }
        } catch (SQLException e) {
            log.error("Can't find user with email", e);
            throw new DaoException("Can't find user with email", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("Can't close result set", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Can't close connection", e);
                }
            }
        }
        return userById.values().isEmpty() ? null : userById.values().iterator().next();
    }


}