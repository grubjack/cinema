package com.grubjack.cinema.model;

import java.io.Serializable;
import java.util.Objects;

public class Seat implements Serializable {
    private int row;
    private int number;
    private boolean taken;

    public Seat() {
    }

    public Seat(int row, int number) {
        this.row = row;
        this.number = number;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return row == seat.row &&
                number == seat.number &&
                taken == seat.taken;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, number, taken);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", number=" + number +
                ", taken=" + taken +
                '}';
    }
}
