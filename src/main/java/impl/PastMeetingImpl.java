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
  private final String notes;

  /**
   * Initialises the PastMeetingImpl class.
   *
   * @param id                           the id of the contact
   * @param date                         the date of the meeting
   * @param contacts                     a list of attendees
   * @param note                         notes for the meeting
   * @throws IllegalArgumentException    if the id is not greater
   *                                     than zero or the contacts
   *                                     are empty
   * @throws NullPointerException        if the date, the contacts
   *                                     or the note are null
   */
  public PastMeetingImpl(final int id, final Calendar date, final Set<Contact> contacts, final String notes) {
    super(id, date, contacts);
    if (notes == null) {
      throw new NullPointerException("notes cannot be null");
    }
    this.notes = notes;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public String getNotes() {
    return this.notes;
  }
}
