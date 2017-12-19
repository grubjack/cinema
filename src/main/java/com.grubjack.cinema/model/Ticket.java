package com.grubjack.cinema.model;

import java.io.Serializable;
import java.util.Objects;

public class Ticket implements Serializable {
    private Seat seat;
    private boolean sold;
    private int price;

    public Ticket() {
    }

    public Ticket(Seat seat, boolean sold, int price) {
        this.seat = seat;
        this.sold = sold;
        this.price = price;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return sold == ticket.sold &&
                price == ticket.price &&
                Objects.equals(seat, ticket.seat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seat, sold, price);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "seat=" + seat +
                ", sold=" + sold +
                ", price=" + price +
                '}';
    }
}
