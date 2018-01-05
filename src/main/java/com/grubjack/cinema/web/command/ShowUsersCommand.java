package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.service.ServiceFactory;
import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Urban Aleksandr
 */
public class ShowUsersCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(ShowUsersCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        log.info("Executing with session id {}", req.getSession().getId());
        req.getSession().setAttribute("users", ServiceFactory.getInstance().getUserService().findAll());
        return ConfigManager.getInstance().getProperty(ConfigManager.USERS_PAGE_PATH);
    }
}
