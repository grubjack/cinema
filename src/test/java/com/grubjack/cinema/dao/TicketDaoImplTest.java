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
    public void testCreate() throws DaoException {
        Show show = new Show(DayOfWeek.SUNDAY, TimeOfDay.SIXTH, "New movie");
        showDao.create(show);
        Ticket ticket = new Ticket(1, 1, 1);
        ticketDao.create(ticket, show.getId());
        Assert.assertEquals(Arrays.asList(ticket), ticketDao.findByShow(show.getId()));
    }

    @Test
    public void testUpdate() throws DaoException {
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
    public void testDelete() throws DaoException {
        ticketDao.delete(1);
        Assert.assertNull(ticketDao.find(1));
    }

    @Test
    public void testFind() throws DaoException {
        Assert.assertNotNull(ticketDao.find(1));
    }

    @Test
    public void testFindAll() throws DaoException {
        Assert.assertEquals(1120, ticketDao.findAll().size());
    }

    @Test
    public void testFindByUser() throws DaoException {
        ticketDao.buyTicket(1, 1);
        Assert.assertEquals(1, ticketDao.findByUser(1).size());
    }

    @Test
    public void testFindByShow() throws DaoException {
        Assert.assertEquals(28, ticketDao.findByShow(1).size());
    }

    @Test
    public void testFindByState() throws DaoException {
        Ticket ticket = ticketDao.find(1);
        ticket.setSold(true);
        ticketDao.update(ticket, showDao.findByTicket(ticket.getId()).getId());
        Assert.assertEquals(1, ticketDao.findByState(true).size());
    }

    @Test
    public void testBuyTicket() throws DaoException {
        ticketDao.buyTicket(1, 1);
        Assert.assertEquals(1, ticketDao.findByState(true).size());
    }

    @Test
    public void testCancel() throws DaoException {
        ticketDao.buyTicket(1, 1);
        ticketDao.cancel(1);
        Assert.assertEquals(0, ticketDao.findByState(true).size());
    }
}