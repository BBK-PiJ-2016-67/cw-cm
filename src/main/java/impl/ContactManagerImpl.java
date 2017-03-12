package impl;

import spec.Contact;
import spec.ContactManager;
import spec.FutureMeeting;
import spec.Meeting;
import spec.PastMeeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ContactManagerImpl is a class implementing {@see ContactManager}.
 *
 * @author BBK-PiJ-2016-67
 */
public final class ContactManagerImpl implements ContactManager {
    /**
     * {@inheritDoc}.
     */
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        return 1;
    }

    /**
     * {@inheritDoc}.
     */
    public PastMeeting getPastMeeting(int id) {
        return new PastMeetingImpl(1, Calendar.getInstance(), new HashSet<Contact>(), "");
    }

    /**
     * {@inheritDoc}.
     */
    public FutureMeeting getFutureMeeting(int id) {
        return new FutureMeetingImpl(1, Calendar.getInstance(), new HashSet<Contact>());
    }

    /**
     * {@inheritDoc}.
     */
    public Meeting getMeeting(int id) {
        return new FutureMeetingImpl(1, Calendar.getInstance(), new HashSet<Contact>());
    }

    /**
     * {@inheritDoc}.
     */
    public List<Meeting> getFutureMeetingList(Contact contact) {
        return new ArrayList<Meeting>();
    }

    /**
     * {@inheritDoc}.
     */
    public List<Meeting> getMeetingListOn(Calendar date) {
        return new ArrayList<Meeting>();
    }

    /**
     * {@inheritDoc}.
     */
    public List<PastMeeting> getPastMeetingListFor(Contact contact) {
        return new ArrayList<PastMeeting>();
    }

    /**
     * {@inheritDoc}.
     */
    public int addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        return 1;
    }

    /**
     * {@inheritDoc}.
     */
    public PastMeeting addMeetingNotes(int id, String text) {
        return new PastMeetingImpl(1, Calendar.getInstance(), new HashSet<Contact>(), "");
    }

    /**
     * {@inheritDoc}.
     */
    public int addNewContact(String name, String notes) {
        return 1;
    }

    /**
     * {@inheritDoc}.
     */
    public Set<Contact> getContacts(String name) {
        return new HashSet<Contact>();
    }

    /**
     * {@inheritDoc}.
     */
    public Set<Contact> getContacts(int... ids) {
        return new HashSet<Contact>();
    }

    /**
     * {@inheritDoc}.
     */
    public void flush() {
        
    }
}
