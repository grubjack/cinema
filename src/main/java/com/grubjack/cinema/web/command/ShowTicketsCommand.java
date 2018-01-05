package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.User;
import com.grubjack.cinema.service.ServiceFactory;
import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Urban Aleksandr
 */
public class ShowTicketsCommand implements Command {

    private static Logger log = LoggerFactory.getLogger(ShowTicketsCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        log.info("Executing with session id {}", req.getSession().getId());
        User user = (User) req.getSession().getAttribute("loggedUser");
        if (user != null) {
            log.info("Show tickets for user with id " + user.getId());
            req.getSession().setAttribute("tickets", ServiceFactory.getInstance().getTicketService().findByUser(user.getId()));
        }
        return ConfigManager.getInstance().getProperty(ConfigManager.TICKETS_PAGE_PATH);
    }
}
