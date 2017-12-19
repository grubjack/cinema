package com.grubjack.cinema.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cinema implements Serializable {

    private String title;
    private Set<Hall> halls;
    private Set<Movie> movies;

    public Cinema(String title) {
        this.title = title;
        this.halls = new HashSet<>();
        this.movies = new HashSet<>();
    }

    public Cinema() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Hall> getHalls() {
        return halls;
    }

    public void setHalls(Set<Hall> halls) {
        this.halls = halls;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(title, cinema.title) &&
                Objects.equals(halls, cinema.halls) &&
                Objects.equals(movies, cinema.movies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, halls, movies);
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "title='" + title + '\'' +
                ", halls=" + halls +
                ", movies=" + movies +
                '}';
    }
}
