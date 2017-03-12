package impl;

import spec.Contact;
import spec.Meeting;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * MeetingImpl is a class implementing {@see Meeting}.
 *
 * @author BBK-PiJ-2016-67
 */
public abstract class MeetingImpl implements Meeting {
    private final int ID;
    private final Calendar date;
    private final HashSet<Contact> contacts;

    /**
     * Initialises the MeetingImpl class.
     *
     * @param ID          the ID of the contact
     * @param date        the date of the meeting
     * @param contacts    a list of attendees
     */
    public MeetingImpl(int ID, Calendar date, HashSet<Contact> contacts) {
        if (ID <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero");
        } else if (date == null) {
            throw new NullPointerException("date cannot be null");
        } else if (contacts == null) {
            throw new NullPointerException("contacts cannot be null");
        } else if (contacts.isEmpty()) {
            throw new IllegalArgumentException("contacts cannot be empty");
        }
        this.ID = ID;
        this.date = date;
        this.contacts = contacts;
    }

    /**
     * {@inheritDoc}.
     */
    public int getId() {
        return this.ID;
    }

    /**
     * {@inheritDoc}.
     */
    public Calendar getDate() {
        Calendar date = Calendar.getInstance();
        date.setTime(this.date.getTime());
        return date;
    }

    /**
     * {@inheritDoc}.
     */
    public Set<Contact> getContacts() {
        HashSet<Contact> contacts = new HashSet<Contact>();
        contacts.addAll(this.contacts);
        return contacts;
    }
}
