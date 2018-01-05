package com.grubjack.cinema.web.command;

import com.grubjack.cinema.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Urban Aleksandr
 */
public class AddMovieCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(AddMovieCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) {
        log.info("Executing with session id {}", req.getSession().getId());
        req.getSession().setAttribute("day",req.getParameter("day"));
        req.getSession().setAttribute("time",req.getParameter("time"));
        return ConfigManager.getInstance().getProperty(ConfigManager.MOVIE_PAGE_PATH);
    }
}