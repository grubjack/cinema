package com.grubjack.cinema.dao.impl;

import com.grubjack.cinema.dao.RoleDao;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.grubjack.cinema.dao.DaoFactory.getConnection;

/**
 * Created by Urban Aleksandr
 */
public class RoleDaoImpl implements RoleDao {

    private static Logger log = LoggerFactory.getLogger(RoleDaoImpl.class);

    @Override
    public void create(Role role) throws DaoException {
        log.info("Creating new role");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO roles (title, description) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, role.getTitle());
            statement.setString(2, role.getDescription());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                role.setId(resultSet.getInt(1));
                log.info("Role is created with id = " + role.getId());
            }
        } catch (SQLException e) {
            log.error("Can't create role", e);
            throw new DaoException("Can't create role", e);
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
    public void update(Role role) throws DaoException {
        log.info("Updating role with id " + role.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE roles SET title=?,description=? WHERE id=?");
            statement.setString(1, role.getTitle());
            statement.setString(2, role.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't update role", e);
            throw new DaoException("Can't update role", e);
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
        log.info("Deleting role with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM roles WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can't delete role", e);
            throw new DaoException("Can't delete role", e);
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
    public Role find(int id) throws DaoException {
        log.info("Finding role with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Role role = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM roles WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                role = new Role();
                role.setId(id);
                role.setTitle(resultSet.getString("title"));
                role.setDescription(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            log.error("Can't find role", e);
            throw new DaoException("Can't find role", e);
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
        return role;
    }

    @Override
    public List<Role> findAll() throws DaoException {
        log.info("Finding all roles");
        List<Role> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM roles");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setTitle(resultSet.getString("title"));
                role.setDescription(resultSet.getString("description"));
                result.add(role);
            }
        } catch (SQLException e) {
            log.error("Can't find roles", e);
            throw new DaoException("Can't find roles", e);
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
        return result;
    }

    @Override
    public Set<Role> findByUser(int userId) throws DaoException {
        log.info("Finding all roles by user with id " + userId);
        Set<Role> result = new HashSet<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM roles WHERE user_id=?");
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setTitle(resultSet.getString("title"));
                role.setDescription(resultSet.getString("description"));
                result.add(role);
            }
        } catch (SQLException e) {
            log.error("Can't find roles", e);
            throw new DaoException("Can't find roles", e);
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
        return result;
    }
}
