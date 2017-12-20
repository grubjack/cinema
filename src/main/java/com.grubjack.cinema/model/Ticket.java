package com.grubjack.cinema.model;

import java.io.Serializable;
import java.util.Objects;

public class Ticket implements Serializable {
    private int row;
    private int seat;
    private int price;
    private boolean sold;

    public Ticket() {
    }

    public Ticket(int row, int seat, int price) {
        this.row = row;
        this.seat = seat;
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return row == ticket.row &&
                seat == ticket.seat &&
                price == ticket.price &&
                sold == ticket.sold;
    }

    @Override
    public int hashCode() {

        return Objects.hash(row, seat, price, sold);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "row=" + row +
                ", seat=" + seat +
                ", price=" + price +
                ", sold=" + sold +
                '}';
    }
}
