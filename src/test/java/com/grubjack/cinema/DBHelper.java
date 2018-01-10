package com.grubjack.cinema;

import org.hsqldb.jdbc.JDBCDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Urban Aleksandr
 */
public class DBHelper {
    private static Logger log = LoggerFactory.getLogger(DBHelper.class);
    private static final String PROPERTIES_FILE = "db/hsqldb.properties";
    private static final String DATABASE_URL = "database.url";
    private static final String DATABASE_USERNAME = "database.username";
    private static final String DATABASE_PASSWORD = "database.password";
    private static final String INIT_DB_SCRIPT = "jdbc.initLocation";
    private static final String POPULATE_DB_SCRIPT = "jdbc.populateLocation";
    private static final Properties PROPERTIES = new Properties();

    private static JDBCDataSource dataSource = new JDBCDataSource();

    static {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE);
        if (inputStream != null) {
            try {
                PROPERTIES.load(inputStream);
            } catch (IOException e) {
                log.error("Can't load property file", e);
            }
            dataSource.setURL(PROPERTIES.getProperty(DATABASE_URL));
            dataSource.setUser(PROPERTIES.getProperty(DATABASE_USERNAME));
            dataSource.setPassword(PROPERTIES.getProperty(DATABASE_PASSWORD));
        } else {
            log.error("Can't get input stream from property file");
        }
    }

    public static void setUpDatabase() {
        try {
            SqlRunner.runScript(dataSource.getConnection(), Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES.getProperty(INIT_DB_SCRIPT)));
            SqlRunner.runScript(dataSource.getConnection(), Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES.getProperty(POPULATE_DB_SCRIPT)));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

}
