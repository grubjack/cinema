package com.grubjack.cinema.model;

import java.util.ArrayList;
import java.util.List;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    public static List<String> names() {
        List<String> names = new ArrayList();
        for (Role role : Role.values()) {
            names.add(role.toString());
        }
        return names;
    }
}