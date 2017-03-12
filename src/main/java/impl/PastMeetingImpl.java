package impl;

import spec.Contact;
import spec.PastMeeting;

import java.util.Calendar;
import java.util.Set;

/**
 * PastMeetingImpl is a class implementing {@see PastMeeting}
 * and extending {@see MeetingImpl}.
 *
 * @author BBK-PiJ-2016-67
 */
public final class PastMeetingImpl extends MeetingImpl implements PastMeeting {
    private String notes;

    /**
     * Initialises the PastMeetingImpl class.
     *
     * @param ID          the ID of the contact
     * @param date        the date of the meeting
     * @param contacts    a list of attendees
     * @param note        notes for the meeting
     */
    public PastMeetingImpl(int ID, Calendar date, Set<Contact> contacts, String notes) {
        super(ID, date, contacts);
        if (notes == null) {
            throw new NullPointerException("notes cannot be null");
        }
        this.notes = notes;
    }

    /**
     * {@inheritDoc}.
     */
    public String getNotes() {
        return this.notes;
    }
}
