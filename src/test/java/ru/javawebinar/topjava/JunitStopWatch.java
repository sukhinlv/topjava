package ru.javawebinar.topjava;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

// https://stackoverflow.com/questions/17552779/record-time-it-takes-junit-tests-to-run
public class JunitStopWatch extends Stopwatch {
    private static final String SUCCEEDED = "succeeded";
    private static final String FAILED = "failed";
    private static final String SKIPPED = "skipped";
    private static final String FINISHED = "finished";
    private final Logger log = LoggerFactory.getLogger(JunitStopWatch.class);
    private final List<String> finishedList;

    public JunitStopWatch(List<String> finishedList) {
        Assert.notNull(finishedList, "finishedList must not be null");
        this.finishedList = finishedList;
    }

    private void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        long micros = TimeUnit.NANOSECONDS.toMillis(nanos);
        log.info(">>>>>>>>>>  Test {} {}, spent {} ms", testName, status, micros);
        if (status.equals(FINISHED)) {
            finishedList.add(String.format(">>>>>>>>>>  %s - %s ms", testName, micros));
        }
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, SUCCEEDED, nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, FAILED, nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, SKIPPED, nanos);
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, FINISHED, nanos);
    }
}
