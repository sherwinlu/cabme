package com.cabme.feedback;

import com.cabme.category.Category;
import com.cabme.driver.entity.Driver;
import com.cabme.feedback.entity.FeedbackSummary;
import com.cabme.ride.entity.Ride;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FeedbackServiceImpl implements FeedbackService {
    private static final FeedbackService feedbackService = new FeedbackServiceImpl();

    private final Map<Driver, Map<Category, Long>> driverCategoryCounter;
    private final Map<Category, Set<FeedbackSummary>> feedbackDriverCategoryCount = new HashMap<>();

    private final FeedbackCollectorService feedbackCollectorService = FeedbackCollectorServiceImpl.getInstance();

    private FeedbackServiceImpl() {
        this.driverCategoryCounter = new ConcurrentHashMap<>();
    }

    @Override
    public void addFeedback(final Set<Category> categories, final Ride ride) {
        driverCategoryCounter.compute(ride.getDriver(), (k, v) -> {
            final Map<Category, Long> tmpCategoryCounter;

            if (v == null) {
                tmpCategoryCounter = new ConcurrentHashMap<>();
            } else {
                tmpCategoryCounter = driverCategoryCounter.get(k);
            }

            for (final Category cat : categories) {
                tmpCategoryCounter.compute(cat, (x, y) -> y == null ? 1 : tmpCategoryCounter.get(cat) + 1);
            }

            return tmpCategoryCounter;
        });
    }

    @Override
    public Map<Driver, Map<Category, Long>> getDriverCategoryCounter() {
        return driverCategoryCounter;
    }

    @Override
    public Map<Category, Set<FeedbackSummary>> getCategoryFeedbackSummary() {
        if (feedbackCollectorService.isUpdateAvailable()) {
            synchronized (this) {
                feedbackDriverCategoryCount.clear();
                final Map<Category, Map<Driver, FeedbackSummary>> feedbackDriverCategorySummary = feedbackCollectorService.getFeedbackDriverCategorySummary();
                Arrays.stream(Category.values()).forEach(c -> {
                            final Set<FeedbackSummary> feedbackSummaries = feedbackDriverCategoryCount.getOrDefault(c, new TreeSet<>());
                            if (feedbackDriverCategorySummary.get(c) != null) {
                                final Map<Driver, FeedbackSummary> feedbackSummaryMap = feedbackDriverCategorySummary.get(c);
                                feedbackSummaryMap.forEach((k, v) ->
                                        feedbackSummaries.add(v)
                                );
                            }
                            feedbackDriverCategoryCount.put(c, feedbackSummaries);
                        }
                );
                feedbackCollectorService.setUpdateAvailable(false);
                return feedbackDriverCategoryCount;
            }
        }
        else {
            return feedbackDriverCategoryCount;
        }
    }

    @Override
    public Map<Category, Set<FeedbackSummary>> getCategoryFeedbackSummaryWithLimit(int limit) {
        synchronized (this) {
            final Map<Category, Set<FeedbackSummary>> feedbackSummaryMap = getCategoryFeedbackSummary();
            final Map<Category, Set<FeedbackSummary>> limitFeedbackSummaryMap = new HashMap<>();
            feedbackSummaryMap.keySet().forEach(k -> {
                final Set<FeedbackSummary> limitFeedbackSummaries = new TreeSet<>();
                final Set<FeedbackSummary> feedbackSummaries = feedbackSummaryMap.get(k);
                Iterator<FeedbackSummary> iter = feedbackSummaries.iterator();
                for (int i = 0; i < Math.min(limit, feedbackSummaries.size()); i++) {
                    limitFeedbackSummaries.add(iter.next());
                }
                limitFeedbackSummaryMap.put(k, limitFeedbackSummaries);
            });

            return limitFeedbackSummaryMap;
        }
    }

    public static FeedbackService getInstance() {
        return feedbackService;
    }
}
