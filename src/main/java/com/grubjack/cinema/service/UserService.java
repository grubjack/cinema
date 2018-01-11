package com.grubjack.cinema.service;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.User;

import java.util.List;

/**
 * {@code UserService} interface for service working with logic of users
 */
public interface UserService {
    boolean checkLogin(String login, String password) throws DaoException;

    /**
     * Find user by its email
     *
     * @return Instance of class {@code User} corresponding to {@param email}
     * @throws DaoException exception for dao operations
     */
    User getByEmail(String email) throws DaoException;

    /**
     * Find all user entity
     *
     * @return List of all user entities
     * @throws DaoException exception for dao operations
     */
    List<User> findAll() throws DaoException;

    /**
     * Delete database record for user and user roles by user id
     *
     * @param userId integer id of user instance
     * @throws DaoException exception for dao operations
     */
    void delete(int userId) throws DaoException;

    /**
     * Save entity user and user roles into database
     *
     * @param user instance of entity {@code User}
     * @throws DaoException exception for dao operations
     */
    void create(User user) throws DaoException;
}
