package com.cabme.feedback;

import com.cabme.category.Category;
import com.cabme.driver.entity.Driver;
import com.cabme.feedback.entity.FeedbackSummary;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FeedbackCollectorServiceImpl implements FeedbackCollectorService {

    private final Map<Category, Map<Driver, FeedbackSummary>> feedbackDriverCategorySummary;

    private static final FeedbackCollectorService instance = new FeedbackCollectorServiceImpl();

    private FeedbackCollectorServiceImpl() {
        feedbackDriverCategorySummary = new ConcurrentHashMap<>();
    }
    private boolean isUpdateAvailable = false;

    @Override
    public void processDriverFeedback() {
        synchronized (this) {
            Map<Driver, Map<Category, Long>> driverCategoryCounter = FeedbackServiceImpl.getInstance().getDriverCategoryCounter();
            driverCategoryCounter.forEach((d, m) -> m.forEach((cat, count) ->
            {
                final Map<Driver, FeedbackSummary> driverCategoryCounts = feedbackDriverCategorySummary.getOrDefault(cat, new ConcurrentHashMap<>());
                final FeedbackSummary feedbackSummary = driverCategoryCounts.getOrDefault(d, new FeedbackSummary(d, cat));
                feedbackSummary.setCount(feedbackSummary.getCount() + count);
                driverCategoryCounts.put(d, feedbackSummary);
                feedbackDriverCategorySummary.put(cat, driverCategoryCounts);
            }));
            driverCategoryCounter.clear();
            setUpdateAvailable(true);
        }
    }

    @Override
    public void setUpdateAvailable(boolean updateAvailable) {
        synchronized (this) {
            isUpdateAvailable = updateAvailable;
        }
    }

    @Override
    public boolean isUpdateAvailable() {
        return isUpdateAvailable;
    }

    @Override
    public Map<Category, Map<Driver, FeedbackSummary>> getFeedbackDriverCategorySummary() {
        return feedbackDriverCategorySummary;
    }

    public static FeedbackCollectorService getInstance() {
        return instance;
    }
}
