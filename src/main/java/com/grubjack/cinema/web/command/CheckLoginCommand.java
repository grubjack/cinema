package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.User;
import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.grubjack.cinema.service.ServiceFactory.getInstance;

/**
 * Created by Urban Aleksandr
 */
public class CheckLoginCommand implements Command {

    private static Logger log = LoggerFactory.getLogger(CheckLoginCommand.class);

    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        log.info("Executing with session id {}", req.getSession().getId());
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        req.getSession().setAttribute("login", login);
        req.getSession().setAttribute("password", password);
        if (getInstance().getUserService().checkLogin(login, password)) {
            User user = getInstance().getUserService().getByEmail(login);
            if (user != null) {
                req.getSession().setAttribute("loggedUser", user);
                return req.getParameter("from") == null ? new ShowScheduleCommand().execute(req, resp) : req.getParameter("from").substring(req.getContextPath().length());
            }
        } else {
            String errorMessage = "Invalid credentials";
            log.warn("User with session id {} has entered invalid credentials", req.getSession().getId());
            req.getSession().setAttribute("errorMessage", errorMessage);
        }
        return ConfigManager.getInstance().getProperty(ConfigManager.ERROR_PAGE_PATH);
    }
}