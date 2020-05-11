package com.cabme.feedback.entity;

import com.cabme.category.Category;
import com.cabme.driver.entity.Driver;

import java.util.Objects;

/**
 * This is a summary object that the summary of the driver's performance. This is to model a row in a database that
 * contains the driver_id, category and their corresponding count
 *
 * This object overrides the compareTo to by default sort using the count value such that when it's added to a TreeSet
 * it will by default sort using the count
 */
public class FeedbackSummary implements Comparable<FeedbackSummary> {
    private final Driver driver;
    private final Category category;
    private long count;

    public FeedbackSummary(Driver driver, Category category) {
        this.driver = driver;
        this.category = category;
    }

    public Driver getDriver() {
        return driver;
    }

    public Category getCategory() {
        return category;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackSummary feedbackSummary = (FeedbackSummary) o;
        return this.driver.equals(feedbackSummary.driver) && this.category.equals(feedbackSummary.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver, category);
    }

    @Override
    public int compareTo(FeedbackSummary feedbackSummary) {
        return Long.valueOf(this.count).compareTo(feedbackSummary.count) * -1;
    }

    @Override
    public String toString() {
        return "FeedbackDriverCategoryCount{" +
                ", driver=" + driver +
                ", category=" + category +
                ", count=" + count +
                '}';
    }
}
