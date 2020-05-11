package com.cabme.feedback;

import com.cabme.category.Category;
import com.cabme.driver.entity.Driver;
import com.cabme.feedback.entity.FeedbackSummary;
import com.cabme.geocode.Geocode;
import com.cabme.passenger.entity.Passenger;
import com.cabme.ride.entity.Ride;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;

public class ApplicationTest {
    private FeedbackService feedbackService = FeedbackServiceImpl.getInstance();
    private FeedbackCollectorService feedbackCollectorService = FeedbackCollectorServiceImpl.getInstance();

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

    private final Passenger p1 = new Passenger(1, "Paul", "McManus");

    public void testFeedbackSummaryForOneRideOneCategory() {
        final Geocode start = new Geocode(new BigDecimal(33.9415889), new BigDecimal(-118.4107187));
        final Geocode end = new Geocode(new BigDecimal(34.0326467), new BigDecimal(-118.4602074));
        final long odomStart = 12345;
        final long odomEnd = 12355;
        final LocalDateTime localDateTime = LocalDateTime.now();
        final ZonedDateTime startDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
        final ZonedDateTime endDateTime = startDateTime.plusMinutes(30);
        final Ride ride = new Ride(1, d1, p1, start, startDateTime, odomStart);
        ride.setEndLoc(end);
        ride.setTripEnd(endDateTime);
        ride.setOdometerEnd(odomEnd);

        feedbackService.addFeedback(Set.of(Category.CLEAN), ride);
        feedbackCollectorService.processDriverFeedback();
        Map<Category, Set<FeedbackSummary>> categoryFeedbackSummary = feedbackService.getCategoryFeedbackSummary();

        if (categoryFeedbackSummary.get(Category.CLEAN).iterator().next().getCount() != 1) {
            System.err.println("should have an entry of 1, but we don't");
            throw new RuntimeException("error");
        }

        System.out.println("test passed");
    }

    public void testFeedbackSummaryForOneRideMultipleCategories() {
        final Geocode start = new Geocode(new BigDecimal(33.9415889), new BigDecimal(-118.4107187));
        final Geocode end = new Geocode(new BigDecimal(34.0326467), new BigDecimal(-118.4602074));
        final long odomStart = 12345;
        final long odomEnd = 12355;
        final LocalDateTime localDateTime = LocalDateTime.now();
        final ZonedDateTime startDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
        final ZonedDateTime endDateTime = startDateTime.plusMinutes(30);
        final Ride ride = new Ride(1, d1, p1, start, startDateTime, odomStart);
        ride.setEndLoc(end);
        ride.setTripEnd(endDateTime);
        ride.setOdometerEnd(odomEnd);

        feedbackService.addFeedback(Set.of(Category.CLEAN, Category.FRIENDLY), ride);

        feedbackCollectorService.processDriverFeedback();
        Map<Category, Set<FeedbackSummary>> categoryFeedbackSummary = feedbackService.getCategoryFeedbackSummary();

        if (categoryFeedbackSummary.get(Category.CLEAN).iterator().next().getCount() != 1) {
            System.err.println("should have an entry of 1, but we don't");
            throw new RuntimeException("error");
        }

        if (categoryFeedbackSummary.get(Category.FRIENDLY).iterator().next().getCount() != 1) {
            System.err.println("should have an entry of 1, but we don't");
            throw new RuntimeException("error");
        }

        System.out.println("test passed");
    }

