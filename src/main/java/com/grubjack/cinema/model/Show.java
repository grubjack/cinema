package com.grubjack.cinema.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Show implements Serializable {
    private int id;
    private DayOfWeek dayOfWeek;
    private TimeOfDay timeOfDay;
    private String movie;
    private Set<Ticket> tickets = new HashSet<>();

    public Show() {
    }

    public Show(DayOfWeek dayOfWeek, TimeOfDay timeOfDay, String movie) {
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
        this.movie = movie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return dayOfWeek == show.dayOfWeek &&
                timeOfDay == show.timeOfDay &&
                Objects.equals(movie, show.movie) &&
                Objects.equals(tickets, show.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, timeOfDay, movie, tickets);
    }
}
