package com.grubjack.cinema.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * {@code User} user entity
 */
public class User implements Serializable {
    private int id;
    /**
     * Mark firstName of user
     */
    private String firstName;
    /**
     * Mark lastName of user
     */
    private String lastName;
    /**
     * Mark email of user.
     * It must be unique
     * There is not allowed to have the same email by a few users
     */
    private String email;
    /**
     * Mark password of user
     * It stored in encrypted view in DB (default MD5)
     */
    private String password;
    /**
     * Roles of users, set users privileges in application
     */
    private Set<Role> roles = new HashSet<>();
    /**
     * Tickets bought by user
     */
    private Set<Ticket> tickets = new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
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
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(tickets, user.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, password, roles, tickets);
    }

    public boolean hasRole(String role) {
        return roles.contains(Role.valueOf(role));
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    /**
     * Allows to add new role for user
     *
     * @param role role for adding
     */
    public void addRole(Role role) {
        roles.add(role);
    }

}
