package com.grubjack.cinema.dao;

import com.grubjack.cinema.dao.impl.ShowDaoImpl;
import com.grubjack.cinema.dao.impl.TicketDaoImpl;
import com.grubjack.cinema.dao.impl.UserDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * {@code DaoFactory} singleton, provides access to datasource and implements
 * factory methods for creating instances of dao implementations
 */
public final class DaoFactory {
    /**
     * Path to db context in jndi
     */
    private static final String DATASOURCE_CONTEXT_PATH = "java:/comp/env/jdbc/CinemaDB";
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(DaoFactory.class);
    /**
     * Instance of singleton
     */
    private static DaoFactory instance;

    private DaoFactory() {
    }

    /**
     * Return datasource from jndi context
     *
     * @return datasource from jndi
     */
    private static DataSource getDataSource() {
        DataSource dataSource = null;
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup(DATASOURCE_CONTEXT_PATH);
        } catch (NamingException e) {
            log.error("Can't find datasource in jndi context", e);
        }
        return dataSource;
    }

    /**
     * Lazy initialing of singleton
     *
     * @return instance of singleton
     */
    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    /**
     * Return implementation of {@code ShowDao} interface for db operation with {@code Show} entity
     *
     * @return implementation of {@code ShowDao} interface
     */
    public ShowDao getShowDao() {
        return new ShowDaoImpl(getDataSource());
    }

    /**
     * Return implementation of {@code TicketDao} interface for db operation with {@code Ticket} entity
     *
     * @return implementation of {@code TicketDao} interface
     */
    public TicketDao getTicketDao() {
        return new TicketDaoImpl(getDataSource());
    }

    /**
     * Return implementation of {@code UserDao} interface for db operation with {@code User} entity
     *
     * @return implementation of {@code UserDao} interface
     */
    public UserDao getUserDao() {
        return new UserDaoImpl(getDataSource());
    }
}
