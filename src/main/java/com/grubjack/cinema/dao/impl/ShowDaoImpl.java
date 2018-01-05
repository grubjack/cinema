package com.grubjack.cinema.dao.impl;

import com.grubjack.cinema.dao.ShowDao;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.DayOfWeek;
import com.grubjack.cinema.model.Show;
import com.grubjack.cinema.model.Ticket;
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
public class ShowDaoImpl implements ShowDao {

    private static Logger log = LoggerFactory.getLogger(ShowDaoImpl.class);

    @Override
    public void create(Show show) throws DaoException {
        log.info("Creating new show");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("INSERT INTO shows (day, time, movie) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, show.getDayOfWeek().toString());
            statement.setString(2, show.getTimeOfDay().toString());
            statement.setString(3, show.getMovie());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                show.setId(resultSet.getInt(1));
                log.info("Show is created with id = " + show.getId());
            }
            statement = connection.prepareStatement("INSERT INTO tickets (row, seat, price, show_id) VALUES (?,?,?,?)");
            for (Ticket ticket : show.getTickets()) {
                statement.setInt(1, ticket.getRow());
                statement.setInt(2, ticket.getSeat());
                statement.setInt(3, ticket.getPrice());
                statement.setInt(4, show.getId());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("Can't rollback connection", e1);
            }
            log.error("Can't create show", e);
            throw new DaoException("Can't create show", e);
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
    public void update(Show show) throws DaoException {
        log.info("Updating show with id " + show.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE shows SET day=?, time=?, movie=? WHERE id=?");
            statement.setString(1, show.getDayOfWeek().toString());
            statement.setString(2, show.getTimeOfDay().toString());
            statement.setString(3, show.getMovie());
            statement.setInt(4, show.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't update show", e);
            throw new DaoException("Can't update show", e);
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
        log.info("Deleting show with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM shows WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can't delete show", e);
            throw new DaoException("Can't delete show", e);
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
    public Show find(int id) throws DaoException {
        log.info("Finding show with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Show show = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM shows WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                show = new Show();
                show.setId(id);
                show.setDayOfWeek(DayOfWeek.valueOf(resultSet.getString("day")));
                show.setTimeOfDay(TimeOfDay.convert(resultSet.getString("time")));
                show.setMovie(resultSet.getString("movie"));
            }
        } catch (SQLException e) {
            log.error("Can't find show", e);
            throw new DaoException("Can't find show", e);
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
        return show;
    }

    @Override
    public List<Show> findAll() throws DaoException {
        log.info("Finding all shows");
        List<Show> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM shows ORDER BY day,time");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Show show = new Show();
                show.setId(resultSet.getInt("id"));
                show.setDayOfWeek(DayOfWeek.valueOf(resultSet.getString("day")));
                show.setTimeOfDay(TimeOfDay.convert(resultSet.getString("time")));
                show.setMovie(resultSet.getString("movie"));
                result.add(show);
            }
        } catch (SQLException e) {
            log.error("Can't find shows", e);
            throw new DaoException("Can't find shows", e);
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
    public List<Show> findByDay(DayOfWeek dayOfWeek) throws DaoException {
        log.info("Finding shows by day " + dayOfWeek);
        List<Show> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM shows WHERE day=?");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Show show = new Show();
                show.setId(resultSet.getInt("id"));
                show.setDayOfWeek(dayOfWeek);
                show.setTimeOfDay(TimeOfDay.convert(resultSet.getString("time")));
                show.setMovie(resultSet.getString("movie"));
                result.add(show);
            }
        } catch (SQLException e) {
            log.error("Can't find shows", e);
            throw new DaoException("Can't find shows", e);
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
    public List<Show> findByTime(TimeOfDay timeOfDay) throws DaoException {
        log.info("Finding shows by time " + timeOfDay);
        List<Show> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM shows WHERE time=?");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Show show = new Show();
                show.setId(resultSet.getInt("id"));
                show.setDayOfWeek(DayOfWeek.valueOf(resultSet.getString("day")));
                show.setTimeOfDay(timeOfDay);
                show.setMovie(resultSet.getString("movie"));
                result.add(show);
            }
        } catch (SQLException e) {
            log.error("Can't find shows", e);
            throw new DaoException("Can't find shows", e);
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
    public Show findByDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) throws DaoException {
        log.info("Finding shows by day {} and time {} ", dayOfWeek, timeOfDay);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Show show = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM shows WHERE day=? AND time=?");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                show = new Show();
                show.setId(resultSet.getInt("id"));
                show.setDayOfWeek(dayOfWeek);
                show.setTimeOfDay(timeOfDay);
                show.setMovie(resultSet.getString("movie"));
            }
        } catch (SQLException e) {
            log.error("Can't find shows", e);
            throw new DaoException("Can't find shows", e);
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
        return show;
    }

    @Override
    public List<Show> findByMovie(String movie) throws DaoException {
        log.info("Finding shows by '{}'" + movie);
        List<Show> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM shows WHERE UPPER(movie) LIKE UPPER(?)");
            statement.setString(1, movie);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Show show = new Show();
                show.setId(resultSet.getInt("id"));
                show.setDayOfWeek(DayOfWeek.valueOf(resultSet.getString("day")));
                show.setTimeOfDay(TimeOfDay.convert(resultSet.getString("time")));
                show.setMovie(resultSet.getString("movie"));
                result.add(show);
            }
        } catch (SQLException e) {
            log.error("Can't find shows", e);
            throw new DaoException("Can't find shows", e);
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
    public Show findByTicket(int ticketId) throws DaoException {
        log.info("Finding shows by ticket with id {}" + ticketId);

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Show show = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT s.id as id, s.day,s.time,s.movie,t.id as tid FROM shows s INNER JOIN tickets t ON s.id = t.show_id WHERE t.id=?");
            statement.setInt(1, ticketId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                show = new Show();
                show.setId(resultSet.getInt("id"));
                show.setDayOfWeek(DayOfWeek.valueOf(resultSet.getString("day")));
                show.setTimeOfDay(TimeOfDay.convert(resultSet.getString("time")));
                show.setMovie(resultSet.getString("movie"));
            }
        } catch (SQLException e) {
            log.error("Can't find show", e);
            throw new DaoException("Can't find show", e);
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
        return show;
    }
}
