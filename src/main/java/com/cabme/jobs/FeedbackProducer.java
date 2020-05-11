package com.cabme.jobs;

import com.cabme.category.Category;
import com.cabme.driver.entity.Driver;
import com.cabme.api.feedback.FeedbackController;
import com.cabme.geocode.Geocode;
import com.cabme.passenger.entity.Passenger;
import com.cabme.ride.entity.Ride;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * This is used to simulate the data from a client
 */
public class FeedbackProducer extends TimerTask {
    private final Driver d1 = new Driver(1, "Becky", "Little", "EZ4DaNoo");
    private final Driver d2 = new Driver(2, "Michael", "Yee", "oich1Iegh");
    private final Driver d3 = new Driver(3, "William", "Maloof", "Eiboh9aeC");
    private final Driver d4 = new Driver(4, "Michael", "Hernandez", "sail6ahW9");
    private final Driver d5 = new Driver(5, "Martin", "Black", "vijaew2Ah");
    private final Driver d6 = new Driver(6, "Jeremy", "Martinez", "mu9shieGh0ch");
    private final Driver d7 = new Driver(7, "Jonathan", "Upshaw", "itoJash5");
    private final Driver d8 = new Driver(8, "Rafaela", "Corbin", "iiph3eiN1sh");
    private final Driver d9 = new Driver(9, "Dawn", "Henriksen", "Eingei8Ae");
    private final Driver d10 = new Driver(10, "Milton", "Montgomery", "shaes0ieNg6");
    private final Driver d11 = new Driver(11, "Heather", "Craig", "oSu7eeNg3");
    private final List<Driver> drivers = Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11);

    private final Passenger p1 = new Passenger(1, "Paul", "McManus");
    private final Passenger p2 = new Passenger(2, "Greg", "Johnson");
    private final Passenger p3 = new Passenger(3, "Jacqualine", "Santos");
    private final Passenger p4 = new Passenger(4, "Sheena", "McPherson");
    private final Passenger p5 = new Passenger(5, "Chester", "Cheeks");
    private final Passenger p6 = new Passenger(6, "Kathy", "King");
    private final Passenger p7 = new Passenger(7, "Ralph ", "Robertson");
    private final Passenger p8 = new Passenger(8, "Jacob", "Brown");
    private final Passenger p9 = new Passenger(9, "Deanna", "Reed");
    private final Passenger p10 = new Passenger(10, "Clarence", "Caldwell");
    private final List<Passenger> passengers = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);

    private final Random random = new Random(System.currentTimeMillis());

    private Driver getRandomDriver() {
        return drivers.get(random.nextInt(drivers.size()));
    }

    private Passenger getRandomPassenger() {
        return passengers.get(random.nextInt(passengers.size()));
    }

    private FeedbackController feedbackController = new FeedbackController();
    @Override
    public void run() {
        final Driver driver = getRandomDriver();
        final Passenger passenger = getRandomPassenger();
        final Geocode start = new Geocode(new BigDecimal(random.nextFloat()), new BigDecimal(random.nextFloat()));
        final Geocode end = new Geocode(new BigDecimal(random.nextFloat()), new BigDecimal(random.nextFloat()));
        final long odomStart = Math.abs(random.nextLong());
        final long odomEnd = odomStart + Math.abs(random.nextInt());
        final LocalDateTime localDateTime = LocalDateTime.now();
        final ZonedDateTime startDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
        final ZonedDateTime endDateTime = startDateTime.plusMinutes(Math.abs(random.nextInt()));
        final Ride ride = new Ride(random.nextInt(), driver, passenger, start, startDateTime, odomStart);
        ride.setEndLoc(end);
        ride.setTripEnd(endDateTime);
        ride.setOdometerEnd(odomEnd);

        final Set<Category> categories = new HashSet<>();
        Arrays.stream(Category.values()).forEach(c -> {
                    if (random.nextBoolean()) {
                        categories.add(c);

                    }
                }
        );

//        System.out.format("%s: Generating feedback for driver: %s, # categories: %d\n", new Date(), ride.getDriver(), categories.size());
        feedbackController.postFeedback(categories, ride);
    }
}
