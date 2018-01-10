package com.grubjack.cinema.dao;

import com.grubjack.cinema.DBHelper;
import com.grubjack.cinema.dao.impl.ShowDaoImpl;
import com.grubjack.cinema.dao.impl.TicketDaoImpl;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.DayOfWeek;
import com.grubjack.cinema.model.Show;
import com.grubjack.cinema.model.Ticket;
import com.grubjack.cinema.model.TimeOfDay;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;


public class TicketDaoImplTest {

    private ShowDao showDao = new ShowDaoImpl(DBHelper.getDataSource());
    private TicketDao ticketDao = new TicketDaoImpl(DBHelper.getDataSource());

    @Before
    public void setUp() {
        DBHelper.setUpDatabase();
    }

    @Test
    public void create() throws DaoException {
        Show show = new Show(DayOfWeek.SUNDAY, TimeOfDay.SIXTH, "New movie");
        showDao.create(show);
        Ticket ticket = new Ticket(1, 1, 1);
        ticketDao.create(ticket, show.getId());
        Assert.assertEquals(Arrays.asList(ticket), ticketDao.findByShow(show.getId()));
    }

    @Test
    public void update() throws DaoException {
        Ticket ticket = ticketDao.find(1);
        if (ticket.isSold()) {
            ticket.setSold(false);
        } else {
            ticket.setSold(true);
        }
        ticketDao.update(ticket, showDao.findByTicket(ticket.getId()).getId());
        Assert.assertEquals(ticket, ticketDao.find(ticket.getId()));
    }

    @Test
    public void delete() throws DaoException {
        ticketDao.delete(1);
        Assert.assertNull(ticketDao.find(1));
    }

    @Test
    public void find() throws DaoException {
        Assert.assertNotNull(ticketDao.find(1));
    }

    @Test
    public void findAll() throws DaoException {
        Assert.assertEquals(320, ticketDao.findAll().size());
    }

    @Test
    public void findByUser() throws DaoException {
        ticketDao.buyTicket(1, 1);
        Assert.assertEquals(1, ticketDao.findByUser(1).size());
    }

    @Test
    public void findByShow() throws DaoException {
        Assert.assertEquals(8, ticketDao.findByShow(1).size());
    }

    @Test
    public void findByState() throws DaoException {
        Ticket ticket = ticketDao.find(1);
        ticket.setSold(true);
        ticketDao.update(ticket, showDao.findByTicket(ticket.getId()).getId());
        Assert.assertEquals(1, ticketDao.findByState(true).size());
    }

    @Test
    public void buyTicket() throws DaoException {
        ticketDao.buyTicket(1, 1);
        Assert.assertEquals(1, ticketDao.findByState(true).size());
    }
}