package com.grubjack.cinema.to;

import com.grubjack.cinema.model.DayOfWeek;
import com.grubjack.cinema.model.TimeOfDay;

/**
 * Created by Urban Aleksandr
 */
public class TicketWithShow {

    private int id;
    private int row;
    private int seat;
    private int price;
    private DayOfWeek dayOfWeek;
    private TimeOfDay timeOfDay;
    private String movie;


    public TicketWithShow(int id, int row, int seat, int price, DayOfWeek dayOfWeek, TimeOfDay timeOfDay, String movie) {
        this.id = id;
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
        this.movie = movie;
    }

    public int getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public int getPrice() {
        return price;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public String getMovie() {
        return movie;
    }
}
