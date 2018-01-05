package com.grubjack.cinema.service;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.User;

import java.util.List;

/**
 * Created by Urban Aleksandr
 */
public interface UserService {
    boolean checkLogin(String login, String password) throws DaoException;

    User getByEmail(String email) throws DaoException;

    List<User> findAll() throws DaoException;

    void delete(int userId) throws DaoException;

    void create(User user) throws DaoException;
}
