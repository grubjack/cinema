package com.grubjack.cinema.web.command;

import com.grubjack.cinema.model.DayOfWeek;
import com.grubjack.cinema.model.TimeOfDay;
import com.grubjack.cinema.service.ServiceFactory;
import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Urban Aleksandr
 */
public class ShowScheduleCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(ShowScheduleCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Executing with session id {}", req.getSession().getId());
        req.getSession().setAttribute("days", DayOfWeek.names());
        req.getSession().setAttribute("times", TimeOfDay.names());
        req.getSession().setAttribute("showService", ServiceFactory.getInstance().getShowService());
        return ConfigManager.getInstance().getProperty(ConfigManager.MAIN_PAGE_PATH);
    }
}