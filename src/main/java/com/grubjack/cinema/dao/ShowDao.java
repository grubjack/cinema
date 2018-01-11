package com.grubjack.cinema.dao;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.DayOfWeek;
import com.grubjack.cinema.model.Show;
import com.grubjack.cinema.model.TimeOfDay;

import java.util.List;

/**
 * {@code ShowDao} extends {@code BaseDao} interface for entity class {@code Show}
 * <p>
 * interface provides additional methods by different criteria.
 */
public interface ShowDao extends BaseDao<Show> {
    /**
     * List of movie shows by day of week
     *
     * @return List of {@code Show} corresponding to {@param dayOfWeek}
     * @throws DaoException exception for dao operations
     */
    List<Show> findByDay(DayOfWeek dayOfWeek) throws DaoException;

    /**
     * List of movie shows by time of movie show
     *
     * @return List of {@code Show} corresponding to {@param timeOfDay}
     * @throws DaoException exception for dao operations
     */
    List<Show> findByTime(TimeOfDay timeOfDay) throws DaoException;

    /**
     * Find show by day of week and time of movie show
     *
     * @return Instance of class {@code Show} corresponding to {@param dayOfWeek} and {@param timeOfDay}
     * @throws DaoException exception for dao operations
     */
    Show findByDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) throws DaoException;

    /**
     * List of movie shows by some movie
     *
     * @return List of {@code Show} corresponding to movie {@param movie}
     * @throws DaoException exception for dao operations
     */
    List<Show> findByMovie(String movie) throws DaoException;

    /**
     * Find show by ticket
     *
     * @return Instance of class {@code Show} corresponding to {@param ticketId}
     * @throws DaoException exception for dao operations
     */
    Show findByTicket(int ticketId) throws DaoException;
}
