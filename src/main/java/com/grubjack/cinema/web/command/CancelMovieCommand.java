package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Urban Aleksandr
 */
public class CancelMovieCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(CancelMovieCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) throws DaoException {
        log.info("Executing with session id {}", req.getSession().getId());
        String showId = req.getParameter("showId");
        if (showId != null && !showId.isEmpty()) {
            log.info("Delete show with id {}", Integer.parseInt(showId));
            ServiceFactory.getInstance().getShowService().delete(Integer.parseInt(showId));
        }
        return new ShowScheduleCommand().execute(req, response);
    }
}
