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
    public static final String DAY_PARAM = "day";
    public static final String TIME_PARAM = "time";
    public static final String ROLES_ATTR = "roles";
    public static final String LOGGED_USER_ATTR = "loggedUser";
    public static final String TICKET_ID_PARAM = "ticketId";
    public static final String SHOW_ID_PARAM = "showId";
    public static final String LANGUAGE_PARAM = "language";
    public static final String FROM_PARAM = "from";
    public static final String LOGIN_PARAM = "login";
    public static final String PASSWORD_PARAM = "password";
    public static final String MOVIE_PARAM = "movie";
    public static final String USER_ID_PARAM = "userId";
    public static final String FIRSTNAME_PARAM = "firstname";
    public static final String LASTNAME_PARAM = "lastname";
    public static final String EMAIL_PARAM = "email";
    public static final String SELECTED_ROLES_PARAM = "selectedRoles";
    public static final String TICKETS_ATTR = "tickets";
    public static final String ROWS_ATTR = "rows";
    public static final String SEATS_ATTR = "seats";
    public static final String TICKET_SERVICE_ATTR = "ticketService";
    public static final String ATTENDANCE_ATTR = "attendance";
    public static final String DAYS_ATTR = "days";
    public static final String TIMES_ATTR = "times";
    public static final String SHOW_SERVICE_ATTR = "showService";
    public static final String USERS_ATTR = "users";
    public static final String ERROR_MESSAGE_ATTR = "errorMessage";

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
