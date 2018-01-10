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

import java.util.List;

public class ShowDaoImplTest {

    private ShowDao showDao = new ShowDaoImpl(DBHelper.getDataSource());
    private TicketDao ticketDao = new TicketDaoImpl(DBHelper.getDataSource());

    @Before
    public void setUp() {
        DBHelper.setUpDatabase();
    }

    @Test
    public void create() throws DaoException {
        Show newShow = new Show(DayOfWeek.SUNDAY, TimeOfDay.SIXTH, "New Show");
        showDao.create(newShow);
        Assert.assertEquals(newShow, showDao.findByDayAndTime(DayOfWeek.SUNDAY, TimeOfDay.SIXTH));
    }

    @Test
    public void createTickets() throws DaoException {
        Show newShow = new Show(DayOfWeek.SUNDAY, TimeOfDay.SIXTH, "New Show");
        newShow.getTickets().add(new Ticket(1,1,1));
        newShow.getTickets().add(new Ticket(1,2,2));
        newShow.getTickets().add(new Ticket(1,3,3));
        showDao.create(newShow);
        Assert.assertEquals(3, ticketDao.findByShow(newShow.getId()).size());
    }

    @Test
    public void update() throws DaoException {
        Show oldShow = showDao.find(1);
        oldShow.setMovie("New movie");
        showDao.update(oldShow);
        Assert.assertEquals(oldShow, showDao.find(1));
    }

    @Test
    public void delete() throws DaoException {
        showDao.delete(1);
        Assert.assertNull(showDao.find(1));
    }

    @Test
    public void find() throws DaoException {
        Show show = showDao.find(1);
        Assert.assertNotNull(show);
    }

    @Test
    public void findAll() throws DaoException {
        List<Show> showList = showDao.findAll();
        Assert.assertEquals(40, showList.size());
    }

    @Test
    public void findByDay() throws DaoException {
        List<Show> dayShows = showDao.findByDay(DayOfWeek.MONDAY);
        Assert.assertEquals(6, dayShows.size());
    }

    @Test
    public void findByTime() throws DaoException {
        List<Show> timeShows = showDao.findByTime(TimeOfDay.FIRST);
        Assert.assertEquals(7, timeShows.size());
    }

    @Test
    public void findByDayAndTime() throws DaoException {
        Show show = showDao.findByDayAndTime(DayOfWeek.MONDAY, TimeOfDay.FIRST);
        Assert.assertNotNull(show);
    }

    @Test
    public void findByMovie() throws DaoException {
        List<Show> movieShows = showDao.findByMovie("Her");
        Assert.assertEquals(5, movieShows.size());
    }

    @Test
    public void findByTicket() throws DaoException {
        Show showByTicket = showDao.findByTicket(1);
        Assert.assertNotNull(showByTicket);

    }

}