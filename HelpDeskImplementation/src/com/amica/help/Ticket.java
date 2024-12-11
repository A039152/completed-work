package com.amica.help;

import java.util.*;

/**
 * Class representing a problem ticket for a help desk.
 *
 * @author Will Provost
 */
public class Ticket implements Comparable<Ticket>{
	public enum Status { CREATED, ASSIGNED, RESOLVED }
	public enum Priority { LOW, MEDIUM, HIGH, URGENT }

	private int ID;
	private String originator;
	private String description;
	private Priority priority;
	private Technician assignedTechnician;
	private List<Event> history = new LinkedList<>();
	private Set<String> tags = new HashSet<>();


	public Ticket(int ID, String originator, String description, Priority priority) {
		this.ID = ID;
		this.originator = originator;
		this.description = description;
		this.priority = priority;
		addEvent(new Event("Created ticket.", Status.CREATED));
	}

	public void addTags(String... tagsToAdd) {
		for (String tag : tagsToAdd) {
			tags.add(tag.toLowerCase()); // Normalize tags to lowercase
		}
	}

	public Set<String> getTags() {
		return tags;
	}

	@Override
	public int compareTo(Ticket other) {
		return Comparator.comparing(Ticket::getPriority).reversed() // Descending by priority
				.thenComparing(Ticket::getID)               // Ascending by ID
				.compare(this, other);
	}


	public int getID() {
		return ID;
	}

	public Priority getPriority() {
		return priority;
	}

	public Status getStatus() {
		for (int i = history.size() - 1; i >= 0; i--) {
			if (history.get(i).getNewStatus() != null) {
				return history.get(i).getNewStatus();
			}
		}
		return null; // Should not occur if history is properly maintained
	}

	public Technician getAssignedTechnician() {
		return assignedTechnician;
	}

	public void assignTechnician(Technician technician) {
		this.assignedTechnician = technician;

		// Update the history to reflect assignment
		addEvent(new Event(
				"Assigned to Technician " + technician.getID() + ", " + technician.getName() + ".",
				Status.ASSIGNED));
	}



	public void resolve(String note) {
		if (assignedTechnician != null) {
			assignedTechnician.resolveTicket(this);
		}
		addEvent(new Event(note, Status.RESOLVED));
	}

	public List<Event> getHistory() {
		return history;
	}

	public void addEvent(Event event) {
		System.out.println("Adding event: " + event);
		history.add(event);
	}

	public void addNote(String note) {
		addEvent(new Event(note, null));
	}

	public long getResolutionTime() {
		long creationTime = -1;
		long resolutionTime = -1;

		for (Event event : history) {
			if (event.getNewStatus() == Status.CREATED) {
				creationTime = event.getTimestamp();
			} else if (event.getNewStatus() == Status.RESOLVED) {
				resolutionTime = event.getTimestamp();
				break;
			}
		}

		return (creationTime != -1 && resolutionTime != -1) ? (resolutionTime - creationTime) : -1;
	}

}
