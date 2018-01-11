package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.grubjack.cinema.util.ConfigManager.*;

/**
 * {@code ShowUsersCommand} implementation of interface {@code Command}
 */
public class ShowUsersCommand implements Command {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(ShowUsersCommand.class);

    /**
     * Set to sessions all users attribute
     *
     * @param request
     * @param response
     * @return path to users page
     * @throws DaoException exception for dao operations
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        log.info("Executing with session id {}", request.getSession().getId());
        request.getSession().setAttribute(USERS_ATTR, ServiceFactory.getInstance().getUserService().findAll());
        return getInstance().getProperty(USERS_PAGE_PATH);
    }
}
