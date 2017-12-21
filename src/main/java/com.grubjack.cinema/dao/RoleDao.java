package com.grubjack.cinema.dao;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Role;

import java.util.Set;

/**
 * Created by Urban Aleksandr
 */
public interface RoleDao extends BaseDao<Role> {
    Set<Role> findByUser(int userId) throws DaoException;
}
