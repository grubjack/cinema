package com.grubjack.cinema.web.command;

import com.grubjack.cinema.util.LocaleResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Urban Aleksandr
 */
public class ChangeLocaleCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(ChangeLocaleCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String lang = req.getParameter("language");
        log.info("Change locale to {}", lang);
        LocaleResourceBundle.getInstance(req).setLocale(new Locale(lang));
        return req.getParameter("from") == null ? new ShowScheduleCommand().execute(req, resp) : req.getParameter("from").substring(req.getContextPath().length());
    }
}