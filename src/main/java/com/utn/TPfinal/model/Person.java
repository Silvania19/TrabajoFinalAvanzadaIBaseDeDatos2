package com.utn.TPfinal.model;

public abstract class Person {

    private Integer id_person;
    private String name;
    private String last_name;

    public Person(Integer id_person, String name, String last_name) {
        this.id_person = id_person;
        this.name = name;
        this.last_name = last_name;
    }

    public Person() {
    }

    public Integer getId_person() {
        return id_person;
    }

    public void setId_person(Integer id_person) {
        this.id_person = id_person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
