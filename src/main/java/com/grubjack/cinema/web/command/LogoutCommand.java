package com.grubjack.cinema.web.command;

import com.grubjack.cinema.util.LocaleResourceBundle;
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
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        log.info("Executing with session id {}", request.getSession().getId());
        request.getSession().invalidate();
        LocaleResourceBundle.setFor(request);
        return new ShowScheduleCommand().execute(request, response);
    }
}
