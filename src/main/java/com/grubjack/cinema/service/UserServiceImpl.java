package com.grubjack.cinema.service;

import com.grubjack.cinema.dao.UserDao;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.User;
import com.grubjack.cinema.util.DigestMD5Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.grubjack.cinema.dao.DaoFactory.getInstance;


/**
 * Created by Urban Aleksandr
 */
public class UserServiceImpl implements UserService {

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

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

    @Override
    public User getByEmail(String email) throws DaoException {
        log.info("Get user with email {}", email);
        return userDao.getByEmail(email);
    }

    @Override
    public List<User> findAll() throws DaoException {
        log.info("Get all users");
        return userDao.findAll();
    }

    @Override
    public void delete(int userId) throws DaoException {
        log.info("Delete user with id {}", userId);
        userDao.delete(userId);
    }

    @Override
    public void create(User user) throws DaoException {
        log.info("Create new user {}", user);
        userDao.create(user);
    }
}
