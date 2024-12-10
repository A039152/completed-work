package com.amica.help;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Class representing a problem ticket for a help desk.
 *
 * @author Will Provost
 */
public class Ticket {
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
	}
	public void addTags(String... tagsToAdd) {
		for (String tag : tagsToAdd) {
			tags.add(tag.toLowerCase()); // Normalize tags to lowercase
		}
	}

	public Set<String> getTags() {
		return tags;
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
		addEvent(new Event(Clock.getTime(),
				"Assigned to Technician " + technician.getID() + ", " + technician.getName() + ".",
				Status.ASSIGNED));
	}



	public void resolve(String note) {
		if (assignedTechnician != null) {
			assignedTechnician.resolveTicket(this);
		}
		addEvent(new Event(Clock.getTime(), note, Status.RESOLVED));
	}

	public List<Event> getHistory() {
		return history;
	}

	public void addEvent(Event event) {
		System.out.println("Adding event: " + event);
		history.add(event);
	}

	public void addNote(String note) {
		addEvent(new Event(Clock.getTime(), note, null));
	}
}
