package com.grubjack.cinema.service;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Show;

import java.util.List;

/**
 * Created by Urban Aleksandr
 */
public interface ShowService {
    List<Show> findAll() throws DaoException;

    void delete(int showId) throws DaoException;

    void create(Show show) throws DaoException;

    int getAttendance(int showId) throws DaoException;

    Show findByDayAndTime(String day, String time) throws DaoException;
}
