package com.grubjack.cinema.service;

import com.grubjack.cinema.dao.DaoFactory;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Show;
import com.grubjack.cinema.model.Ticket;
import com.grubjack.cinema.model.TimeOfDay;
import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Urban Aleksandr
 */
public class ShowServiceImpl implements ShowService {
    private static Logger log = LoggerFactory.getLogger(ShowServiceImpl.class);
    private List<Show> shows;

    @Override
    public List<Show> findAll() throws DaoException {
        log.info("Get all show");
        return DaoFactory.getInstance().getShowDao().findAll();
    }

    @Override
    public Show findByDayAndTime(String day, String time) throws DaoException {
        if (shows == null) {
            log.info("Get all show");
            shows = DaoFactory.getInstance().getShowDao().findAll();
        }
        for (Show show : shows) {
            if (show.getDayOfWeek().toString().equalsIgnoreCase(day) && show.getTimeOfDay().toString().equalsIgnoreCase(time)) {
                log.info("Get show by day {} and time {}", day, time);
                return show;
            }
        }
        return null;
    }

    @Override
    public void delete(int showId) throws DaoException {
        log.info("Delete show with id {}", showId);
        DaoFactory.getInstance().getShowDao().delete(showId);
    }

    public static Show findByTicket(int ticketId) throws DaoException {
        log.info("Get show by ticket with id {}", ticketId);
        return DaoFactory.getInstance().getShowDao().findByTicket(ticketId);
    }

    @Override
    public void create(Show show) throws DaoException {
        log.info("Create show {}", show.toString());
        String totalRows = ConfigManager.getInstance().getProperty(ConfigManager.HALL_ROW_VALUE);
        String seatsPerRow = ConfigManager.getInstance().getProperty(ConfigManager.HALL_SEAT_VALUE);

        log.info("Create tickets for new show");
        for (int row = 1; row <= Integer.parseInt(totalRows); row++) {
            for (int seat = 1; seat <= Integer.parseInt(seatsPerRow); seat++) {
                show.getTickets().add(new Ticket(row, seat, computeCost(show.getTimeOfDay())));
            }
        }
        DaoFactory.getInstance().getShowDao().create(show);
    }

    @Override
    public int getAttendance(int showId) throws DaoException {
        log.info("Compute attendance for show with id {}", showId);
        List<Ticket> showTickets = DaoFactory.getInstance().getTicketDao().findByShow(showId);
        return (int) (showTickets.stream().filter(Ticket::isSold).count() * 100 / showTickets.size());
    }

    @Override
    public int computeCost(TimeOfDay timeOfDay) {
        int result = 0;
        switch (timeOfDay) {
            case FIRST:
            case SECOND:
                result = Integer.parseInt(ConfigManager.getInstance().getProperty(ConfigManager.TICKET_LOW_PRICE));
                break;
            case THIRD:
            case FOURTH:
                result = Integer.parseInt(ConfigManager.getInstance().getProperty(ConfigManager.TICKET_MIDDLE_PRICE));
                break;
            case FIFTH:
            case SIXTH:
                result = Integer.parseInt(ConfigManager.getInstance().getProperty(ConfigManager.TICKET_HIGH_PRICE));
                break;
        }
        return result;
    }
}
