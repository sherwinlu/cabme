package com.cabme.feedback;

import com.cabme.category.Category;
import com.cabme.driver.entity.Driver;
import com.cabme.feedback.entity.FeedbackSummary;

import java.util.Map;

/**
 * Service for collecting all the feedback data from all the rides
 */
public interface FeedbackCollectorService {

    /**
     * Process all the feedback for the drivers
     */
    void processDriverFeedback();

    /**
     * Set the update flag to determine if the cache has been refreshed
     * TODO: this should be refactored such that it's a private or at least protected method
     * @param updateAvailable whether or not the cache has been refreshed
     */
    void setUpdateAvailable(boolean updateAvailable);

    /**
     * Determine if there's the cache has been updated
     * @return if the cache has been updated
     */
    boolean isUpdateAvailable();

    /**
     * Fetches the feedback for the drivers for all the categories
     * @return the feedback for the drivers for all categories
     */
    Map<Category, Map<Driver, FeedbackSummary>> getFeedbackDriverCategorySummary();
}
