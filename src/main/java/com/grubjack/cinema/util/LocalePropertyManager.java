package com.grubjack.cinema.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Urban Aleksandr
 */
public class LocalePropertyManager extends ResourceBundle {
    public static final String MESSAGE_ATTRIBUTE_NAME = "locale";
    private static final String MESSAGE_BASE_NAME = "message/message";

    private LocalePropertyManager(Locale locale) {
        setLocale(locale);
    }

    public static void setFor(HttpServletRequest request) {
        if (request.getAttribute(MESSAGE_ATTRIBUTE_NAME) == null) {
            request.setAttribute(MESSAGE_ATTRIBUTE_NAME, new LocalePropertyManager(request.getLocale()));
        }
    }

    public void setLocale(Locale locale) {
        if (parent == null || !parent.getLocale().equals(locale)) {
            setParent(getBundle(MESSAGE_BASE_NAME, locale));
        }
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
