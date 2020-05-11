package com.cabme.passenger.entity;

import java.io.Serializable;

/**
 * Map to the Passenger table in the database. Holds various information on the passenger
 */
public class Passenger implements Serializable {
    private final long id;
    private final String firstName;
    private final String lastName;

    public Passenger(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}