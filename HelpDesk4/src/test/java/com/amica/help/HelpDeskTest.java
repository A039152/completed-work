package com.amica.help;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import com.amica.help.Ticket.Priority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Unit test for the {@link HelpDesk} class.
 * 
 * @author Will Provost
 */
public class HelpDeskTest {

	public static final String TECH1 = "TECH1";
	public static final String TECH2 = "TECH2";
	public static final String TECH3 = "TECH3";

	public static final int TICKET1_ID = 1;
	public static final String TICKET1_ORIGINATOR = "TICKET1_ORIGINATOR";
	public static final String TICKET1_DESCRIPTION = "TICKET1_DESCRIPTION";
	public static final Priority TICKET1_PRIORITY = Priority.LOW;
	public static final int TICKET2_ID = 2;
	public static final String TICKET2_ORIGINATOR = "TICKET2_ORIGINATOR";
	public static final String TICKET2_DESCRIPTION = "TICKET2_DESCRIPTION";
	public static final Priority TICKET2_PRIORITY = Priority.HIGH;
	
	public static final String TAG1 = "TAG1";
	public static final String TAG2 = "TAG2";
	public static final String TAG3 = "TAG3";
	
	private HelpDesk helpDesk = new HelpDesk();
	private Technician tech1;
	private Technician tech2;
	private Technician tech3;

	@BeforeEach
	public void setUp() {
		helpDesk = new HelpDesk();

		// Add technicians
		helpDesk.createTechnician(TECH1, TECH1, 1001);
		helpDesk.createTechnician(TECH2, TECH2, 1002);
		helpDesk.createTechnician(TECH3, TECH3, 1003);

		// Assign technicians to fields
		Iterator<Technician> iterator = helpDesk.getTechnicians().iterator();
		tech1 = iterator.next();
		tech2 = iterator.next();
		tech3 = iterator.next();

		// Set clock time
		Clock.setTime(100);
	}

	/**
	 * Custom matcher that checks the contents of a stream of tickets
	 * against expected IDs, in exact order;
	 */
	public static class HasIDs extends TypeSafeMatcher<Stream<? extends Ticket>> {

		private String expected;
		private String was;
		private HelpDesk helpDesk;




		public HasIDs(int... IDs) {
			int[] expectedIDs = IDs;
			expected = Arrays.stream(expectedIDs)
					.mapToObj(Integer::toString)
					.collect(Collectors.joining(", ", "[ ", " ]"));		
		}
		
		public void describeTo(Description description) {
			
			description.appendText("tickets with IDs ");
			description.appendText(expected);
		}
		
		@Override
		public void describeMismatchSafely
				(Stream<? extends Ticket> tickets, Description description) {
			description.appendText("was: tickets with IDs ");
			description.appendText(was);
		}

		protected boolean matchesSafely(Stream<? extends Ticket> tickets) {
			was = tickets.mapToInt(Ticket::getID)
					.mapToObj(Integer::toString)
					.collect(Collectors.joining(", ", "[ ", " ]"));
			return expected.equals(was);
		}
		
	}
	public static Matcher<Stream<? extends Ticket>> hasIDs(int... IDs) {
		return new HasIDs(IDs);
	}
// Step5 uses a generic stream matcher:
//	public static Matcher<Stream<? extends Ticket>> hasIDs(Integer... IDs) {
//		return HasKeys.hasKeys(Ticket::getID, IDs);
//	}

	@Test
	public void testCreateAndGetTicketByID() {
		createTicket1();
		createTicket2();
		Ticket ticket = helpDesk.getTicketByID(TICKET2_ID);
		assertThat(ticket.getDescription(), equalTo(TICKET2_DESCRIPTION));
	}

	@Test
	public void testGetTicketByIDBeforeAddingTickets() {
		assertThat(helpDesk.getTicketByID(1), is(nullValue()));
	}

	@Test
	public void testCreateTicketWithoutTechnicians() {
		HelpDesk emptyHelpDesk = new HelpDesk();
		org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () -> {
			emptyHelpDesk.createTicket("Originator", "Description", Ticket.Priority.HIGH);
		});
	}

	@Test
	public void testTicketAssignedToTechnician() {
		createTicket1();
		Ticket ticket = helpDesk.getTicketByID(TICKET1_ID);
		assertThat(ticket.getTechnician(), equalTo(tech1));
		assertThat(tech1.getActiveTickets().count(), equalTo(1L));
	}

	@Test
	public void testTicketsAssignedRoundRobin() {
		createTicket1();
		createTicket2();
		Ticket ticket2 = helpDesk.getTicketByID(TICKET2_ID);
		assertThat(ticket2.getTechnician(), equalTo(tech2));
	}

	@Test
	public void testTicketsByStatus() {
		createTicket1();
		createTicket2();
		Ticket ticket2 = helpDesk.getTicketByID(TICKET2_ID);
		ticket2.resolve("Resolved for testing.");
		assertThat(helpDesk.getTicketsByStatus(Ticket.Status.ASSIGNED).count(), equalTo(1L));
		assertThat(helpDesk.getTicketsByStatus(Ticket.Status.RESOLVED).count(), equalTo(1L));
	}


	@Test
	public void testGetTicketsByNotStatus() {
		createTicket1();
		createTicket2();
		Stream<Ticket> tickets = helpDesk.getTicketsByNotStatus(Ticket.Status.WAITING);
		assertThat(tickets, hasIDs(TICKET2_ID, TICKET1_ID));
	}

	@Test
	public void testTicketsByTags() {
		createTicket1();
		createTicket2();
		helpDesk.addTags(TICKET1_ID, TAG1, TAG2);
		helpDesk.addTags(TICKET2_ID, TAG2, TAG3);
		assertThat(helpDesk.getTicketsWithAnyTag(TAG1).count(), equalTo(1L));
		assertThat(helpDesk.getTicketsWithAnyTag(TAG2).count(), equalTo(2L));
	}

	@Test
	public void testTicketsByTechnician() {
		createTicket1();
		createTicket2();
		Stream<Ticket> tickets = helpDesk.getTicketsByTechnician(TECH1);
		assertThat(tickets, hasIDs(TICKET1_ID));
	}

	@Test
	public void testTicketsByText() {
		createTicket1();
		createTicket2();
		Stream<Ticket> tickets = helpDesk.getTicketsByText(TICKET2_DESCRIPTION);
		assertThat(tickets, hasIDs(TICKET2_ID));
	}









	private void createTicket1() {
		helpDesk.createTicket(TICKET1_ORIGINATOR, TICKET1_DESCRIPTION, TICKET1_PRIORITY);
	}

	private void createTicket2() {
		helpDesk.createTicket(TICKET2_ORIGINATOR, TICKET2_DESCRIPTION, TICKET2_PRIORITY);
	}




}