    public void testFeedbackSummaryForMultipleRidesMultipleCategories() {
        final Geocode start = new Geocode(new BigDecimal(33.9415889), new BigDecimal(-118.4107187));
        final Geocode end = new Geocode(new BigDecimal(34.0326467), new BigDecimal(-118.4602074));
        final long odomStart = 12345;
        final long odomEnd = 12355;
        final LocalDateTime localDateTime = LocalDateTime.now();
        final ZonedDateTime startDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
        final ZonedDateTime endDateTime = startDateTime.plusMinutes(30);
        final Ride ride = new Ride(1, d1, p1, start, startDateTime, odomStart);
        ride.setEndLoc(end);
        ride.setTripEnd(endDateTime);
        ride.setOdometerEnd(odomEnd);

        feedbackService.addFeedback(Set.of(Category.CLEAN, Category.FRIENDLY), ride);

        final Ride ride2 = new Ride(2, d1, p1, start, startDateTime, odomStart);
        feedbackService.addFeedback(Set.of(Category.CLEAN, Category.PROMPT), ride2);

        feedbackCollectorService.processDriverFeedback();
        Map<Category, Set<FeedbackSummary>> categoryFeedbackSummary = feedbackService.getCategoryFeedbackSummary();

        if (categoryFeedbackSummary.get(Category.CLEAN).iterator().next().getCount() != 2) {
            System.err.println("should have an entry of 2, but we don't");
            throw new RuntimeException("error");
        }

        if (categoryFeedbackSummary.get(Category.FRIENDLY).iterator().next().getCount() != 1) {
            System.err.println("should have an entry of 1, but we don't");
            throw new RuntimeException("error");
        }

        if (categoryFeedbackSummary.get(Category.PROMPT).iterator().next().getCount() != 1) {
            System.err.println("should have an entry of 1, but we don't");
            throw new RuntimeException("error");
        }

        System.out.println("test passed");
    }

    public void testFeedbackSummaryForMultipleProcesses() {
        final Geocode start = new Geocode(new BigDecimal(33.9415889), new BigDecimal(-118.4107187));
        final Geocode end = new Geocode(new BigDecimal(34.0326467), new BigDecimal(-118.4602074));
        final long odomStart = 12345;
        final long odomEnd = 12355;
        final LocalDateTime localDateTime = LocalDateTime.now();
        final ZonedDateTime startDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
        final ZonedDateTime endDateTime = startDateTime.plusMinutes(30);
        final Ride ride = new Ride(1, d1, p1, start, startDateTime, odomStart);
        ride.setEndLoc(end);
        ride.setTripEnd(endDateTime);
        ride.setOdometerEnd(odomEnd);

        feedbackService.addFeedback(Set.of(Category.CLEAN, Category.FRIENDLY), ride);
        feedbackCollectorService.processDriverFeedback();

        final Ride ride2 = new Ride(2, d1, p1, start, startDateTime, odomStart);
        feedbackService.addFeedback(Set.of(Category.CLEAN, Category.PROMPT), ride2);

        feedbackCollectorService.processDriverFeedback();
        Map<Category, Set<FeedbackSummary>> categoryFeedbackSummary = feedbackService.getCategoryFeedbackSummary();

        if (categoryFeedbackSummary.get(Category.CLEAN).iterator().next().getCount() != 2) {
            System.err.println("should have an entry of 2, but found " + categoryFeedbackSummary.get(Category.CLEAN).iterator().next().getCount());
            throw new RuntimeException("error");
        }

        if (categoryFeedbackSummary.get(Category.FRIENDLY).iterator().next().getCount() != 1) {
            System.err.println("should have an entry of 1, but found " + categoryFeedbackSummary.get(Category.FRIENDLY).iterator().next().getCount());
            throw new RuntimeException("error");
        }

        if (categoryFeedbackSummary.get(Category.PROMPT).iterator().next().getCount() != 1) {
            System.err.println("should have an entry of 1, but found " + categoryFeedbackSummary.get(Category.PROMPT).iterator().next().getCount());
            throw new RuntimeException("error");
        }

        System.out.println("test passed");
    }

    public static void main(String[] args) {
        ApplicationTest applicationTest = new ApplicationTest();
//        applicationTest.testFeedbackSummaryForOneRideOneCategory();
//        applicationTest.testFeedbackSummaryForOneRideMultipleCategories();
//        applicationTest.testFeedbackSummaryForMultipleRidesMultipleCategories();
        applicationTest.testFeedbackSummaryForMultipleProcesses();
    }
}
