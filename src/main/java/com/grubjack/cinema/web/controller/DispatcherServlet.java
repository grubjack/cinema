package com.grubjack.cinema.web.controller;

import com.grubjack.cinema.exception.DaoException;
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

import static com.grubjack.cinema.util.ConfigManager.*;

/**
 * {@code DispatcherServlet} single application http servlet
 * Using for routing client requests
 */
public class DispatcherServlet extends HttpServlet {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Process client requests
     * Determinate instance of {@code Command}} , execute it and forwart to related page
     * All exceptions write to log, error message set as attribute of request
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocaleResourceBundle.setFor(request);
        String page;
        try {
            Command command = RequestHelper.getInstance().getCommand(request);
            page = command.execute(request, response);
        } catch (DaoException e) {
            String errorMessage = e.getLocalizedMessage();
            log.error("Error: {}", errorMessage);
            log.error("Error StackTrace: {}", e);
            request.setAttribute(ERROR_MESSAGE_ATTR, errorMessage);
            page = getInstance().getProperty(ERROR_PAGE_PATH);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
