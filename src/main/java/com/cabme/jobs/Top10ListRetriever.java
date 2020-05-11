package com.cabme.jobs;

import com.cabme.api.feedback.FeedbackController;
import com.cabme.category.Category;
import com.cabme.feedback.entity.FeedbackSummary;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

/**
 * This is to simulate a client calling the REST endpoint to retrieve the top 10 list
 */
public class Top10ListRetriever extends TimerTask {
    private FeedbackController feedbackController = new FeedbackController();

    @Override
    public void run() {
        Map<Category, Set<FeedbackSummary>> categoryFeedbackSummary = feedbackController.getTop10();
        System.out.format("Retrieving Top 10 List (%s)\n", new Date());
        categoryFeedbackSummary.keySet().forEach(k -> {
            System.out.format("%s\n", k);
            categoryFeedbackSummary.get(k).forEach(f -> System.out.format("%s: %d\n", f.getDriver(), f.getCount()));
        });
    }
}
