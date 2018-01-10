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
 * Created by Urban Aleksandr
 */
public final class DaoFactory {
    private static final String DATASOURCE_CONTEXT_PATH = "java:/comp/env/jdbc/CinemaDB";
    private static Logger log = LoggerFactory.getLogger(DaoFactory.class);
    private static DaoFactory instance;

    private DaoFactory() {
    }

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

    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    public ShowDao getShowDao() {
        return new ShowDaoImpl(getDataSource());
    }

    public TicketDao getTicketDao() {
        return new TicketDaoImpl(getDataSource());
    }

    public UserDao getUserDao() {
        return new UserDaoImpl(getDataSource());
    }
}
