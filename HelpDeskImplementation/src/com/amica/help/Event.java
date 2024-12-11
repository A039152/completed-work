package com.amica.help;

/**
 * Represents an event in the history of a ticket.
 * Includes a timestamp, a note, and an optional status change.
 */
public class Event {
    private long timestamp;
    private String note;
    private Ticket.Status newStatus;

    public Event(String note, Ticket.Status newStatus) {
        this.timestamp = Clock.getTime();
        this.note = note;
        this.newStatus = newStatus;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getNote() {
        return note;
    }

    public Ticket.Status getNewStatus() {
        return newStatus;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (Status: %s)",
                Clock.format(timestamp), note, newStatus != null ? newStatus : "No Change");
    }
}
