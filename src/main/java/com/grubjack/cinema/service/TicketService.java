package com.grubjack.cinema.service;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Ticket;

import java.util.List;

/**
 * Created by Urban Aleksandr
 */
public interface TicketService {
    List<Ticket> findByUser(int userId) throws DaoException;

    List<Ticket> findByShow(int showId) throws DaoException;

    void buyTicket(int id, int userId) throws DaoException;

    Ticket findByPlace(String showId, int row, int seat) throws DaoException;
}
