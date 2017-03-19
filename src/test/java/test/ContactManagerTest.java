package test;

import impl.MockContactImpl;
import impl.ContactManagerImpl;
import impl.FutureMeetingImpl;
import impl.PastMeetingImpl;
import org.junit.Before;
import org.junit.Test;
import spec.*;

import java.io.*;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ContactManagerTest {

    ContactManager contactManager;
    Calendar nowDate;
    Calendar pastDate;
    Calendar futureDate;
    HashSet<Contact> contacts;

    @Before
    public void setUp() {
        contactManager = new ContactManagerImpl();
        nowDate = Calendar.getInstance();
        pastDate = Calendar.getInstance();
        pastDate.add(Calendar.YEAR, -1);
        futureDate = Calendar.getInstance();
        futureDate.add(Calendar.YEAR, 1);
        contacts = new HashSet<Contact>();
        contacts.add(new MockContactImpl(1, "joe", ""));
        contacts.add(new MockContactImpl(2, "ben", ""));
        contactManager.addNewContact("joe", "good contact");
        contactManager.addNewContact("ben", "good contact");
    }

    @Test
    public void testAddFutureMeeting() {
        try {
            contactManager.addFutureMeeting(null, futureDate);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("contacts cannot be null", e.getMessage());
        }

        try {
            contactManager.addFutureMeeting(contacts, null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("date cannot be null", e.getMessage());
        }

        try {
            contactManager.addFutureMeeting(contacts, pastDate);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("date cannot be in the past", e.getMessage());
        }

        assertEquals(1, contactManager.addFutureMeeting(contacts, futureDate));
        assertEquals(contacts, contactManager.getMeeting(1).getContacts());
    }

    @Test
    public void testGetPastMeeting() {
        contactManager.addNewPastMeeting(contacts, pastDate, "good meeting");
        contactManager.addFutureMeeting(contacts, futureDate);

        try {
            contactManager.getPastMeeting(-1);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("id must be greater than 0", e.getMessage());
        }

        try {
            contactManager.getPastMeeting(0);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("id must be greater than 0", e.getMessage());
        }

        assertEquals("good meeting", contactManager.getPastMeeting(1).getNotes());
        assertEquals(null, contactManager.getPastMeeting(3));

        try {
            contactManager.getPastMeeting(2);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("The meeting with this ID is happening in the future", e.getMessage());
        }
    }

    @Test
    public void testGetFutureMeeting() {
        contactManager.addFutureMeeting(contacts, futureDate);
        contactManager.addNewPastMeeting(contacts, pastDate, "good meeting");

        try {
            contactManager.getFutureMeeting(-1);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("id must be greater than 0", e.getMessage());
        }

        try {
            contactManager.getFutureMeeting(0);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("id must be greater than 0", e.getMessage());
        }

        assertTrue(contactManager.getFutureMeeting(1) instanceof FutureMeetingImpl);
        assertEquals(null, contactManager.getFutureMeeting(3));
        
        try {
            contactManager.getFutureMeeting(2);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("The meeting with this ID is happening in the past", e.getMessage());
        }
    }

    @Test
    public void testGetMeeting() {
        contactManager.addFutureMeeting(contacts, futureDate);
        contactManager.addNewPastMeeting(contacts, pastDate, "good meeting");

        assertEquals(null, contactManager.getMeeting(-1));
        assertEquals(null, contactManager.getMeeting(0));
        assertTrue(contactManager.getMeeting(1) instanceof FutureMeetingImpl);
        assertTrue(contactManager.getMeeting(2) instanceof PastMeetingImpl);
        assertEquals(null, contactManager.getMeeting(3));
    }

    @Test
    public void testGetFutureMeetingList() {
        try {
            contactManager.getFutureMeetingList(null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("contact cannot be null", e.getMessage());
        }

        Contact contact = new MockContactImpl(1, "joe", "");

        try {
            contactManager.getFutureMeetingList(contact);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("contact does not exist", e.getMessage());
        }

        contactManager.addNewContact("joe", "good contact");
        assertEquals(0, contactManager.getFutureMeetingList(contact).size());

        contactManager.addFutureMeeting(contacts, futureDate);
        assertEquals(1, contactManager.getFutureMeetingList(contact).size());

        Calendar nearFutureDate = Calendar.getInstance();
        nearFutureDate.add(Calendar.MONTH, 1);
        contactManager.addNewPastMeeting(contacts, pastDate, "good meeting");
        contactManager.addFutureMeeting(contacts, nearFutureDate);
        assertEquals(2, contactManager.getFutureMeetingList(contact).size());
        assertEquals(3, contactManager.getFutureMeetingList(contact).get(0).getId());
        assertEquals(1, contactManager.getFutureMeetingList(contact).get(1).getId());
    }

    @Test
    public void testGetMeetingListOn() {
        try {
            contactManager.getMeetingListOn(null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("date cannot be null", e.getMessage());
        }

        assertEquals(0, contactManager.getMeetingListOn(Calendar.getInstance()).size());

        Calendar oneHourFromNow = Calendar.getInstance();
        oneHourFromNow.add(Calendar.HOUR_OF_DAY, 1);
        contactManager.addFutureMeeting(contacts, oneHourFromNow);
        Calendar twoHourFromNow = Calendar.getInstance();
        twoHourFromNow.add(Calendar.HOUR_OF_DAY, 2);
        contactManager.addFutureMeeting(contacts, twoHourFromNow);

        assertEquals(2, contactManager.getMeetingListOn(Calendar.getInstance()).size());
    }

    @Test
    public void testGetPastMeetingListFor() {
        try {
            contactManager.getPastMeetingListFor(null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("contact cannot be null", e.getMessage());
        }

        Contact contact = new MockContactImpl(1, "joe", "");

        try {
            contactManager.getPastMeetingListFor(contact);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("contact does not exist", e.getMessage());
        }

        contactManager.addNewContact("joe", "good contact");
        assertEquals(0, contactManager.getPastMeetingListFor(contact).size());

        contactManager.addNewPastMeeting(contacts, pastDate, "good meeting");
        assertEquals(1, contactManager.getPastMeetingListFor(contact).size());

        Calendar nearPastDate = Calendar.getInstance();
        nearPastDate.add(Calendar.MONTH, -1);
        contactManager.addFutureMeeting(contacts, futureDate);
        contactManager.addNewPastMeeting(contacts, nearPastDate, "good meeting also");
        assertEquals(2, contactManager.getPastMeetingListFor(contact).size());
        assertEquals(3, contactManager.getPastMeetingListFor(contact).get(0).getId());
        assertEquals(1, contactManager.getPastMeetingListFor(contact).get(1).getId());
    }

    @Test
    public void testAddNewPastMeeting() {
        try {
            contactManager.addNewPastMeeting(null, pastDate, "good meeting");
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("contacts cannot be null", e.getMessage());
        }

        try {
            contactManager.addNewPastMeeting(contacts, null, "good meeting");
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("date cannot be null", e.getMessage());
        }

        try {
            contactManager.addNewPastMeeting(contacts, futureDate, "good meeting");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("date cannot be in the future", e.getMessage());
        }

        try {
            contactManager.addNewPastMeeting(contacts, pastDate, null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("text cannot be null", e.getMessage());
        }

        assertEquals(1, contactManager.addNewPastMeeting(contacts, pastDate, "good meeting"));
        assertEquals(2, contactManager.addNewPastMeeting(contacts, pastDate, "bad meeting"));
    }

    @Test
    public void testAddMeetingNotes() {
        contactManager.addFutureMeeting(contacts, futureDate);
        contactManager.addFutureMeeting(contacts, futureDate);
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MILLISECOND, 1);
        contactManager.addFutureMeeting(contacts, date);

        try {
            contactManager.addMeetingNotes(1, null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("text cannot be null", e.getMessage());
        }

        try {
            contactManager.addMeetingNotes(1, "good meeting");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("cannot add notes to future meeting", e.getMessage());
        }

        try {
            contactManager.addMeetingNotes(4, "good meeting");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("meeting not found", e.getMessage());
        }

        try {
            Thread.sleep(10);
            assertEquals("good meeting", contactManager.addMeetingNotes(3, "good meeting").getNotes());
            assertEquals("good meeting", contactManager.getPastMeeting(3).getNotes());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testAddNewContact() {
        try {
            contactManager.addNewContact(null, "good contact");
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("name cannot be null", e.getMessage());
        }

        try {
            contactManager.addNewContact("", "good contact");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("name cannot be empty", e.getMessage());
        }

        try {
            contactManager.addNewContact("joe", null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("notes cannot be null", e.getMessage());
        }

        try {
            contactManager.addNewContact("joe", "");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("notes cannot be empty", e.getMessage());
        }

        assertEquals(3, contactManager.addNewContact("joe", "good contact"));
    }

    @Test
    public void testGetContacts() {
        try {
            String name = null;
            contactManager.getContacts(name);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("name cannot be null", e.getMessage());
        }

        assertEquals(2, contactManager.getContacts("").size());
        assertEquals(1, contactManager.getContacts("joe").size());
        assertEquals(1, contactManager.getContacts("ben").size());
        assertEquals(0, contactManager.getContacts("sam").size());
    }

    @Test
    public void testGetContactsWithIds() {
        contactManager.addNewContact("joe", "good contact");
        contactManager.addNewContact("ben", "bad contact");

        try {
            int[] ids = null;
            contactManager.getContacts(ids);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("ids cannot be null", e.getMessage());
        }

        assertEquals(1, contactManager.getContacts(1).size());
        assertEquals(2, contactManager.getContacts(1, 2).size());

        try {
            contactManager.getContacts(3);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("contact does not exist", e.getMessage());
        }
    }

    @Test
    public void testFlush() {
        // save data to disk :/
    }

}
