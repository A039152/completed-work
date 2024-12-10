package com.amica.help;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a technician in the help desk system.
 */
public class Technician {
    private String ID;
    private String name;
    private int extension;
    private List<Ticket> activeTickets = new LinkedList<>();

    public Technician(String ID, String name, int extension) {
        this.ID = ID;
        this.name = name;
        this.extension = extension;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getExtension() {
        return extension;
    }

    public List<Ticket> getActiveTickets() {
        return activeTickets;
    }

    public void assignTicket(Ticket ticket) {
        activeTickets.add(ticket);
    }

    public void resolveTicket(Ticket ticket) {
        activeTickets.remove(ticket);
    }

    public int getActiveTicketCount() {
        return activeTickets.size();
    }

    @Override
    public String toString() {
        return String.format("Technician[ID=%s, Name=%s, ActiveTickets=%d]",
                ID, name, activeTickets.size());
    }
}
