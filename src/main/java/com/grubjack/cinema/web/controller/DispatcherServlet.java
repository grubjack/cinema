package com.grubjack.cinema.web.controller;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.util.ConfigManager;
import com.grubjack.cinema.util.LocaleResourceBundle;
import com.grubjack.cinema.web.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Urban Aleksandr
 */
public class DispatcherServlet extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleResourceBundle.setFor(req);
        String page;
        try {
            Command command = RequestHelper.getInstance().getCommand(req);
            page = command.execute(req, resp);
        } catch (ServletException | IOException | DaoException e) {
            e.printStackTrace();
            String errorMessage = String.format("ServletException: %s", e.getLocalizedMessage());
            log.error("Error: {}", errorMessage);
            req.setAttribute("errorMessage", errorMessage);
            page = ConfigManager.getInstance().getProperty(ConfigManager.ERROR_PAGE_PATH);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(req, resp);
    }
}
