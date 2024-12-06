package com.amica.mars;

import java.time.LocalDateTime;

public class Report {
    private final String roverId;
    private final int x;
    private final int y;
    private final Rover.Direction direction;
    private final LocalDateTime timestamp;
    private final String observation;

    // Constructor that takes a rover and observation
    public Report(Rover rover, String observation) {
        this.roverId = rover.getId();
        this.x = rover.getX();
        this.y = rover.getY();
        this.direction = rover.getDirection();
        this.timestamp = LocalDateTime.now();
        this.observation = observation;
    }

    // Getters for all fields
    public String getRoverId() {
        return roverId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rover.Direction getDirection() {
        return direction;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getObservation() {
        return observation;
    }

    // toString method for formatted output
    @Override
    public String toString() {
        return String.format("[%s] Rover ID: %s | Location: (%d, %d) | Direction: %s | Observation: %s",
                timestamp, roverId, x, y, direction, observation);
    }
}
