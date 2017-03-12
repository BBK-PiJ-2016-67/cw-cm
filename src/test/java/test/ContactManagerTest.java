package test;

import impl.MockContactImpl;
import impl.ContactManagerImpl;
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
        contacts.add(new MockContactImpl(1, "", ""));
        contacts.add(new MockContactImpl(1, "", ""));
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

}
