package com.grubjack.cinema.web.command;

import com.grubjack.cinema.util.LocaleResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static com.grubjack.cinema.util.ConfigManager.FROM_PARAM;
import static com.grubjack.cinema.util.ConfigManager.LANGUAGE_PARAM;

/**
 * Created by Urban Aleksandr
 */
public class ChangeLocaleCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(ChangeLocaleCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String lang = request.getParameter(LANGUAGE_PARAM);
        log.info("Change locale to {}", lang);
        LocaleResourceBundle.getInstance(request).setLocale(new Locale(lang));
        return request.getParameter(FROM_PARAM) == null ? new ShowScheduleCommand().execute(request, response) : request.getParameter(FROM_PARAM).substring(request.getContextPath().length());
    }
}