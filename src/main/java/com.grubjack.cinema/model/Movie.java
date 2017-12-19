package com.grubjack.cinema.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Movie implements Serializable {
    private String title;
    private Country country;
    private String producer;
    private int year;
    private int duration;
    private Set<Genre> genres;
    private Set<Session> sessions;
    private boolean canceled;

    public Movie() {
    }

    public Movie(String title) {
        this.title = title;
        this.country = Country.OTHER;
        this.producer = "Unknown";
        this.year = LocalDate.now().getYear();
        this.genres = new HashSet<>();
        this.sessions = new HashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return year == movie.year &&
                duration == movie.duration &&
                canceled == movie.canceled &&
                Objects.equals(title, movie.title) &&
                country == movie.country &&
                Objects.equals(producer, movie.producer) &&
                Objects.equals(genres, movie.genres) &&
                Objects.equals(sessions, movie.sessions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, country, producer, year, duration, genres, sessions, canceled);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", country=" + country +
                ", producer='" + producer + '\'' +
                ", year=" + year +
                ", duration=" + duration +
                ", genres=" + genres +
                ", sessions=" + sessions +
                ", canceled=" + canceled +
                '}';
    }
}
