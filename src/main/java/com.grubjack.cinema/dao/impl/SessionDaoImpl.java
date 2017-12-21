package com.grubjack.cinema.dao.impl;

import com.grubjack.cinema.dao.SessionDao;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.DayOfWeek;
import com.grubjack.cinema.model.Session;
import com.grubjack.cinema.model.TimeOfDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.cinema.dao.DaoFactory.getConnection;

/**
 * Created by Urban Aleksandr
 */
public class SessionDaoImpl implements SessionDao {

    private static Logger log = LoggerFactory.getLogger(SessionDaoImpl.class);

    @Override
    public void create(Session session) throws DaoException {
        log.info("Creating new session");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO sessions (day, time, movie) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, session.getDayOfWeek().toString());
            statement.setString(2, session.getTimeOfDay().toString());
            statement.setString(3, session.getMovie());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                session.setId(resultSet.getInt(1));
                log.info("Session is created with id = " + session.getId());
            }
        } catch (SQLException e) {
            log.error("Can't create session", e);
            throw new DaoException("Can't create session", e);
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
    public void update(Session session) throws DaoException {
        log.info("Updating session with id " + session.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE sessions SET day=?, time=?, movie=? WHERE id=?");
            statement.setString(1, session.getDayOfWeek().toString());
            statement.setString(2, session.getTimeOfDay().toString());
            statement.setString(3, session.getMovie());
            statement.setInt(4, session.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't update session", e);
            throw new DaoException("Can't update session", e);
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
        log.info("Deleting session with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM sessions WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can't delete session", e);
            throw new DaoException("Can't delete session", e);
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
    public Session find(int id) throws DaoException {
        log.info("Finding session with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Session session = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM sessions WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                session = new Session();
                session.setId(id);
                session.setDayOfWeek(DayOfWeek.valueOf(resultSet.getString("day")));
                session.setTimeOfDay(TimeOfDay.convert(resultSet.getString("time")));
                session.setMovie(resultSet.getString("movie"));
            }
        } catch (SQLException e) {
            log.error("Can't find session", e);
            throw new DaoException("Can't find session", e);
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
        return session;
    }

    @Override
    public List<Session> findAll() throws DaoException {
        log.info("Finding all sessions");
        List<Session> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM sessions");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Session session = new Session();
                session.setId(resultSet.getInt("id"));
                session.setDayOfWeek(DayOfWeek.valueOf(resultSet.getString("day")));
                session.setTimeOfDay(TimeOfDay.convert(resultSet.getString("time")));
                session.setMovie(resultSet.getString("movie"));
                result.add(session);
            }
        } catch (SQLException e) {
            log.error("Can't find sessions", e);
            throw new DaoException("Can't find sessions", e);
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
    public List<Session> findByDay(DayOfWeek dayOfWeek) throws DaoException {
        log.info("Finding sessions by day " + dayOfWeek);
        List<Session> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM sessions WHERE day=?");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Session session = new Session();
                session.setId(resultSet.getInt("id"));
                session.setDayOfWeek(dayOfWeek);
                session.setTimeOfDay(TimeOfDay.convert(resultSet.getString("time")));
                session.setMovie(resultSet.getString("movie"));
                result.add(session);
            }
        } catch (SQLException e) {
            log.error("Can't find sessions", e);
            throw new DaoException("Can't find sessions", e);
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
    public List<Session> findByTime(TimeOfDay timeOfDay) throws DaoException {
        log.info("Finding sessions by time " + timeOfDay);
        List<Session> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM sessions WHERE time=?");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Session session = new Session();
                session.setId(resultSet.getInt("id"));
                session.setDayOfWeek(DayOfWeek.valueOf(resultSet.getString("day")));
                session.setTimeOfDay(timeOfDay);
                session.setMovie(resultSet.getString("movie"));
                result.add(session);
            }
        } catch (SQLException e) {
            log.error("Can't find sessions", e);
            throw new DaoException("Can't find sessions", e);
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
    public Session findByDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) throws DaoException {
        log.info(String.format("Finding sessions by day %s and time %s ", dayOfWeek, timeOfDay));
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Session session = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM sessions WHERE day=? AND time=?");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                session = new Session();
                session.setId(resultSet.getInt("id"));
                session.setDayOfWeek(dayOfWeek);
                session.setTimeOfDay(timeOfDay);
                session.setMovie(resultSet.getString("movie"));
            }
        } catch (SQLException e) {
            log.error("Can't find sessions", e);
            throw new DaoException("Can't find sessions", e);
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
        return session;
    }

    @Override
    public List<Session> findByMovie(String movie) throws DaoException {
        log.info(String.format("Finding sessions by '%s'" + movie));
        List<Session> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM sessions WHERE UPPER(movie) LIKE UPPER(?)");
            statement.setString(1, movie);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Session session = new Session();
                session.setId(resultSet.getInt("id"));
                session.setDayOfWeek(DayOfWeek.valueOf(resultSet.getString("day")));
                session.setTimeOfDay(TimeOfDay.convert(resultSet.getString("time")));
                session.setMovie(resultSet.getString("movie"));
                result.add(session);
            }
        } catch (SQLException e) {
            log.error("Can't find sessions", e);
            throw new DaoException("Can't find sessions", e);
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
