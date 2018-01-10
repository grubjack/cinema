package com.grubjack.cinema.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A day-of-week, such as 'Tuesday'.
 * <p>
 * {@code DayOfWeek} is an enum representing the 7 days of the week -
 * Monday, Tuesday, Wednesday, Thursday, Friday, Saturday and Sunday.
 * <p>
 */

public enum DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    /**
     * Returns list of text names all enum values
     *
     * @return the list of names of all enum constants
     */
    public static List<String> names() {
        List<String> names = new ArrayList();
        for (DayOfWeek day : DayOfWeek.values()) {
            names.add(day.toString());
        }
        return names;
    }

}
