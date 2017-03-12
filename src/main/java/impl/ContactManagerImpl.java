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
    private HashSet<Meeting> meetings = new HashSet<Meeting>();

    /**
     * {@inheritDoc}.
     */
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        if (contacts == null) {
            throw new NullPointerException("contacts cannot be null");
        } else if (contacts.isEmpty()) {
            throw new IllegalArgumentException("contacts cannot be empty");
        } else if (date == null) {
            throw new NullPointerException("date cannot be null");
        } else if (!date.after(Calendar.getInstance())) {
            throw new IllegalArgumentException("date cannot be in the past");
        }
        int id = this.meetings.size() + 1;
        FutureMeeting meeting = new FutureMeetingImpl(id, date, contacts);
        this.meetings.add(meeting);
        return id;
    }

    /**
     * {@inheritDoc}.
     */
    public PastMeeting getPastMeeting(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be greater than 0");
        }
        Meeting meeting = this.getMeeting(id);
        try {
            PastMeeting pastMeeting = (PastMeeting) meeting;
            return pastMeeting;
        } catch (Exception e) {
            throw new IllegalArgumentException("The meeting with this ID is happening in the future");
        }
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
        for (Meeting meeting : this.meetings) {
            if (meeting.getId() == id) {
                return meeting;
            }
        }
        return null;
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
        int id = this.meetings.size() + 1;
        PastMeeting meeting = new PastMeetingImpl(id, date, contacts, text);
        this.meetings.add(meeting);
        return id;
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
