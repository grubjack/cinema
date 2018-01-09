package com.grubjack.cinema.web.command;

import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.grubjack.cinema.util.ConfigManager.LOGIN_PAGE_PATH;

/**
 * Created by Urban Aleksandr
 */
public class LoginCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        log.info("Executing with session id {}", request.getSession().getId());
        return ConfigManager.getInstance().getProperty(LOGIN_PAGE_PATH);
    }
}
