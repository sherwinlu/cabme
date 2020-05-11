package com.cabme.feedback;

import com.cabme.category.Category;
import com.cabme.driver.entity.Driver;
import com.cabme.feedback.entity.FeedbackSummary;
import com.cabme.ride.entity.Ride;

import java.util.Map;
import java.util.Set;

/**
 * Service for handling feedback
 */
public interface FeedbackService {
    /**
     * To add feedback for the ride
     * @param categories list of categories that the passenger gave thumbs up for
     * @param ride corresponding ride that the categories are associated with
     */
    void addFeedback(Set<Category> categories, Ride ride);

    /**
     * retrieves all the feedback given to a driver. this data is wiped out after it's collected
     * @return all the feedback given to a driver
     */
    Map<Driver, Map<Category, Long>> getDriverCategoryCounter();

    /**
     * retrieves all the feedback given to a driver. this data is persisted
     * @return all the feedback given to all drivers
     */
    Map<Category, Set<FeedbackSummary>> getCategoryFeedbackSummary();

    /**
     * retrieves all the feedback given to a driver. this data is persisted.
     * the number of drivers returned is capped
     * @param limit number of drivers per category
     * @return all the feedback given to the top limit number of drivers per category
     */
    Map<Category, Set<FeedbackSummary>> getCategoryFeedbackSummaryWithLimit(int limit);
}
