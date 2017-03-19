package impl;

import spec.Contact;
import spec.PastMeeting;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * MockPastMeetingImpl is a mock class implementing {@see PastMeeting}.
 *
 * @author BBK-PiJ-2016-67
 */
public final class MockPastMeetingImpl implements PastMeeting {
  public MockPastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {}

  public int getId() {
    return 1;
  }

  public Calendar getDate() {
    return Calendar.getInstance();
  }

  public Set<Contact> getContacts() {
    return new HashSet<Contact>();
  }

  public String getNotes() {
    return "great meeting";
  }
}
