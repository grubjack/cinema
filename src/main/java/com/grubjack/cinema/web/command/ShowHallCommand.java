package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
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
public class ShowHallCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(ShowHallCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        log.info("Executing with session id {}", request.getSession().getId());
        String showIdParameter = request.getParameter(SHOW_ID_PARAM);
        if (showIdParameter != null && !showIdParameter.isEmpty()) {
            request.getSession().setAttribute(SHOW_ID_PARAM, Integer.parseInt(showIdParameter));
        }
        int showId = (int) request.getSession().getAttribute(SHOW_ID_PARAM);
        log.info("Show tickets for session with id " + showId);
        request.getSession().setAttribute(TICKETS_ATTR, ServiceFactory.getInstance().getTicketService().findByShow(showId));
        request.getSession().setAttribute(ROWS_ATTR, ConfigManager.getInstance().getProperty(ConfigManager.HALL_ROW_VALUE));
        request.getSession().setAttribute(SEATS_ATTR, ConfigManager.getInstance().getProperty(ConfigManager.HALL_SEAT_VALUE));
        request.getSession().setAttribute(TICKET_SERVICE_ATTR, ServiceFactory.getInstance().getTicketService());
        request.getSession().setAttribute(ATTENDANCE_ATTR, ServiceFactory.getInstance().getShowService().getAttendance(showId));
        return ConfigManager.getInstance().getProperty(HALL_PAGE_PATH);
    }
}
