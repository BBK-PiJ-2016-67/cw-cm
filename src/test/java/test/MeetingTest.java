package test;

import impl.MeetingImpl;
import impl.MockContactImpl;
import org.junit.Before;
import org.junit.Test;
import spec.Contact;
import spec.Meeting;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class MeetingTest {

    Calendar nowDate;
    Set<Contact> contacts;

    @Before
    public void setUp() {
        nowDate = Calendar.getInstance();
        contacts = new HashSet<Contact>();
        contacts.add(new MockContactImpl(1, "", ""));
        contacts.add(new MockContactImpl(1, "", ""));
    }

    @Test
    public void testMeetingConstructor() {
        try {
            new MeetingImpl(-1, "", contacts);
        } catch (Exception e) {
            assertEquals("ID must be greater than zero", e.getMessage());
        }

        try {
            new MeetingImpl(0, "", contacts);
        } catch (Exception e) {
            assertEquals("ID must be greater than zero", e.getMessage());
        }

        try {
            new MeetingImpl(1, null, contacts);
        } catch (Exception e) {
            assertEquals("date cannot be null", e.getMessage());
        }

        try {
            new MeetingImpl(1, nowDate, null);
        } catch (Exception e) {
            assertEquals("contacts cannot be null", e.getMessage());
        }

        try {
            new MeetingImpl(1, nowDate, new HashSet<Contact>());
        } catch (Exception e) {
            assertEquals("contacts set cannot be empty", e.getMessage());
        }

        MeetingImpl meeting = new MeetingImpl(1, nowDate, contacts);
        assertEquals(1, meeting.getId());
        assertEquals(nowDate, meeting.getDate());
        assertEquals(contacts, meeting.getContacts());
    }

    @Test
    public void testImmutableDate() {
        Meeting meeting = new MeetingImpl(1, nowDate, contacts);
        Calendar date = meeting.getDate();
        date.add(Calendar.YEAR, 1);
        assertFalse(date.get(Calendar.YEAR) == meeting.getDate().get(Calendar.YEAR));
    }

    @Test
    public void testContactsImmutableFromOutsideObject() {
        Meeting meeting = new MeetingImpl(1, nowDate, contacts);
        Set<Contact> contacts = meeting.getContacts();
        contacts.add(new MockContactImpl());
        assertFalse(contacts.size() == meeting.getContacts().size());
    }

    @Test
    public void testUniqueIds() {
        Meeting meetingOne = new MeetingImpl(1, nowDate, contacts);
        Meeting meetingTwo = new MeetingImpl(2, nowDate, contacts);
        assertThat(meetingOne.getId(), is(not(equalTo(meetingTwo.getId()))));
    }
}
