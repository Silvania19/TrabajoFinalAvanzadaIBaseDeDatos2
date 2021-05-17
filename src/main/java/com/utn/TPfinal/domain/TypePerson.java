package com.utn.TPfinal.domain;

public enum TypePerson {

    CLIENT("client"),
    EMPLOYEE("employee");

    private String description;

    TypePerson(String description)
    {
        this.description = description;
    }

    public static TypePerson find(final String value) {
        for (TypePerson v : values()) {
            if (v.toString().equalsIgnoreCase(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid TypePerson: %s", value));
    }

    public String getDescription() {
        return description;
    }
}
