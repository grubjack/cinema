package com.grubjack.cinema.web.command;

import com.grubjack.cinema.util.LocaleResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code LogoutCommand} implementation of interface {@code Command}
 */
public class LogoutCommand implements Command {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(LogoutCommand.class);

    /**
     * Remove user session, set locale bundle by request
     *
     * @param request
     * @param response
     * @return path to schedules page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        log.info("Executing with session id {}", request.getSession().getId());
        request.getSession().invalidate();
        LocaleResourceBundle.setFor(request);
        return new ShowScheduleCommand().execute(request, response);
    }
}
