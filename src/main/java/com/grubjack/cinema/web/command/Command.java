package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Urban Aleksandr
 */
public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, DaoException;
}
