package com.cabme.ride.entity;

import com.cabme.driver.entity.Driver;
import com.cabme.geocode.Geocode;
import com.cabme.passenger.entity.Passenger;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Map to the Ride table in the database. Various details on the ride that was completed.
 */
public class Ride implements Serializable {
    private final long id;
    private final Driver driver;
    private final Passenger passenger;
    private final Geocode startLoc;
    private Geocode endLoc;
    private final ZonedDateTime tripStart;
    private ZonedDateTime tripEnd;
    private final long odometerStart;
    private long odometerEnd;
    private BigDecimal fareAmount;
    private String fareCurrency;

    public Ride(long id, Driver driver, Passenger passenger, Geocode startLoc, ZonedDateTime tripStart, long odometerStart) {
        this.id = id;
        this.driver = driver;
        this.passenger = passenger;
        this.startLoc = startLoc;
        this.tripStart = tripStart;
        this.odometerStart = odometerStart;
    }

    public void setEndLoc(Geocode endLoc) {
        this.endLoc = endLoc;
    }

    public void setTripEnd(ZonedDateTime tripEnd) {
        this.tripEnd = tripEnd;
    }

    public void setOdometerEnd(long odometerEnd) {
        this.odometerEnd = odometerEnd;
    }

    public void setFareAmount(BigDecimal fareAmount) {
        this.fareAmount = fareAmount;
    }

    public void setFareCurrency(String fareCurrency) {
        this.fareCurrency = fareCurrency;
    }

    public long getId() {
        return id;
    }

    public Driver getDriver() {
        return driver;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Geocode getStartLoc() {
        return startLoc;
    }

    public Geocode getEndLoc() {
        return endLoc;
    }

    public ZonedDateTime getTripStart() {
        return tripStart;
    }

    public ZonedDateTime getTripEnd() {
        return tripEnd;
    }

    public long getOdometerStart() {
        return odometerStart;
    }

    public long getOdometerEnd() {
        return odometerEnd;
    }

    public BigDecimal getFareAmount() {
        return fareAmount;
    }

    public String getFareCurrency() {
        return fareCurrency;
    }
}
