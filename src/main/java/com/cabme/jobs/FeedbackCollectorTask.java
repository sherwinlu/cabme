package com.cabme.jobs;

import com.cabme.feedback.FeedbackCollectorService;
import com.cabme.feedback.FeedbackCollectorServiceImpl;

import java.util.TimerTask;

/**
 * This is the task that will collect all the feedback that has been submitted thus far
 */
public class FeedbackCollectorTask extends TimerTask {
    private FeedbackCollectorService feedbackCollectorService = FeedbackCollectorServiceImpl.getInstance();

    @Override
    public void run() {
//        System.out.format("--> %s: Processing feedback <--\n", new Date());
        feedbackCollectorService.processDriverFeedback();
    }
}
