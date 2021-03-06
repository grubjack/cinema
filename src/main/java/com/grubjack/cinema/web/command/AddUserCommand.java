package com.grubjack.cinema.web.command;

import com.grubjack.cinema.model.Role;
import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.grubjack.cinema.util.ConfigManager.REGISTER_PAGE_PATH;
import static com.grubjack.cinema.util.ConfigManager.ROLES_ATTR;

/**
 * {@code AddUserCommand} implementation of interface {@code Command}
 */
public class AddUserCommand implements Command {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(AddUserCommand.class);

    /**
     * Save in session names of user roles
     *
     * @param request
     * @param response
     * @return path to register user jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        log.info("Executing with session id {}", request.getSession().getId());
        request.getSession().setAttribute(ROLES_ATTR, Role.names());
        return ConfigManager.getInstance().getProperty(REGISTER_PAGE_PATH);
    }
}
