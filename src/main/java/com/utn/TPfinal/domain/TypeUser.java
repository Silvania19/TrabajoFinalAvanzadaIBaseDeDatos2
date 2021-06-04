package com.utn.TPfinal.domain;

public enum TypeUser {

    CLIENT("client"),
    EMPLOYEE("employee");

    private String description;

    TypeUser(String description)
    {
        this.description = description;
    }

    public static TypeUser find(final String value) {
        for (TypeUser v : values()) {
            if (v.toString().equalsIgnoreCase(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid TypeUser: %s", value));
    }

    public String getDescription() {
        return description;
    }
}
