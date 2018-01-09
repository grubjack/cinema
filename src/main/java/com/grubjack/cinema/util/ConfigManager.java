package com.grubjack.cinema.util;

import java.util.ResourceBundle;

/**
 * Created by Urban Aleksandr
 */
public class ConfigManager {
    public static final String ERROR_PAGE_PATH = "ERROR_PAGE_PATH";
    public static final String LOGIN_PAGE_PATH = "LOGIN_PAGE_PATH";
    public static final String MAIN_PAGE_PATH = "MAIN_PAGE_PATH";
    public static final String HALL_PAGE_PATH = "HALL_PAGE_PATH";
    public static final String REGISTER_PAGE_PATH = "REGISTER_PAGE_PATH";
    public static final String TICKETS_PAGE_PATH = "TICKETS_PAGE_PATH";
    public static final String USERS_PAGE_PATH = "USERS_PAGE_PATH";
    public static final String HALL_ROW_VALUE = "HALL_ROW_VALUE";
    public static final String HALL_SEAT_VALUE = "HALL_SEAT_VALUE";
    public static final String MOVIE_PAGE_PATH = "MOVIE_PAGE_PATH";
    public static final String TICKET_LOW_PRICE = "TICKET_LOW_PRICE";
    public static final String TICKET_MIDDLE_PRICE = "TICKET_MIDDLE_PRICE";
    public static final String TICKET_HIGH_PRICE = "TICKET_HIGH_PRICE";

    private static final String CONFIG_FILENAME = "config";
    private static ConfigManager instance;
    private ResourceBundle resourceBundle;

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
            instance.resourceBundle = ResourceBundle.getBundle(CONFIG_FILENAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }
}
