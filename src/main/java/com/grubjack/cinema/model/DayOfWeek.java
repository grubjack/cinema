package com.grubjack.cinema.model;

import java.util.ArrayList;
import java.util.List;

public enum DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static List<String> names() {
        List<String> names = new ArrayList();
        for (DayOfWeek day : DayOfWeek.values()) {
            names.add(day.toString());
        }
        return names;
    }

}
