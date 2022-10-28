package ru.javawebinar.topjava;

import org.junit.ClassRule;
import org.junit.Rule;

import java.util.LinkedList;
import java.util.List;

public class StopWatchTestCase {
    private static final List<String> finishedList = new LinkedList<>();

    @Rule
    public JunitStopWatch stopWatch = new JunitStopWatch(finishedList);

    @ClassRule
    public static JunitClassStopWatch globalStopWatch = new JunitClassStopWatch(finishedList);
}
