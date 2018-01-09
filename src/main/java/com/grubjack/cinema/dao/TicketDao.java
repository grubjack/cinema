package com.grubjack.cinema.dao;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Ticket;

import java.util.List;

/**
 * Created by Urban Aleksandr
 */
public interface TicketDao {

    void create(Ticket ticket, int showId) throws DaoException;

    void update(Ticket ticket, int showId) throws DaoException;

    void delete(int id) throws DaoException;

    Ticket find(int id) throws DaoException;

    List<Ticket> findAll() throws DaoException;

    List<Ticket> findByUser(int userId) throws DaoException;

    List<Ticket> findByShow(int showId) throws DaoException;

    List<Ticket> findByState(boolean sold) throws DaoException;

    void buyTicket(int id, int userId) throws DaoException;


}
