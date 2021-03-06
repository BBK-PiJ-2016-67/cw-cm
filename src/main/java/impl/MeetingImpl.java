package impl;

import spec.Contact;
import spec.Meeting;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * MeetingImpl is a class implementing {@see Meeting}.
 *
 * @author BBK-PiJ-2016-67
 */
public abstract class MeetingImpl implements Meeting, Serializable {
  private final int id;
  private final Calendar date;
  private final Set<Contact> contacts;

  /**
   * Initialises the MeetingImpl class.
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
  public MeetingImpl(final int id, final Calendar date, final Set<Contact> contacts) {
    if (id <= 0) {
      throw new IllegalArgumentException("id must be greater than zero");
    } else if (date == null) {
      throw new NullPointerException("date cannot be null");
    } else if (contacts == null) {
      throw new NullPointerException("contacts cannot be null");
    } else if (contacts.isEmpty()) {
      throw new IllegalArgumentException("contacts cannot be empty");
    }
    this.id = id;
    this.date = date;
    this.contacts = contacts;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public int getId() {
    return this.id;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public Calendar getDate() {
    final Calendar date = Calendar.getInstance();
    date.setTime(this.date.getTime());
    return date;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public Set<Contact> getContacts() {
    final HashSet<Contact> contacts = new HashSet<Contact>();
    contacts.addAll(this.contacts);
    return contacts;
  }
}
