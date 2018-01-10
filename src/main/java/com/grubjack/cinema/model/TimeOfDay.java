package com.grubjack.cinema.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code TimeOfDay} is an enum representing the time movie sessions of the day
 */

public enum TimeOfDay {
    FIRST("09:00"),
    SECOND("11:30"),
    THIRD("14:00"),
    FOURTH("17:30"),
    FIFTH("20:00"),
    SIXTH("22:00");

    /**
     * The value is used for mark time of start session
     */
    private final String text;

    TimeOfDay(String text) {
        this.text = text;
    }

    /**
     * Method allows to convert time of start session to related instance of enum
     * @param string time of start session
     * @return the name of this enum constant
     */

    public static TimeOfDay convert(String string) {
        for (TimeOfDay time : TimeOfDay.values()) {
            if (time.toString().equals(string))
                return time;
        }
        return null;
    }

    /**
     * Returns list of text names all enum values
     * @return the list of names of all enum constants
     */
    public static List<String> names() {
        List<String> names = new ArrayList();
        for (TimeOfDay time : TimeOfDay.values()) {
            names.add(time.toString());
        }
        return names;
    }

    /**
     * Returns value of start time
     * @return value of start time
     */
    @Override
    public String toString() {
        return text;
    }
}
