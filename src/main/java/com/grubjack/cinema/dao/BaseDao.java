package com.grubjack.cinema.dao;

import com.grubjack.cinema.exception.DaoException;

import java.util.List;

/**
 * {@code BaseDao} interface to all entities, provides base CRUD methods
 *
 * @param <T> class of entity
 */
public interface BaseDao<T> {
    /**
     * Create new entity
     *
     * @param t class of entity
     * @throws DaoException exception for dao operations
     */
    void create(T t) throws DaoException;

    /**
     * Create new entity
     *
     * @param t class of entity
     * @throws DaoException exception for dao operations
     */
    void update(T t) throws DaoException;

    /**
     * Delete entity by id
     *
     * @param id integer id of entity
     * @throws DaoException exception for dao operations
     */
    void delete(int id) throws DaoException;

    /**
     * Find entity by id
     *
     * @param id integer id of entity
     * @return found entity
     * @throws DaoException exception for dao operations
     */
    T find(int id) throws DaoException;

    /**
     * Find all entities
     *
     * @return List of all entities
     * @throws DaoException exception for dao operations
     */
    List<T> findAll() throws DaoException;
}
