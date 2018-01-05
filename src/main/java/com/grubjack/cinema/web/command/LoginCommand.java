package com.grubjack.cinema.web.command;

import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Urban Aleksandr
 */
public class LoginCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Executing with session id {}", req.getSession().getId());
        return ConfigManager.getInstance().getProperty(ConfigManager.LOGIN_PAGE_PATH);
    }
}
