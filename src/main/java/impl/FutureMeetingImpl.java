package impl;

import spec.Contact;
import spec.FutureMeeting;

import java.util.Calendar;
import java.util.Set;

/**
 * FutureMeetingImpl is a class implementing {@see FutureMeeting}
 * and extending {@see MeetingImpl}.
 *
 * @author BBK-PiJ-2016-67
 */
public final class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
    /**
     * Initialises the FutureMeetingImpl class.
     *
     * @param ID          the ID of the contact
     * @param date        the date of the meeting
     * @param contacts    a list of attendees
     */
    public FutureMeetingImpl(int ID, Calendar date, Set<Contact> contacts) {
        super(ID, date, contacts);
    }
}
