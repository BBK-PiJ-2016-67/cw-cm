package test;

import impl.FutureMeetingImpl;
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
    HashSet<Contact> contacts;

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
            new FutureMeetingImpl(-1, nowDate, contacts);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("ID must be greater than zero", e.getMessage());
        }

        try {
            new FutureMeetingImpl(0, nowDate, contacts);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("ID must be greater than zero", e.getMessage());
        }

        try {
            new FutureMeetingImpl(1, null, contacts);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("date cannot be null", e.getMessage());
        }

        try {
            new FutureMeetingImpl(1, nowDate, null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("contacts cannot be null", e.getMessage());
        }

        try {
            new FutureMeetingImpl(1, nowDate, new HashSet<Contact>());
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("contacts cannot be empty", e.getMessage());
        }

        Meeting meeting = new FutureMeetingImpl(1, nowDate, contacts);
        assertEquals(1, meeting.getId());
        assertEquals(nowDate, meeting.getDate());
        assertEquals(contacts, meeting.getContacts());
    }

    @Test
    public void testImmutableDate() {
        Meeting meeting = new FutureMeetingImpl(1, nowDate, contacts);
        Calendar date = meeting.getDate();
        date.add(Calendar.YEAR, 1);
        assertFalse(date.get(Calendar.YEAR) == meeting.getDate().get(Calendar.YEAR));
    }

    @Test
    public void testContactsImmutableFromOutsideObject() {
        Meeting meeting = new FutureMeetingImpl(1, nowDate, contacts);
        Set<Contact> contacts = meeting.getContacts();
        contacts.add(new MockContactImpl(1, "", ""));
        assertFalse(contacts.size() == meeting.getContacts().size());
    }

    @Test
    public void testUniqueIds() {
        Meeting meetingOne = new FutureMeetingImpl(1, nowDate, contacts);
        Meeting meetingTwo = new FutureMeetingImpl(2, nowDate, contacts);
        assertThat(meetingOne.getId(), is(not(equalTo(meetingTwo.getId()))));
    }
}
