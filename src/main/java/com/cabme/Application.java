package com.cabme;

import com.cabme.jobs.FeedbackCollectorTask;
import com.cabme.jobs.FeedbackProducer;
import com.cabme.jobs.Top10ListRetriever;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Main starting point to launch the Taxi Backend. For data generation purposes it includes a data generator as well as a client
 * requesting the top 10 list
 *
 */
public class Application {

    public static void main(String[] args) {
        FeedbackCollectorTask feedbackCollectorTask = new FeedbackCollectorTask();
        FeedbackProducer feedbackProducer = new FeedbackProducer();
        Top10ListRetriever top10ListRetriever = new Top10ListRetriever();

        ScheduledExecutorService collectorExecutor = Executors.newSingleThreadScheduledExecutor();
        collectorExecutor.scheduleAtFixedRate(feedbackCollectorTask, 1L, 60L, TimeUnit.MINUTES);

        ScheduledExecutorService producerExecutor = Executors.newSingleThreadScheduledExecutor();
        producerExecutor.scheduleAtFixedRate(feedbackProducer, 15L, 60L, TimeUnit.SECONDS);

        ScheduledExecutorService top10Executor = Executors.newSingleThreadScheduledExecutor();
        top10Executor.scheduleAtFixedRate(top10ListRetriever, 61L, 60L, TimeUnit.MINUTES);
    }
}
