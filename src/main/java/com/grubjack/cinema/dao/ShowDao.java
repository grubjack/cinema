package com.grubjack.cinema.dao;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.DayOfWeek;
import com.grubjack.cinema.model.Show;
import com.grubjack.cinema.model.TimeOfDay;

import java.util.List;

/**
 * Created by Urban Aleksandr
 */
public interface ShowDao extends BaseDao<Show> {
    List<Show> findByDay(DayOfWeek dayOfWeek) throws DaoException;

    List<Show> findByTime(TimeOfDay timeOfDay) throws DaoException;

    Show findByDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) throws DaoException;

    List<Show> findByMovie(String movie) throws DaoException;

    Show findByTicket(int ticketId) throws DaoException;

}
