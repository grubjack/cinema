package com.grubjack.cinema.model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Session implements Serializable {
    private DayOfWeek dayOfWeek;
    private TimeOfDay timeOfDay;
    private Hall hall;
    private Set<Ticket> tickets;

    public Session() {
    }

    public Session(DayOfWeek dayOfWeek, TimeOfDay timeOfDay, Hall hall) {
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
        this.hall = hall;
        this.tickets = new HashSet<>();
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

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
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
        Session session = (Session) o;
        return dayOfWeek == session.dayOfWeek &&
                timeOfDay == session.timeOfDay &&
                Objects.equals(hall, session.hall) &&
                Objects.equals(tickets, session.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, timeOfDay, hall, tickets);
    }

    @Override
    public String toString() {
        return "Session{" +
                "dayOfWeek=" + dayOfWeek +
                ", timeOfDay=" + timeOfDay +
                ", hall=" + hall +
                ", tickets=" + tickets +
                '}';
    }
}
