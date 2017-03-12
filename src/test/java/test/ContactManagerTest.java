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
        contacts.add(new MockContactImpl(1, "ben", ""));
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
            contactManager.addFutureMeeting(new HashSet<Contact>(), futureDate);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("contacts cannot be empty", e.getMessage());
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
        // get list
        // return empty list
        // chronologically sorted
        // no dupes
        // nullpointer if contact null
        // illegal argument if not in list of contacts
    }

    @Test
    public void testGetMeetingListOn() {
        // return list
        // return empty list
        // nullpointer null date
        // chronolog
        // no dupes
    }

    @Test
    public void testGetPastMeetingListFor() {
        // get list
        // return empty list
        // chronologically sorted
        // no dupes
        // nullpointer if contact null
        // illegal argument if not in list of contacts
    }

    @Test
    public void testAddNewPastMeeting() {
        // nullpointer args null
        // illage argument if contacts empty, contact doesnt exist or date in future
        // return id
    }

    @Test
    public void testAddMeetingNotes() {
        // null pointer notes null
        // illegal if date future
        // illegal meeting doesn't exist
        // convert futuretopast
        // return pastmeeting
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

        assertEquals(1, contactManager.addNewContact("joe", "good contact"));
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
        // illegal if no ids or if ids aren't for contacts
        // get one, get multiple
        // return list
    }

    @Test
    public void testFlush() {
        // save data to disk :/
    }

}
