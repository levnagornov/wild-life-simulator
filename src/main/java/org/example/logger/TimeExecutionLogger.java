package org.example.logger;

import org.slf4j.Logger;

/**
 * The {@code TimeExecutionLogger} class provides functionality to log the execution time of a task.
 * It utilizes an {@code org.slf4j.Logger} to log the start, end, and duration of the task execution.
 */
public class TimeExecutionLogger {
    private final Logger logger;

    /**
     * Constructs a new {@code TimeExecutionLogger} with the specified logger.
     *
     * @param logger the logger to use for logging execution times
     */
    public TimeExecutionLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Logs the execution time of the specified task. The task's name and duration are logged.
     *
     * @param taskName the name of the task to be logged
     * @param action   the task to be executed and timed
     */
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
