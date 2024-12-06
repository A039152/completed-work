package com.amica.mars;

import java.time.LocalDateTime;

public abstract class ReportingRover extends Rover {
    private Telemetry subscriber;

    public ReportingRover(String id, int x, int y, Direction direction) {
        super(id, x, y, direction);
    }

    // Getter for the subscriber
    public Telemetry getSubscriber() {
        return subscriber;
    }

    // Setter for the subscriber
    public void setSubscriber(Telemetry subscriber) {
        this.subscriber = subscriber;
    }

    // Helper method to create and send a report
    protected void report(String observation) {
        if (subscriber != null) {
            Report report = new Report(this, observation);
            subscriber.sendReport(report);
        }
    }
}

