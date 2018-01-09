package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Urban Aleksandr
 */
public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException;
}
