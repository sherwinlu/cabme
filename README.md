# Cab driver attribute tracking system
Initial code base for Cab-Me Corp to track various attributes about its drivers, specifically passengers are given the option
to give a thumbs-up after the ride in the categories _prompt_, _clean_, and/or _friendly_. Every hour all the new thumbs-up ratings
are collected and the data is stored for each driver. Upon request, all the thumbs-up stats are retrieved and/or a leaderboard of
the top 10 drivers in each category is provided.

## Compiling the project

Either manually compile the various java files or use Maven to compile the project

```
% mvn clean package 
```

This should automatically build the project

### Running the project

```
% java -classpath target/classes com.cabme.Application
```

### Running the tests

```
% java -classpath target/classes;target/test-classes com.cabme.ApplicationTest
```

## Architecture / Design

Because of the scope of this project (no DB, no 3rd party frameworks), the application was designed to easily
changed to support REST API and Spring.

Given the nature of the project, I decided to use Threads to handle the passengers sending in a feedback
and every hour there is a data collector that sends all the collected data that is has collected to be processed.
This is done as a Java executor thread that runs every hour.

Another thread is used to simulate the taxi driver or whomever is calling to request the top 10 list.

## Design Limitations / Flaws

As this project is not meant to be a fully working production system, there are significant flaws and limitations.
If this was a production system, there are numerous points of failures as the data is not persisted anytime.

Also since the data is never reset, eventually we can run into problems with the count. Internally we are using long
to store the count, however, there is a maximum value of 9,223,372,036,854,775,807. For future improvement,
we could switch it from a long to a BigInteger. However, eventually we will run out of memory to store all the data.

Under ideal circumstances, we simply store all the data in the database such that there is persistence.