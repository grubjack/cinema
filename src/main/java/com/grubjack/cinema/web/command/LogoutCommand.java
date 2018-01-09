package com.grubjack.cinema.web.command;

import com.grubjack.cinema.util.LocalePropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Urban Aleksandr
 */
public class LogoutCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Executing with session id {}", req.getSession().getId());
        req.getSession().invalidate();
        req.setAttribute(LocalePropertyManager.MESSAGE_ATTRIBUTE_NAME, new LocalePropertyManager(req.getLocale()));
        return new ShowScheduleCommand().execute(req, resp);
    }
}
