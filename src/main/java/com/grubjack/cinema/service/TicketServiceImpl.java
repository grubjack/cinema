package com.grubjack.cinema.service;

import com.grubjack.cinema.dao.TicketDao;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code TicketServiceImpl} implementation of interface {@code TicketService} with login for entity {@code Ticket}
 */
public class TicketServiceImpl implements TicketService {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    /**
     * Store show tickets for preventing a lot of queries to database
     */
    private List<Ticket> showTickets;

    /**
     * interface {@code TicketDao} for jdbc operations with entity {@code Ticket}
     */
    private TicketDao ticketDao;

    public TicketServiceImpl(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    /**
     * Find all user tickets
     *
     * @return List of tickets corresponding to user with id {@param userId}
     * @throws DaoException exception for dao operations
     */
    @Override
    public List<Ticket> findByUser(int userId) throws DaoException {
        log.info("Get tickets of user with id {}", userId);
        return ticketDao.findByUser(userId);
    }

    /**
     * Find all movie show tickets
     *
     * @return List of tickets corresponding to show with id {@param showId}
     * @throws DaoException exception for dao operations
     */
    @Override
    public List<Ticket> findByShow(int showId) throws DaoException {
        log.info("Get tickets of show with id {}", showId);
        return ticketDao.findByShow(showId);
    }

    /**
     * Find the ticket by row and seat for some show
     *
     * @return Ticket corresponding to show with id {@param showId}with row {@param row}  and seat {@param seat}
     * @throws DaoException exception for dao operations
     */
    @Override
    public Ticket findByPlace(String showId, int row, int seat) throws DaoException {
        if (showTickets == null) {
            log.info("Get ticket of show with id {} and place row: {}, seat: {}", showId, row, seat);
            showTickets = ticketDao.findByShow(Integer.parseInt(showId));
        }
        return showTickets.stream().filter(t -> t.getRow() == row && t.getSeat() == seat).collect(Collectors.toList()).get(0);
    }

    /**
     * Cancel ticket by id
     * Ticket becomes available to buy again
     *
     * @param id ticket id
     * @throws DaoException exception for dao operations
     */
    @Override
    public void cancel(int id) throws DaoException {
        log.info("Cancel ticket with id {}", id);
        ticketDao.cancel(id);
    }

    /**
     * Change status of user ticket to sold
     *
     * @param id     ticket id
     * @param userId user id
     * @throws DaoException exception for dao operations
     */
    @Override
    public void buyTicket(int id, int userId) throws DaoException {
        log.info("Buy ticket with id {} by user with id {}", id, userId);
        ticketDao.buyTicket(id, userId);
    }

}
