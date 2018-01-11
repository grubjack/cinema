package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.User;
import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.grubjack.cinema.service.ServiceFactory.getInstance;
import static com.grubjack.cinema.util.ConfigManager.*;

/**
 * {@code CheckLoginCommand} implementation of interface {@code Command}
 */
public class CheckLoginCommand implements Command {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(CheckLoginCommand.class);

    /**
     * Get entered user email and password from request and check it
     * If credentials is invalid add error message to session
     *
     * @param request
     * @param response
     * @return forward to previous page using fromPage parameter or error page
     * @throws DaoException exception for dao operations
     */
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        log.info("Executing with session id {}", request.getSession().getId());
        String login = request.getParameter(LOGIN_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);
        request.getSession().setAttribute(LOGIN_PARAM, login);
        request.getSession().setAttribute(PASSWORD_PARAM, password);
        if (getInstance().getUserService().checkLogin(login, password)) {
            User user = getInstance().getUserService().getByEmail(login);
            if (user != null) {
                request.getSession().setAttribute(LOGGED_USER_ATTR, user);
                String fromPage = request.getParameter(FROM_PARAM);
                log.info("referer page:  {}", fromPage);
                return (fromPage != null) && !fromPage.endsWith(ConfigManager.getInstance().getProperty(ERROR_PAGE_PATH)) ? fromPage.substring(request.getContextPath().length()) : ConfigManager.getInstance().getProperty(LOGIN_PAGE_PATH);
            }
        } else {
            String errorMessage = "Invalid credentials";
            log.warn("User with session id {} has entered invalid credentials", request.getSession().getId());
            request.getSession().setAttribute("errorMessage", errorMessage);
        }
        return ConfigManager.getInstance().getProperty(ERROR_PAGE_PATH);
    }
}