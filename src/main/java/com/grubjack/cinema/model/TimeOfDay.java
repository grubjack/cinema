package com.grubjack.cinema.model;

import java.util.ArrayList;
import java.util.List;

public enum TimeOfDay {
    FIRST("09:00"),
    SECOND("11:30"),
    THIRD("14:00"),
    FOURTH("17:30"),
    FIFTH("20:00"),
    SIXTH("22:00");

    private final String text;

    TimeOfDay(String text) {
        this.text = text;
    }

    public static TimeOfDay convert(String string) {
        for (TimeOfDay time : TimeOfDay.values()) {
            if (time.toString().equals(string))
                return time;
        }
        return null;
    }

    public static List<String> names() {
        List<String> names = new ArrayList();
        for (TimeOfDay time : TimeOfDay.values()) {
            names.add(time.toString());
        }
        return names;
    }

    @Override
    public String toString() {
        return text;
    }
}
