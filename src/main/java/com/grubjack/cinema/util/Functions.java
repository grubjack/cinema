package com.grubjack.cinema.util;

import com.grubjack.cinema.model.DayOfWeek;

/**
 * {@code ConfigManager} utility class
 * <p>
 * Provides functions for JSTL custom lib
 */
public class Functions {

    /**
     * Converts all of the characters of input object to lower case
     *
     * @param dayOfWeek input day of week
     * @return input dayOfWeek in lower case string
     */
    public static String toLowerCase(DayOfWeek dayOfWeek) {
        return dayOfWeek.name().toLowerCase();
    }
}
