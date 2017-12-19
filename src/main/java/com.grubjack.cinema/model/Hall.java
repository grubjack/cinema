package com.grubjack.cinema.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Hall implements Serializable {
    private String name;
    private int capacity;
    private String location;
    private Set<Seat> seats;

    public Hall() {
    }

    public Hall(String name, int capacity, String location) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.seats = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return capacity == hall.capacity &&
                Objects.equals(name, hall.name) &&
                Objects.equals(location, hall.location) &&
                Objects.equals(seats, hall.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capacity, location, seats);
    }

    @Override
    public String toString() {
        return "Hall{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                ", location='" + location + '\'' +
                ", seats=" + seats +
                '}';
    }
}
