package com.cabme.driver.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Map to the driver table in the database. Holds various information about the Driver
 */
public class Driver implements Serializable {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String medallion;

    public Driver(long id, String firstName, String lastName, String medallion) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.medallion = medallion;
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

    public String getMedallion() {
        return medallion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id == driver.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, medallion);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", medallion='" + medallion + '\'' +
                '}';
    }
}