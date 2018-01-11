package com.grubjack.cinema.dao;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Ticket;

import java.util.List;

/**
 * {@code TicketDao} extends {@code BaseDao} interface for entity class {@code Ticket}
 * <p>
 * interface provides additional methods by different criteria.
 */
public interface TicketDao {
    /**
     * Create new ticket for movie show
     *
     * @param ticket entity for create
     * @param showId id of show movie
     * @throws DaoException exception for dao operations
     */
    void create(Ticket ticket, int showId) throws DaoException;

    /**
     * Update ticket for movie show
     *
     * @param ticket entity for update
     * @param showId id of show movie
     * @throws DaoException exception for dao operations
     */
    void update(Ticket ticket, int showId) throws DaoException;

    /**
     * Delete ticket by id
     *
     * @param id integer id of ticket
     * @throws DaoException exception for dao operations
     */
    void delete(int id) throws DaoException;

    /**
     * Find ticket by id
     *
     * @param id integer id of ticket
     * @return found ticket
     * @throws DaoException exception for dao operations
     */
    Ticket find(int id) throws DaoException;

    /**
     * Find all tickets
     *
     * @return List of all tickets
     * @throws DaoException exception for dao operations
     */
    List<Ticket> findAll() throws DaoException;

    /**
     * Find all user tickets
     *
     * @return List of tickets corresponding to user with id {@param userId}
     * @throws DaoException exception for dao operations
     */
    List<Ticket> findByUser(int userId) throws DaoException;

    /**
     * Find all movie show tickets
     *
     * @return List of tickets corresponding to show with id {@param showId}
     * @throws DaoException exception for dao operations
     */
    List<Ticket> findByShow(int showId) throws DaoException;

    /**
     * Find all tickets by its sold status
     *
     * @return List of tickets corresponding to sold status {@param sold}
     * @throws DaoException exception for dao operations
     */
    List<Ticket> findByState(boolean sold) throws DaoException;

    /**
     * Change status of user ticket to sold
     *
     * @param id     ticket id
     * @param userId user id
     * @throws DaoException exception for dao operations
     */
    void buyTicket(int id, int userId) throws DaoException;
}
