package com.grubjack.cinema.service;

import com.grubjack.cinema.DBHelper;
import com.grubjack.cinema.dao.ShowDao;
import com.grubjack.cinema.dao.TicketDao;
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
import java.util.Map;

public class ShowServiceImplTest {

    private ShowDao showDao = new ShowDaoImpl(DBHelper.getDataSource());
    private TicketDao ticketDao = new TicketDaoImpl(DBHelper.getDataSource());
    private ShowService showService = new ShowServiceImpl(showDao, ticketDao);

    @Before
    public void setUp() {
        DBHelper.setUpDatabase();
    }

    @Test
    public void testCreate() throws DaoException {
        Show show = new Show(DayOfWeek.SUNDAY, TimeOfDay.SIXTH, "Test movie");
        showService.create(show);
        Assert.assertEquals(28, ticketDao.findByShow(show.getId()).size());
    }

    @Test
    public void testAttendance() throws DaoException {
        Show show = new Show(DayOfWeek.SUNDAY, TimeOfDay.SIXTH, "Test movie");
        showService.create(show);
        Assert.assertEquals(0, showService.getAttendance(show.getId()));
    }

    @Test
    public void testAttendanceFull() throws DaoException {
        Show show = new Show(DayOfWeek.SUNDAY, TimeOfDay.SIXTH, "Test movie");
        showService.create(show);
        for (Ticket ticket : ticketDao.findByShow(show.getId())) {
            ticketDao.buyTicket(ticket.getId(), 1);
        }
        Assert.assertEquals(100, showService.getAttendance(show.getId()));
    }

    @Test
    public void testScheduleContainsAllTimes() throws DaoException {
        Map<TimeOfDay, Map<DayOfWeek, Show>> schedule = showService.getSchedule();
        Assert.assertTrue(schedule.keySet().containsAll(Arrays.asList(TimeOfDay.values())));
    }

    @Test
    public void testScheduleContainsAllDay() throws DaoException {
        Map<TimeOfDay, Map<DayOfWeek, Show>> schedule = showService.getSchedule();
        Assert.assertTrue(schedule.get(TimeOfDay.FIRST).keySet().containsAll(Arrays.asList(DayOfWeek.values())));
    }
}