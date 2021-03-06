package com.grubjack.cinema.service;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Ticket;
import com.grubjack.cinema.to.TicketWithShow;

import java.util.List;
import java.util.Map;

/**
 * {@code TicketService} interface for service working with login of tickets
 */
public interface TicketService {
    /**
     * Find all user tickets with show
     *
     * @return List of tickets with show corresponding to user with id {@param userId}
     * @throws DaoException exception for dao operations
     */
    List<TicketWithShow> findByUser(int userId) throws DaoException;

    /**
     * Find all movie show tickets
     *
     * @return List of tickets corresponding to show with id {@param showId}
     * @throws DaoException exception for dao operations
     */
    Map<Integer, Map<Integer, Ticket>> findByShowGroupByPlace(int showId) throws DaoException;

    /**
     * Change status of user ticket to sold
     *
     * @param id     ticket id
     * @param userId user id
     * @throws DaoException exception for dao operations
     */
    void buyTicket(int id, int userId) throws DaoException;

    /**
     * Cancel ticket by id
     * Ticket becomes available to buy again
     *
     * @param id ticket id
     * @throws DaoException exception for dao operations
     */
    void cancel(int id) throws DaoException;

}
