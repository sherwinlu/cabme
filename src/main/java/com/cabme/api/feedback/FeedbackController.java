package com.cabme.api.feedback;

import com.cabme.category.Category;
import com.cabme.feedback.FeedbackService;
import com.cabme.feedback.FeedbackServiceImpl;
import com.cabme.feedback.entity.FeedbackSummary;
import com.cabme.ride.entity.Ride;

import java.util.Map;
import java.util.Set;

/**
 * This is to simulate a REST Controller
 */
public class FeedbackController {
    private FeedbackService feedbackService = FeedbackServiceImpl.getInstance();

    /**
     * POST endpoint for passengers to submit feedback for their ride
     * @param categories list of categories that the passenger gave a thumbs up for
     * @param ride the corresponding ride that the thumbs up categories are associated with
     */
    public void postFeedback(Set<Category> categories, Ride ride) {
        feedbackService.addFeedback(categories, ride);
    }

    /**
     * GET endpoint for the clients to see the top 10 for each category
     * @return the top 10 and the corresponding list of feedback summary
     */
    public Map<Category, Set<FeedbackSummary>> getTop10() {
        return feedbackService.getCategoryFeedbackSummaryWithLimit(10);
    }
}
