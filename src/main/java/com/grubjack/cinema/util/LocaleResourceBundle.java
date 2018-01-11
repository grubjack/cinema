package com.grubjack.cinema.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * {@code LocaleResourceBundle} utility class
 * <p>
 * Manages http client locale using bundle with localization messages
 * <p>
 * Save instance of {@code LocaleResourceBundle} to client http session
 */
public class LocaleResourceBundle extends ResourceBundle {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(LocaleResourceBundle.class);
    /**
     * Name of attribute in client http session
     */
    private static final String MESSAGE_ATTRIBUTE_NAME = "lang";
    /**
     * Name of bundle with localization messages
     */
    private static final String MESSAGE_BASE_NAME = "message/message";


    private LocaleResourceBundle(Locale locale) {
        setLocale(locale);
    }

    /**
     * Store instance of class with bundle related with client locale
     *
     * @param request http request
     */
    public static void setFor(HttpServletRequest request) {
        if (request.getSession().getAttribute(MESSAGE_ATTRIBUTE_NAME) == null) {
            request.getSession().setAttribute(MESSAGE_ATTRIBUTE_NAME, new LocaleResourceBundle(request.getLocale()));
        }
    }

    /**
     * Set bundle related with user locale
     *
     * @param locale
     */
    public void setLocale(Locale locale) {
        if (parent == null || !parent.getLocale().equals(locale)) {
            log.info("Set locale {} ", locale);
            setParent(getBundle(MESSAGE_BASE_NAME, locale));
        }
    }

    /**
     * Retrieve instance from client http session
     *
     * @param request client http request
     * @return instance of {@code LocaleResourceBundle}
     */
    public static LocaleResourceBundle getInstance(HttpServletRequest request) {
        return (LocaleResourceBundle) request.getSession().getAttribute(MESSAGE_ATTRIBUTE_NAME);
    }

    @Override
    public Enumeration<String> getKeys() {
        return parent.getKeys();
    }

    @Override
    protected Object handleGetObject(String key) {
        return parent.getObject(key);
    }
}
