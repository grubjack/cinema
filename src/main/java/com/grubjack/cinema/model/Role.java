package com.grubjack.cinema.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Role} is an enum representing the available user roles
 */
public enum Role {
    /**
     * User can buy ticket
     */
    ROLE_USER,

    /**
     * User can add/cancel movie session, manage other user accounts
     */
    ROLE_ADMIN;

    /**
     * Returns list of text names all enum values
     *
     * @return the list of names of all enum constants
     */
    public static List<String> names() {
        List<String> names = new ArrayList();
        for (Role role : Role.values()) {
            names.add(role.toString());
        }
        return names;
    }
}