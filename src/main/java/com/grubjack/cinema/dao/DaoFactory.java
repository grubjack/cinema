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
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Urban Aleksandr
 */
public final class DaoFactory {
    private static final String PROPERTIES_FILE = "db/mysql.properties";
    private static final String DATASOURCE_CONTEXT_PATH = "java:/comp/env/jdbc/CinemaDB";
    private static final String DATABASE_DRIVER = "database.driver";
    private static final String DATABASE_URL = "database.url";
    private static final String DATABASE_USERNAME = "database.username";
    private static final String DATABASE_PASSWORD = "database.password";
    private static final Properties PROPERTIES = new Properties();
    private static DaoFactory instance;

    private static Logger log = LoggerFactory.getLogger(DaoFactory.class);

    private DaoFactory() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup(DATASOURCE_CONTEXT_PATH);
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

    public static Connection getDirectConnection() {
        Connection connection = null;
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE);

        if (inputStream != null) {

            try {
                PROPERTIES.load(inputStream);
            } catch (IOException e) {
                log.error("Can't load property file", e);
            }
            try {
                Class.forName(PROPERTIES.getProperty(DATABASE_DRIVER));
            } catch (ClassNotFoundException e) {
                log.error("Can't load class for name", e);
            }

            try {
                String url = PROPERTIES.getProperty(DATABASE_URL);
                String username = PROPERTIES.getProperty(DATABASE_USERNAME);
                String password = PROPERTIES.getProperty(DATABASE_PASSWORD);
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                log.error("Can't get connection", e);
            }
        } else {
            log.error("Can't get input stream from property file");
        }
        return connection;
    }

    public ShowDao getShowDao() {
        return new ShowDaoImpl();
    }

    public TicketDao getTicketDao() {
        return new TicketDaoImpl();
    }

    public UserDao getUserDao() {
        return new UserDaoImpl();
    }
}
