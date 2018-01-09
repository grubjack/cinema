package com.grubjack.cinema.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Urban Aleksandr
 */
public class LocalePropertyManager extends ResourceBundle {
    private static Logger log = LoggerFactory.getLogger(LocalePropertyManager.class);
    public static final String MESSAGE_ATTRIBUTE_NAME = "lang";
    private static final String MESSAGE_BASE_NAME = "message/message";

    public LocalePropertyManager(Locale locale) {
        log.info("Set locale {} ", locale);
        setLocale(locale);
    }

    public static void setFor(HttpServletRequest request) {
        if (request.getSession().getAttribute(MESSAGE_ATTRIBUTE_NAME) == null) {
            request.getSession().setAttribute(MESSAGE_ATTRIBUTE_NAME, new LocalePropertyManager(request.getLocale()));
        }
    }

    public void setLocale(Locale locale) {
        if (parent == null || !parent.getLocale().equals(locale)) {
            setParent(getBundle(MESSAGE_BASE_NAME, locale));
        }
    }

    public static LocalePropertyManager getInstance(HttpServletRequest request) {
        return (LocalePropertyManager) request.getSession().getAttribute(MESSAGE_ATTRIBUTE_NAME);
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
