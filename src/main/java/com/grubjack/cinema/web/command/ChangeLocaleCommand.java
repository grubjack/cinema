package com.grubjack.cinema.web.command;

import com.grubjack.cinema.util.ConfigManager;
import com.grubjack.cinema.util.LocaleResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static com.grubjack.cinema.util.ConfigManager.*;

/**
 * {@code ChangeLocaleCommand} implementation of interface {@code Command}
 */
public class ChangeLocaleCommand implements Command {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(ChangeLocaleCommand.class);

    /**
     * Get lang parameter from request and change locale bundle
     *
     * @param request
     * @param response
     * @return forward to previous page using fromPage parameter
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String lang = request.getParameter(LANGUAGE_PARAM);
        log.info("Change locale to {}", lang);
        LocaleResourceBundle.getInstance(request).setLocale(new Locale(lang));
        String fromPage = request.getParameter(FROM_PARAM);
        log.info("referer page:  {}", fromPage);
        return (fromPage != null) && !fromPage.endsWith(ConfigManager.getInstance().getProperty(ERROR_PAGE_PATH)) ? fromPage.substring(request.getContextPath().length()) : ConfigManager.getInstance().getProperty(LOGIN_PAGE_PATH);
    }
}