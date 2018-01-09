package com.grubjack.cinema.web.command;

import com.grubjack.cinema.model.DayOfWeek;
import com.grubjack.cinema.model.TimeOfDay;
import com.grubjack.cinema.service.ServiceFactory;
import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.grubjack.cinema.util.ConfigManager.*;

/**
 * Created by Urban Aleksandr
 */
public class ShowScheduleCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(ShowScheduleCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        log.info("Executing with session id {}", request.getSession().getId());
        request.getSession().setAttribute(DAYS_ATTR, DayOfWeek.names());
        request.getSession().setAttribute(TIMES_ATTR, TimeOfDay.names());
        request.getSession().setAttribute(SHOW_SERVICE_ATTR, ServiceFactory.getInstance().getShowService());
        return ConfigManager.getInstance().getProperty(MAIN_PAGE_PATH);
    }
}