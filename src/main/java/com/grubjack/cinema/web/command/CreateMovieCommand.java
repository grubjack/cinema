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

import static com.grubjack.cinema.util.ConfigManager.*;

/**
 * {@code CreateMovieCommand} implementation of interface {@code Command}
 */
public class CreateMovieCommand implements Command {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(CreateMovieCommand.class);

    /**
     * Get day,time and movie title parameters from request
     * Save movie parameter in session
     * <p>
     * Create new movie show
     *
     * @param request
     * @param response
     * @return path to schedules page
     * @throws DaoException exception for dao operations
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String day = (String) request.getSession().getAttribute(DAY_PARAM);
        String time = (String) request.getSession().getAttribute(TIME_PARAM);
        String movie = request.getParameter(MOVIE_PARAM);
        request.getSession().setAttribute(MOVIE_PARAM, movie);
        if (day != null && !day.isEmpty() && time != null && !time.isEmpty()) {
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
            TimeOfDay timeOfDay = TimeOfDay.convert(time);
            log.info("Create movie \"{}\" day {}, time {} ", movie, day, time);
            ServiceFactory.getInstance().getShowService().create(new Show(dayOfWeek, timeOfDay, movie));
        }
        return new ShowScheduleCommand().execute(request, response);
    }

}
