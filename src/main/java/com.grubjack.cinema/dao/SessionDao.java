package com.grubjack.cinema.dao;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.DayOfWeek;
import com.grubjack.cinema.model.Session;
import com.grubjack.cinema.model.TimeOfDay;

import java.util.List;

/**
 * Created by Urban Aleksandr
 */
public interface SessionDao extends BaseDao<Session> {
    List<Session> findByDay(DayOfWeek dayOfWeek) throws DaoException;

    List<Session> findByTime(TimeOfDay timeOfDay) throws DaoException;

    Session findByDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) throws DaoException;

    List<Session> findByMovie(String movie) throws DaoException;

}
