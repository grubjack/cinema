package com.grubjack.cinema.web.command;

import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.grubjack.cinema.util.ConfigManager.*;

/**
 * Created by Urban Aleksandr
 */
public class AddMovieCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(AddMovieCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        log.info("Executing with session id {}", request.getSession().getId());
        request.getSession().setAttribute(DAY_PARAM, request.getParameter(DAY_PARAM));
        request.getSession().setAttribute(TIME_PARAM, request.getParameter(TIME_PARAM));
        return ConfigManager.getInstance().getProperty(MOVIE_PAGE_PATH);
    }
}