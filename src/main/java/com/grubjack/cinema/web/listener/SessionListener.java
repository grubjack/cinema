package com.grubjack.cinema.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * {@code SessionListener} http session listener
 * Keeps track the total number of active sessions in a web application
 */
public class SessionListener implements HttpSessionListener {
    private static Logger log = LoggerFactory.getLogger(SessionListener.class);
    private int sessionCount = 0;

    /**
     * Write to log creating new client http session
     * Write total numbers of active sessions
     *
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        synchronized (this) {
            sessionCount++;
        }
        log.info("Session with id {} is created", se.getSession().getId());
        log.info("Total Sessions: {}", sessionCount);
    }

    /**
     * Write to log deleting client http session
     * Write total numbers of active sessions
     *
     * @param se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        synchronized (this) {
            sessionCount--;
        }
        log.info("Session with id {} is destroyed", se.getSession().getId());
        log.info("Total Sessions: {}", sessionCount);
    }

}
