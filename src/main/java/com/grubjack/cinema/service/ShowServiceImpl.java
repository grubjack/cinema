package com.grubjack.cinema.service;

import com.grubjack.cinema.dao.ShowDao;
import com.grubjack.cinema.dao.TicketDao;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.DayOfWeek;
import com.grubjack.cinema.model.Show;
import com.grubjack.cinema.model.Ticket;
import com.grubjack.cinema.model.TimeOfDay;
import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * {@code ShowServiceImpl} implementation of interface {@code ShowService} with login for entity {@code Show}
 */
public class ShowServiceImpl implements ShowService {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(ShowServiceImpl.class);

    /**
     * interface {@code ShowDao} for jdbc operations with entity {@code Show}
     */
    private ShowDao showDao;

    /**
     * interface {@code TicketDao} for jdbc operations with entity {@code Ticket}
     */
    private TicketDao ticketDao;

    public ShowServiceImpl(ShowDao showDao, TicketDao ticketDao) {
        this.showDao = showDao;
        this.ticketDao = ticketDao;
    }

    /**
     * Find all show entity
     *
     * @return List of all show entities
     * @throws DaoException exception for dao operations
     */
    @Override
    public List<Show> findAll() throws DaoException {
        log.info("Get all show");
        return showDao.findAll();
    }

    /**
     * Build cinema schedule
     * Find all shows grouping by day and time values
     *
     * @return cinema schedule group by time and day of shows
     * @throws DaoException exception for dao operations
     */
    @Override
    public Map<TimeOfDay, Map<DayOfWeek, Show>> getSchedule() throws DaoException {
        log.info("Get schedule");
        return showDao.findAll().stream().collect(groupingBy(Show::getTimeOfDay, toMap(Show::getDayOfWeek, identity())));
    }

    /**
     * Delete database record for show by id
     *
     * @param showId integer id of show instance
     * @throws DaoException exception for dao operations
     */
    @Override
    public void delete(int showId) throws DaoException {
        log.info("Delete show with id {}", showId);
        showDao.delete(showId);
    }

    public Show findByTicket(int ticketId) throws DaoException {
        log.info("Get show by ticket with id {}", ticketId);
        return showDao.findByTicket(ticketId);
    }

    /**
     * Save entity show and related tickets into database
     *
     * @param show instance of entity {@code Show}
     * @throws DaoException exception for dao operations
     */
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
        showDao.create(show);
    }

    /**
     * Computer attendance of movie show session in percentage
     *
     * @param showId id of movie show
     * @return attendance in percentage
     * @throws DaoException exception for dao operations
     */
    @Override
    public int getAttendance(int showId) throws DaoException {
        log.info("Compute attendance for show with id {}", showId);
        List<Ticket> showTickets = ticketDao.findByShow(showId);
        return showTickets.size() > 0 ? (int) (showTickets.stream().filter(Ticket::isSold).count() * 100 / showTickets.size()) : 0;
    }

    /**
     * Computer cost of ticket by time of movie show based on application config file
     *
     * @param timeOfDay time of show
     * @return ticket cost
     */
    private int computeCost(TimeOfDay timeOfDay) {
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
