package com.grubjack.cinema.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return Stream.of(TimeOfDay.values()).filter(t -> t.equals(string)).findFirst().get();
    }

    public static List<String> names() {
        return Stream.of(TimeOfDay.values()).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return text;
    }
}
