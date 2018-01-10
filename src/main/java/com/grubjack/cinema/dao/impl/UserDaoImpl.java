package com.grubjack.cinema.dao.impl;

import com.grubjack.cinema.dao.UserDao;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Role;
import com.grubjack.cinema.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;


/**
 * Created by Urban Aleksandr
 */
public class UserDaoImpl implements UserDao {

    private static Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
    private static final String CREATE_USER_SQL = "INSERT INTO users (firstname, lastname, email, password) VALUES (?,?,?,?)";
    private static final String CREATE_USER_ROLES_SQL = "INSERT INTO user_roles (role,user_id) VALUES (?,?)";
    private static final String UPDATE_USER_SQL = "UPDATE users SET firstname=?,lastname=?,email=?,password=? WHERE id=?";
    private static final String UPDATE_USER_TICKETS_SQL = "UPDATE tickets SET sold=?,user_id=? WHERE user_id=?";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id=?";
    private static final String FIND_USER_SQL = "SELECT * FROM users WHERE id=?";
    private static final String FIND_ALL_USER_SQL = "SELECT * FROM users LEFT JOIN user_roles ON users.id=user_roles.user_id ORDER BY users.firstname,users.lastname";
    private static final String FIND_USER_BY_EMAIL_SQL = "SELECT * FROM users LEFT JOIN user_roles ON users.id=user_roles.user_id WHERE LOWER(email) LIKE LOWER(?)";

    private DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(User user) throws DaoException {
        log.info("Creating new user");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(CREATE_USER_SQL, Statement.RETURN_GENERATED_KEYS);
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
            statement = connection.prepareStatement(CREATE_USER_ROLES_SQL);
            if (user.getRoles().size() > 0) {
                for (Role role : user.getRoles()) {
                    statement.setString(1, role.toString());
                    statement.setInt(2, user.getId());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
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
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(UPDATE_USER_SQL);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
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
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_USER_TICKETS_SQL);
            statement.setBoolean(1, false);
            statement.setNull(2, java.sql.Types.INTEGER);
            statement.setInt(3, id);
            statement.executeUpdate();
            statement = connection.prepareStatement(DELETE_USER_SQL);
            statement.setInt(1, id);
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("Can't rollback connection", e1);
            }
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
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(FIND_USER_SQL);
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
        Map<Integer, User> userById = new LinkedHashMap<>();
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(FIND_ALL_USER_SQL);
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
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(FIND_USER_BY_EMAIL_SQL);
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
