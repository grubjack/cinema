package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Ticket;
import com.grubjack.cinema.model.User;
import com.grubjack.cinema.service.ServiceFactory;
import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.grubjack.cinema.util.ConfigManager.*;

/**
 * {@code ShowTicketsCommand} implementation of interface {@code Command}
 */
public class ShowTicketsCommand implements Command {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(ShowTicketsCommand.class);

    /**
     * Get logged user from session
     * Set to sessions attribute:
     * - user tickets
     * - showService for find show by ticket id
     * @param request
     * @param response
     * @return  path to tickets page
     * @throws DaoException exception for dao operations
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        log.info("Executing with session id {}", request.getSession().getId());
        User user = (User) request.getSession().getAttribute(LOGGED_USER_ATTR);
        if (user != null) {
            log.info("Show tickets for user with id " + user.getId());
            request.getSession().setAttribute(TICKETS_ATTR, ServiceFactory.getInstance().getTicketService().findByUser(user.getId()));
        }
        return ConfigManager.getInstance().getProperty(TICKETS_PAGE_PATH);
    }
}
