package com.grubjack.cinema.dao;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.User;

/**
 * {@code UserDao} extends {@code BaseDao} interface for entity class {@code User}
 * <p>
 * interface provides additional methods by different criteria.
 */
public interface UserDao extends BaseDao<User> {
    /**
     * Find user by its email
     *
     * @return Instance of class {@code User} corresponding to {@param email}
     * @throws DaoException exception for dao operations
     */
    User getByEmail(String email) throws DaoException;
}
