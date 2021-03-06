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
   * @param id                           the id of the contact
   * @param date                         the date of the meeting
   * @param contacts                     a list of attendees
   * @throws IllegalArgumentException    if the id is not greater
   *                                     than zero or the contacts
   *                                     are empty
   * @throws NullPointerException        if the date or the contacts
   *                                     are null
   */
  public FutureMeetingImpl(final int id, final Calendar date, final Set<Contact> contacts) {
    super(id, date, contacts);
  }
}
