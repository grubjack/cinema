package com.grubjack.cinema.service;

import com.grubjack.cinema.dao.UserDao;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.User;
import com.grubjack.cinema.util.DigestMD5Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * {@code UserServiceImpl} implementation of interface {@code UserService} with login for entity {@code User}
 */
public class UserServiceImpl implements UserService {

    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * interface {@code UserDao} for jdbc operations with entity {@code User}
     */
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Check user credentials
     * Compure MD5 hash of user password and compare it with data retrieved from database
     *
     * @param login    user email
     * @param password user password in plain text
     * @return true in credentials are right, otherwise false
     * @throws DaoException
     */
    @Override
    public boolean checkLogin(String login, String password) throws DaoException {
        User user = userDao.getByEmail(login);
        boolean result = false;
        if (user != null && user.getPassword().equalsIgnoreCase(DigestMD5Helper.computeHash(password))) {
            log.info("User {} {} has successfully logged in", user.getFirstName(), user.getLastName());
            result = true;
        }
        return result;
    }

    /**
     * Find user by its email
     *
     * @return Instance of class {@code User} corresponding to {@param email}
     * @throws DaoException exception for dao operations
     */
    @Override
    public User getByEmail(String email) throws DaoException {
        log.info("Get user with email {}", email);
        return userDao.getByEmail(email);
    }

    /**
     * Find all user entity
     *
     * @return List of all user entities
     * @throws DaoException exception for dao operations
     */
    @Override
    public List<User> findAll() throws DaoException {
        log.info("Get all users");
        return userDao.findAll();
    }

    /**
     * Delete database record for user and user roles by user id
     *
     * @param userId integer id of user instance
     * @throws DaoException exception for dao operations
     */
    @Override
    public void delete(int userId) throws DaoException {
        log.info("Delete user with id {}", userId);
        userDao.delete(userId);
    }

    /**
     * Save entity user and user roles into database
     *
     * @param user instance of entity {@code User}
     * @throws DaoException exception for dao operations
     */
    @Override
    public void create(User user) throws DaoException {
        log.info("Create new user {}", user);
        userDao.create(user);
    }
}
