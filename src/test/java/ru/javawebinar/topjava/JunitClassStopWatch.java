package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;

// https://www.logicbig.com/tutorials/unit-testing/junit/class-rule.html
public class JunitClassStopWatch implements TestRule {
    private final Logger log = LoggerFactory.getLogger(JunitClassStopWatch.class);
    private final List<String> finishedList;

    public JunitClassStopWatch(List<String> finishedList) {
        Assert.notNull(finishedList, "finishedList must not be null");
        this.finishedList = finishedList;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long start = System.currentTimeMillis();
                try {
                    base.evaluate();
                } finally {
                    log.info(">>>>>>>>>>  *** Test class time summary: ***");
                    // don`t replace with foreach, it produces incorrect getDisplayName output
                    for (String s : finishedList) {
                        log.info(s);
                    }
                    log.info(">>>>>>>>>>  *** Time taken for {}: {} ms ***", description.getDisplayName(), System.currentTimeMillis() - start);
                }
            }
        };
    }
}
