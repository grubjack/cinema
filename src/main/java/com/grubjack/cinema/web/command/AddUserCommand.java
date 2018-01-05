package com.grubjack.cinema.web.command;

import com.grubjack.cinema.model.Role;
import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Urban Aleksandr
 */
public class AddUserCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(AddUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Executing with session id {}", req.getSession().getId());
        req.getSession().setAttribute("roles", Role.names());
        return ConfigManager.getInstance().getProperty(ConfigManager.REGISTER_PAGE_PATH);
    }
}
