package org.example.service;

public class DelayService {
    private final int latency;

    public DelayService(int latency) {
        this.latency = latency;
    }

    public void delay() {
        try {
            Thread.sleep(latency);
        } catch (InterruptedException ignored) {
        }
    }
}
