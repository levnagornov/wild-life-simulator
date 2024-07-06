package org.example.logger;

import org.slf4j.Logger;

public class TimeExecutionLogger {
    private final Logger logger;

    public TimeExecutionLogger(Logger logger) {
        this.logger = logger;
    }

    public void logExecutionTime(String taskName, Runnable action) {
        var startTime = System.currentTimeMillis();
        logger.info("=== {} started ===", taskName);

        try {
            action.run();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            logger.info("=== {} finished in: {} ms ===", taskName, duration);
        }
    }
}
