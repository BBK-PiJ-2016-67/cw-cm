package test;

import impl.PastMeetingImpl;
import impl.MockContactImpl;
import org.junit.Before;
import org.junit.Test;
import spec.Contact;
import spec.PastMeeting;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class PastMeetingTest {

  Calendar pastDate;
  HashSet<Contact> contacts;

  @Before
  public void setUp() {
    pastDate = Calendar.getInstance();
    pastDate.add(Calendar.YEAR, -1);
    contacts = new HashSet<Contact>();
    contacts.add(new MockContactImpl(1, "", ""));
    contacts.add(new MockContactImpl(1, "", ""));
  }

  @Test
  public void testMeetingConstructor() {
    try {
      new PastMeetingImpl(-1, pastDate, contacts, "");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
      assertEquals("id must be greater than zero", e.getMessage());
    }

    try {
      new PastMeetingImpl(0, pastDate, contacts, "");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
      assertEquals("id must be greater than zero", e.getMessage());
    }

    try {
      new PastMeetingImpl(1, null, contacts, "");
    } catch (Exception e) {
      assertTrue(e instanceof NullPointerException);
      assertEquals("date cannot be null", e.getMessage());
    }

    try {
      new PastMeetingImpl(1, pastDate, null, "");
    } catch (Exception e) {
      assertTrue(e instanceof NullPointerException);
      assertEquals("contacts cannot be null", e.getMessage());
    }

    try {
      new PastMeetingImpl(1, pastDate, new HashSet<Contact>(), "");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
      assertEquals("contacts cannot be empty", e.getMessage());
    }

    try {
      new PastMeetingImpl(1, pastDate, contacts, null);
    } catch (Exception e) {
      assertTrue(e instanceof NullPointerException);
      assertEquals("notes cannot be null", e.getMessage());
    }

    PastMeeting meeting = new PastMeetingImpl(1, pastDate, contacts, "");
    assertEquals(1, meeting.getId());
    assertEquals(pastDate, meeting.getDate());
    assertEquals(contacts, meeting.getContacts());
    assertEquals("", meeting.getNotes());
  }

  @Test
  public void testImmutableDate() {
    PastMeeting meeting = new PastMeetingImpl(1, pastDate, contacts, "");
    Calendar date = meeting.getDate();
    date.add(Calendar.YEAR, 1);
    assertFalse(date.get(Calendar.YEAR) == meeting.getDate().get(Calendar.YEAR));
  }

  @Test
  public void testContactsImmutableFromOutsideObject() {
    PastMeeting meeting = new PastMeetingImpl(1, pastDate, contacts, "");
    Set<Contact> contacts = meeting.getContacts();
    contacts.add(new MockContactImpl(1, "", ""));
    assertFalse(contacts.size() == meeting.getContacts().size());
  }

  @Test
  public void testUniqueIds() {
    PastMeeting meetingOne = new PastMeetingImpl(1, pastDate, contacts, "");
    PastMeeting meetingTwo = new PastMeetingImpl(2, pastDate, contacts, "");
    assertThat(meetingOne.getId(), is(not(equalTo(meetingTwo.getId()))));
  }

}
