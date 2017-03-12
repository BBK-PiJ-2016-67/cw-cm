package impl;

import spec.Contact;
import spec.FutureMeeting;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * MockFutureMeetingImpl is a mock class implementing {@see FutureMeeting}.
 *
 * @author BBK-PiJ-2016-67
 */
public final class MockFutureMeetingImpl implements FutureMeeting {
    public MockFutureMeetingImpl(int ID, Calendar date, HashSet<Contact> contacts) {}

    public int getId() {
        return 1;
    }

    public Calendar getDate() {
        return Calendar.getInstance();
    }

    public Set<Contact> getContacts() {
        return new HashSet<Contact>();
    }
}
