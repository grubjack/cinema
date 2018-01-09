package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.DayOfWeek;
import com.grubjack.cinema.model.Show;
import com.grubjack.cinema.model.TimeOfDay;
import com.grubjack.cinema.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Urban Aleksandr
 */
public class CreateMovieCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(CreateMovieCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        String day = (String) req.getSession().getAttribute("day");
        String time = (String) req.getSession().getAttribute("time");
        String movie = req.getParameter("movie");
        req.getSession().setAttribute("movie", movie);
        if (day != null && !day.isEmpty() && time != null && !time.isEmpty()) {
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
            TimeOfDay timeOfDay = TimeOfDay.convert(time);
            log.info("Create movie \"{}\" day {}, time {} ", movie, day, time);
            ServiceFactory.getInstance().getShowService().create(new Show(dayOfWeek, timeOfDay, movie));
        }
        return new ShowScheduleCommand().execute(req, resp);
    }

}
