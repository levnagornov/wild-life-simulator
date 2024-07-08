package org.example.service;

/**
 * The {@code DelayService} class provides a method to introduce a delay
 * based on a specified latency.
 */
public class DelayService {
    private final int latency;

    /**
     * Constructs a new {@code DelayService} with the specified latency.
     *
     * @param latency the delay duration in milliseconds
     */
    public DelayService(int latency) {
        this.latency = latency;
    }

    /**
     * Introduces a delay for the duration specified by the latency.
     * This method catches and ignores {@code InterruptedException}.
     */
    public void delay() {
        try {
            Thread.sleep(latency);
        } catch (InterruptedException ignored) {
        }
    }
}
