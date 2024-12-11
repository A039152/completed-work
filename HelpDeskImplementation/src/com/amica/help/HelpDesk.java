package com.amica.help;

import java.util.*;

public class HelpDesk implements HelpDeskAPI {

    private int nextTicketID = 1;
    private SortedSet<Ticket> tickets = new TreeSet<>();
    private Map<Integer, Ticket> ticketMap = new HashMap<>();
    private List<Technician> technicians = new ArrayList<>();

    @Override
    public void createTechnician(String ID, String name, int extension) {
        technicians.add(new Technician(ID, name, extension));
    }

    @Override
    public int createTicket(String originator, String description, Ticket.Priority priority) {
        Ticket ticket = new Ticket(nextTicketID++, originator, description, priority); // Ticket handles the CREATED event

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
        long totalTime = 0;
        int resolvedCount = 0;

        for (Ticket ticket : tickets) {
            long resolutionTime = ticket.getResolutionTime();
            if (resolutionTime != -1) { // Include only resolved tickets
                totalTime += resolutionTime;
                resolvedCount++;
            }
        }

        // Return average in minutes (convert milliseconds to minutes)
        return resolvedCount > 0 ? (int) (totalTime / (resolvedCount * 60 * 1000)) : 0;
    }

    @Override
    public Map<String, Integer> getAverageMinutesToResolvePerTechnician() {
        Map<String, Long> totalTimes = new HashMap<>();
        Map<String, Integer> resolvedCounts = new HashMap<>();

        for (Ticket ticket : tickets) {
            long resolutionTime = ticket.getResolutionTime();
            Technician technician = ticket.getAssignedTechnician();

            if (resolutionTime != -1 && technician != null) {
                String techID = technician.getID();
                totalTimes.put(techID, totalTimes.getOrDefault(techID, 0L) + resolutionTime);
                resolvedCounts.put(techID, resolvedCounts.getOrDefault(techID, 0) + 1);
            }
        }

        // Calculate averages and convert to minutes
        Map<String, Integer> averages = new HashMap<>();
        for (String techID : totalTimes.keySet()) {
            long totalTime = totalTimes.get(techID);
            int count = resolvedCounts.get(techID);
            averages.put(techID, (int) (totalTime / (count * 60 * 1000)));
        }

        return averages;
    }

    @Override
    public List<Ticket> getTicketsByText(String text) {
        // Implementation in Phase 6
        return null;
    }
}
