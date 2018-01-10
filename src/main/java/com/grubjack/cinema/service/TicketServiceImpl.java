package com.grubjack.cinema.service;

import com.grubjack.cinema.dao.TicketDao;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Urban Aleksandr
 */
public class TicketServiceImpl implements TicketService {
    private static Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);
    private List<Ticket> showTickets;

    private TicketDao ticketDao;

    public TicketServiceImpl(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public List<Ticket> findByUser(int userId) throws DaoException {
        log.info("Get tickets of user with id {}", userId);
        return ticketDao.findByUser(userId);
    }

    @Override
    public List<Ticket> findByShow(int showId) throws DaoException {
        log.info("Get tickets of show with id {}", showId);
        return ticketDao.findByShow(showId);
    }

    @Override
    public Ticket findByPlace(String showId, int row, int seat) throws DaoException {
        if (showTickets == null) {
            log.info("Get ticket of show with id {} and place row: {}, seat: {}", showId, row, seat);
            showTickets = ticketDao.findByShow(Integer.parseInt(showId));
        }
        return showTickets.stream().filter(t -> t.getRow() == row && t.getSeat() == seat).collect(Collectors.toList()).get(0);
    }

    @Override
    public void buyTicket(int id, int userId) throws DaoException {
        log.info("Buy ticket with id {} by user with id {}", id, userId);
        ticketDao.buyTicket(id, userId);
    }

}
