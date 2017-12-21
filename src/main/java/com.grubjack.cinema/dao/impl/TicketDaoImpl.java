package com.grubjack.cinema.dao.impl;

import com.grubjack.cinema.dao.TicketDao;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.grubjack.cinema.dao.DaoFactory.getConnection;

/**
 * Created by Urban Aleksandr
 */
public class TicketDaoImpl implements TicketDao {

    private static Logger log = LoggerFactory.getLogger(TicketDaoImpl.class);

    @Override
    public void create(Ticket ticket, int sessionId) throws DaoException {
        log.info("Creating new ticket");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO tickets (row, seat, price, sold, session_id) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, ticket.getRow());
            statement.setInt(2, ticket.getSeat());
            statement.setInt(3, ticket.getPrice());
            statement.setBoolean(4, ticket.isSold());
            statement.setInt(5, sessionId);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                ticket.setId(resultSet.getInt(1));
                log.info("Ticket is created with id = " + ticket.getId());
            }
        } catch (SQLException e) {
            log.error("Can't create ticket", e);
            throw new DaoException("Can't create ticket", e);
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
    public void update(Ticket ticket, int sessionId) throws DaoException {
        log.info("Updating ticket with id " + ticket.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE tickets SET row=?, seat=?, price=?, sold=?, session_id=? WHERE id=?");
            statement.setInt(1, ticket.getRow());
            statement.setInt(2, ticket.getSeat());
            statement.setInt(3, ticket.getPrice());
            statement.setBoolean(4, ticket.isSold());
            statement.setInt(5, sessionId);
            statement.setInt(6, ticket.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't update ticket", e);
            throw new DaoException("Can't update ticket", e);
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
        log.info("Deleting ticket with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM tickets WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can't delete ticket", e);
            throw new DaoException("Can't delete ticket", e);
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
    public Ticket find(int id) throws DaoException {
        log.info("Finding ticket with id " + id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Ticket ticket = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM tickets WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ticket = new Ticket();
                ticket.setId(id);
                ticket.setRow(resultSet.getInt("row"));
                ticket.setSeat(resultSet.getInt("seat"));
                ticket.setPrice(resultSet.getInt("price"));
                ticket.setSold(resultSet.getBoolean("sold"));
            }
        } catch (SQLException e) {
            log.error("Can't find ticket", e);
            throw new DaoException("Can't find ticket", e);
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
        return ticket;
    }

    @Override
    public List<Ticket> findAll() throws DaoException {
        log.info("Finding all tickets");
        List<Ticket> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM tickets");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getInt("id"));
                ticket.setRow(resultSet.getInt("row"));
                ticket.setSeat(resultSet.getInt("seat"));
                ticket.setPrice(resultSet.getInt("price"));
                ticket.setSold(resultSet.getBoolean("sold"));
                result.add(ticket);
            }
        } catch (SQLException e) {
            log.error("Can't find tickets", e);
            throw new DaoException("Can't find tickets", e);
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
    public List<Ticket> findByUser(int userId) throws DaoException {
        log.info("Finding all tickets by user with id " + userId);
        List<Ticket> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM tickets WHERE user_id=?");
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getInt("id"));
                ticket.setRow(resultSet.getInt("row"));
                ticket.setSeat(resultSet.getInt("seat"));
                ticket.setPrice(resultSet.getInt("price"));
                ticket.setSold(resultSet.getBoolean("sold"));
                result.add(ticket);
            }
        } catch (SQLException e) {
            log.error("Can't find tickets", e);
            throw new DaoException("Can't find tickets", e);
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
    public List<Ticket> findBySession(int sessionId) throws DaoException {
        log.info("Finding all tickets by session with id " + sessionId);
        List<Ticket> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM tickets WHERE session_id=?");
            statement.setInt(1, sessionId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getInt("id"));
                ticket.setRow(resultSet.getInt("row"));
                ticket.setSeat(resultSet.getInt("seat"));
                ticket.setPrice(resultSet.getInt("price"));
                ticket.setSold(resultSet.getBoolean("sold"));
                result.add(ticket);
            }
        } catch (SQLException e) {
            log.error("Can't find tickets", e);
            throw new DaoException("Can't find tickets", e);
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
    public List<Ticket> findByState(boolean sold) throws DaoException {
        log.info("Finding all available tickets");
        List<Ticket> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM tickets WHERE sold=?");
            statement.setBoolean(1, sold);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getInt("id"));
                ticket.setRow(resultSet.getInt("row"));
                ticket.setSeat(resultSet.getInt("seat"));
                ticket.setPrice(resultSet.getInt("price"));
                ticket.setSold(sold);
                result.add(ticket);
            }
        } catch (SQLException e) {
            log.error("Can't find tickets", e);
            throw new DaoException("Can't find tickets", e);
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
