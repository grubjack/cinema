package com.grubjack.cinema.dao;

import com.grubjack.cinema.dao.impl.RoleDaoImpl;
import com.grubjack.cinema.dao.impl.SessionDaoImpl;
import com.grubjack.cinema.dao.impl.TicketDaoImpl;
import com.grubjack.cinema.dao.impl.UserDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Urban Aleksandr
 */
public final class DaoFactory {
    private static DaoFactory instance;

    private static Logger log = LoggerFactory.getLogger(DaoFactory.class);

    private DaoFactory() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/CinemaDB");
            connection = dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            log.error("Can't find datasource in jndi context", e);
        }
        return connection;
    }

    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }


    public RoleDao getRoleDao() {
        return new RoleDaoImpl();
    }

    public SessionDao getSessionDao() {
        return new SessionDaoImpl();
    }

    public TicketDao getTicketDao() {
        return new TicketDaoImpl();
    }

    public UserDao getUserDao() {
        return new UserDaoImpl();
    }
}
