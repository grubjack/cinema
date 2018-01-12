package com.grubjack.cinema.service;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.DayOfWeek;
import com.grubjack.cinema.model.Show;
import com.grubjack.cinema.model.TimeOfDay;

import java.util.List;
import java.util.Map;

/**
 * {@code ShowService} interface for service working with logic of movies
 */
public interface ShowService {

    /**
     * Find all show entity
     *
     * @return List of all show entities
     * @throws DaoException exception for dao operations
     */
    List<Show> findAll() throws DaoException;

    /**
     * Delete database record for show by id
     *
     * @param showId integer id of show instance
     * @throws DaoException exception for dao operations
     */
    void delete(int showId) throws DaoException;

    /**
     * Save entity show and related tickets into database
     *
     * @param show instance of entity {@code Show}
     * @throws DaoException exception for dao operations
     */
    void create(Show show) throws DaoException;

    /**
     * Computer attendance of movie show session in percentage
     *
     * @param showId id of movie show
     * @return attendance in percentage
     * @throws DaoException exception for dao operations
     */
    int getAttendance(int showId) throws DaoException;

    /**
     * Build cinema schedule
     * Find all shows grouping by day and time values
     *
     * @return cinema schedule group by time and day of shows
     * @throws DaoException exception for dao operations
     */
    public Map<TimeOfDay, Map<DayOfWeek, Show>> getSchedule() throws DaoException;
}
