package com.grubjack.cinema.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Urban Aleksandr
 */
public class SessionListener implements HttpSessionListener {
    private static Logger log = LoggerFactory.getLogger(SessionListener.class);
    private int sessionCount = 0;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        synchronized (this) {
            sessionCount++;
        }
        log.info("Session with id {} is created", se.getSession().getId());
        log.info("Total Sessions: {}", sessionCount);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        synchronized (this) {
            sessionCount--;
        }
        log.info("Session with id {} is destroyed", se.getSession().getId());
        log.info("Total Sessions: {}", sessionCount);
    }

}
