package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.service.ServiceFactory;
import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Urban Aleksandr
 */
public class ShowHallCommand implements Command {

    private static Logger log = LoggerFactory.getLogger(ShowHallCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        log.info("Executing with session id {}", req.getSession().getId());
        String page = ConfigManager.getInstance().getProperty(ConfigManager.HALL_PAGE_PATH);
        String showIdParameter = req.getParameter("showId");
        if (showIdParameter != null && !showIdParameter.isEmpty()) {
            req.getSession().setAttribute("showId", Integer.parseInt(showIdParameter));
        } else {
        }
        int showId = (int) req.getSession().getAttribute("showId");

        log.info("Show tickets for session with id " + showId);
        req.getSession().setAttribute("tickets", ServiceFactory.getInstance().getTicketService().findByShow(showId));
        req.getSession().setAttribute("rows", ConfigManager.getInstance().getProperty(ConfigManager.HALL_ROW_VALUE));
        req.getSession().setAttribute("seats", ConfigManager.getInstance().getProperty(ConfigManager.HALL_SEAT_VALUE));
        req.getSession().setAttribute("ticketService", ServiceFactory.getInstance().getTicketService());
        req.getSession().setAttribute("attendance", ServiceFactory.getInstance().getShowService().getAttendance(showId));
        req.getSession().setAttribute("sourceCommand", this);
        return page;
    }
}
