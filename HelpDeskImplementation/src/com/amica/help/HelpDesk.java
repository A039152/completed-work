package com.amica.help;

import java.util.*;

public class HelpDesk implements HelpDeskAPI {

    private int nextTicketID = 1;
    private SortedSet<Ticket> tickets = new TreeSet<>(Comparator.comparingInt(Ticket::getID));
    private Map<Integer, Ticket> ticketMap = new HashMap<>();
    private List<Technician> technicians = new ArrayList<>();

    @Override
    public void createTechnician(String ID, String name, int extension) {
        technicians.add(new Technician(ID, name, extension));
    }

    @Override
    public int createTicket(String originator, String description, Ticket.Priority priority) {
        Ticket ticket = new Ticket(nextTicketID++, originator, description, priority);

        // Add a "Created ticket." event
        ticket.addEvent(new Event(Clock.getTime(), "Created ticket.", Ticket.Status.CREATED));

        // Find the least-busy technician
        Technician leastBusyTechnician = findLeastBusyTechnician();
        if (leastBusyTechnician != null) {
            leastBusyTechnician.assignTicket(ticket);
            ticket.assignTechnician(leastBusyTechnician); // Adds the ASSIGNED event
        }

        tickets.add(ticket);
        ticketMap.put(ticket.getID(), ticket);
        return ticket.getID();
    }






    private Technician findLeastBusyTechnician() {
        return technicians.stream()
                .min(Comparator.comparingInt(Technician::getActiveTicketCount))
                .orElse(null);
    }

    @Override
    public List<Ticket> getTicketsByTechnician(String techID) {
        return tickets.stream()
                .filter(ticket -> ticket.getAssignedTechnician() != null &&
                        ticket.getAssignedTechnician().getID().equals(techID))
                .toList();
    }

    @Override
    public void addNoteToTicket(int ticketID, String note) {
        Ticket ticket = ticketMap.get(ticketID);
        if (ticket != null) {
            ticket.addNote(note);
        }
    }

    @Override
    public void resolveTicket(int ticketID, String note) {
        Ticket ticket = ticketMap.get(ticketID);
        if (ticket != null) {
            ticket.resolve(note);
        }
    }

    @Override
    public void addTags(int ticketID, String... tags) {
        Ticket ticket = ticketMap.get(ticketID);
        if (ticket != null) {
            ticket.addTags(tags);
        }
    }

    @Override
    public int reopenTicket(int priorTicketID, String reason, Ticket.Priority priority) {
        // Implementation in Phase 7
        return 0;
    }

    @Override
    public Ticket getTicketByID(int ID) {
        return ticketMap.get(ID);
    }

    @Override
    public SortedSet<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public List<Ticket> getTicketsByStatus(Ticket.Status status) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getStatus() == status) {
                result.add(ticket);
            }
        }
        return result;
    }

    @Override
    public List<Ticket> getTicketsByNotStatus(Ticket.Status status) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getStatus() != status) {
                result.add(ticket);
            }
        }
        return result;
    }


    @Override
    public List<Ticket> getTicketsWithAnyTag(String... tags) {
        Set<String> searchTags = new HashSet<>();
        for (String tag : tags) {
            searchTags.add(tag.toLowerCase()); // Normalize tags to lowercase
        }

        List<Ticket> result = new ArrayList<>();
        for (Ticket ticket : tickets) {
            for (String tag : ticket.getTags()) {
                if (searchTags.contains(tag)) {
                    result.add(ticket);
                    break; // Avoid duplicates in results
                }
            }
        }
        return result;
    }

    @Override
    public int getAverageMinutesToResolve() {
        // Implementation in Phase 5
        return 0;
    }

    @Override
    public Map<String, Integer> getAverageMinutesToResolvePerTechnician() {
        // Implementation in Phase 5
        return null;
    }

    @Override
    public List<Ticket> getTicketsByText(String text) {
        // Implementation in Phase 6
        return null;
    }
}
