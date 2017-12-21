package com.grubjack.cinema.model;

import java.io.Serializable;
import java.util.Objects;

public class Role implements Serializable {
    private int id;
    private String title;
    private String description;

    public Role(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Role() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(title, role.title) &&
                Objects.equals(description, role.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
