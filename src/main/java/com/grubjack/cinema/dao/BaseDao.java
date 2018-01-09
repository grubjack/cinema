package com.grubjack.cinema.dao;

import com.grubjack.cinema.exception.DaoException;

import java.util.List;

/**
 * Created by Urban Aleksandr
 */
public interface BaseDao<T> {
    void create(T t) throws DaoException;

    void update(T t) throws DaoException;

    void delete(int id) throws DaoException;

    T find(int id) throws DaoException;

    List<T> findAll() throws DaoException;
}
