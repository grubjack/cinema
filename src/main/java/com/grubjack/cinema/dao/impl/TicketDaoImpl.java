package com.grubjack.cinema.dao.impl;

import com.grubjack.cinema.dao.TicketDao;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Urban Aleksandr
 */
public class TicketDaoImpl implements TicketDao {

    private static Logger log = LoggerFactory.getLogger(TicketDaoImpl.class);
    private static final String CREATE_SHOW_TICKET_SQL = "INSERT INTO tickets (row, seat, price, sold, show_id) VALUES (?,?,?,?,?)";
    private static final String UPDATE_SHOW_TICKET_SQL = "UPDATE tickets SET row=?, seat=?, price=?, sold=?, show_id=? WHERE id=?";
    private static final String UPDATE_TICKET_SQL = "DELETE FROM tickets WHERE id=?";
    private static final String FIND_TICKET_SQL = "SELECT * FROM tickets WHERE id=?";
    private static final String FIND_ALL_TICKET_SQL = "SELECT * FROM tickets";
    private static final String FIND_USER_TICKET_SQL = "SELECT * FROM tickets WHERE user_id=?";
    private static final String FIND_SHOW_TICKET_SQL = "SELECT * FROM tickets WHERE show_id=?";
    private static final String FIND_TICKET_BY_STATE_SQL = "SELECT * FROM tickets WHERE sold=?";
    private static final String UPDATE_USER_TICKET_SQL = "UPDATE tickets SET sold=?,user_id=? WHERE id=?";

    private DataSource dataSource;

    public TicketDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Ticket ticket, int showId) throws DaoException {
        log.info("Creating new ticket");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(CREATE_SHOW_TICKET_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, ticket.getRow());
            statement.setInt(2, ticket.getSeat());
            statement.setInt(3, ticket.getPrice());
            statement.setBoolean(4, ticket.isSold());
            statement.setInt(5, showId);
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
    public void update(Ticket ticket, int showId) throws DaoException {
        log.info("Updating ticket with id {}", ticket.getId());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(UPDATE_SHOW_TICKET_SQL);
            statement.setInt(1, ticket.getRow());
            statement.setInt(2, ticket.getSeat());
            statement.setInt(3, ticket.getPrice());
            statement.setBoolean(4, ticket.isSold());
            statement.setInt(5, showId);
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
        log.info("Deleting ticket with id {}", id);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(UPDATE_TICKET_SQL);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can't delete ticket", e);
            throw new DaoException("Can't delete ticket", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
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
        log.info("Finding ticket with id {}", id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Ticket ticket = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(FIND_TICKET_SQL);
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
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(FIND_ALL_TICKET_SQL);
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
        log.info("Finding all tickets by user with id {}", userId);
        List<Ticket> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(FIND_USER_TICKET_SQL);
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
    public List<Ticket> findByShow(int showId) throws DaoException {
        log.info("Finding all tickets by show with id {}", showId);
        List<Ticket> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(FIND_SHOW_TICKET_SQL);
            statement.setInt(1, showId);
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
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(FIND_TICKET_BY_STATE_SQL);
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

    @Override
    public void buyTicket(int id, int userId) throws DaoException {
        log.info("Buy ticket with id {} by user with id", id, userId);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(UPDATE_USER_TICKET_SQL);
            statement.setBoolean(1, true);
            statement.setInt(2, userId);
            statement.setInt(3, id);
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
}
