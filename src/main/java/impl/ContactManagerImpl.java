package impl;

import spec.Contact;
import spec.ContactManager;
import spec.FutureMeeting;
import spec.Meeting;
import spec.PastMeeting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ContactManagerImpl is a class implementing {@see ContactManager}.
 *
 * @author BBK-PiJ-2016-67
 */
public final class ContactManagerImpl implements ContactManager {
  private List<Contact> contacts = new ArrayList<Contact>();
  private List<Meeting> meetings = new ArrayList<Meeting>();

  private static final String CONTACT_CANNOT_BE_NULL = "contact cannot be null";
  private static final String CONTACT_DOES_NOT_EXIST = "contact does not exist";
  private static final String CONTACTS_CANNOT_BE_NULL = "contacts cannot be null";
  private static final String DATE_CANNOT_BE_NULL = "date cannot be null";

  /**
   * Initialises the ContactManagerImpl class. If the file
   * contacts.txt exists, the contents will be used to populate
   * contacts and meetings.
   */
  public ContactManagerImpl() {
    File contactsFile = new File("contacts.txt");
    if (!contactsFile.exists()) {
      return;
    }
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("contacts.txt"))) {
      this.contacts = (List<Contact>) (inputStream.readObject());
      this.meetings = (List<Meeting>) (inputStream.readObject());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Determines whether a contact exists.
   *
   * @param contact    the contact to find
   * @return           whether the contact exists
   */
  private boolean exists(final Contact contact) {
    for (Contact existingContact : this.contacts) {
      if (contact.getId() == existingContact.getId()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Determines whether all contacts within a set of
   * contacts exist.
   *
   * @param contacts    the contacts to find
   * @return            whether all contacts exist
   */
  private boolean exists(final Set<Contact> contacts) {
    for (Contact contact : contacts) {
      if (!this.exists(contact)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether a meeting contains a contact.
   *
   * @param meeting    the meeting to look in
   * @param contact    the contact to find
   * @return           whether the meeting contains the contact
   */
  private boolean meetingContainsContact(final Meeting meeting, final Contact contact) {
    for (Contact meetingContact : meeting.getContacts()) {
      if (contact.getId() == meetingContact.getId()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sorts a list of meetings chronologically.
   *
   * @param meetings    the meetings to sort
   */
  private void sortChronologically(final List<Meeting> meetings) {
    Collections.sort(meetings, new Comparator<Meeting>(){
      public int compare(final Meeting meetingOne, final Meeting meetingTwo){
        return meetingOne.getDate().compareTo(meetingTwo.getDate());
      }
    });
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public int addFutureMeeting(final Set<Contact> contacts, final Calendar date) {
    if (contacts == null) {
      throw new NullPointerException(CONTACTS_CANNOT_BE_NULL);
    } else if (date == null) {
      throw new NullPointerException(DATE_CANNOT_BE_NULL);
    } else if (!date.after(Calendar.getInstance())) {
      throw new IllegalArgumentException("date cannot be in the past");
    } else if (!this.exists(contacts)) {
      throw new IllegalArgumentException(CONTACT_DOES_NOT_EXIST);
    }
    final int id = this.meetings.size() + 1;
    final FutureMeeting meeting = new FutureMeetingImpl(id, date, contacts);
    this.meetings.add(meeting);
    return id;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public PastMeeting getPastMeeting(final int id) {
    try {
      return (PastMeeting) this.getMeeting(id);
    } catch (Exception e) {
      throw new IllegalStateException("The meeting with this id is happening in the future");
    }
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public FutureMeeting getFutureMeeting(final int id) {
    try {
      return (FutureMeeting) this.getMeeting(id);
    } catch (Exception e) {
      throw new IllegalStateException("The meeting with this id is happening in the past");
    }
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public Meeting getMeeting(final int id) {
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
  @Override
  public List<Meeting> getFutureMeetingList(final Contact contact) {
    if (contact == null) {
      throw new NullPointerException(CONTACT_CANNOT_BE_NULL);
    } else if (!this.exists(contact)) {
      throw new IllegalArgumentException(CONTACT_DOES_NOT_EXIST);
    }
    final List<Meeting> meetings = new ArrayList<Meeting>();
    for (Meeting meeting : this.meetings) {
      if (!(meeting instanceof FutureMeeting)) {
        continue;
      }
      if (this.meetingContainsContact(meeting, contact)) {
        meetings.add(meeting);
      }
    }
    this.sortChronologically(meetings);
    return meetings;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public List<Meeting> getMeetingListOn(final Calendar date) {
    if (date == null) {
      throw new NullPointerException(DATE_CANNOT_BE_NULL);
    }
    final List<Meeting> meetings = new ArrayList<Meeting>();
    final int day = date.get(Calendar.DAY_OF_MONTH);
    final int month = date.get(Calendar.MONTH);
    final int year = date.get(Calendar.YEAR);
    for (Meeting meeting : this.meetings) {
      final Calendar meetingDate = meeting.getDate();
      final int meetingDay = meetingDate.get(Calendar.DAY_OF_MONTH);
      final int meetingMonth = meetingDate.get(Calendar.MONTH);
      final int meetingYear = meetingDate.get(Calendar.YEAR);
      if (day == meetingDay && month == meetingMonth && year == meetingYear) {
        meetings.add(meeting);
      }
    }
    this.sortChronologically(meetings);
    return meetings;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public List<PastMeeting> getPastMeetingListFor(final Contact contact) {
    if (contact == null) {
      throw new NullPointerException(CONTACT_CANNOT_BE_NULL);
    } else if (!this.exists(contact)) {
      throw new IllegalArgumentException(CONTACT_DOES_NOT_EXIST);
    }
    final List<PastMeeting> meetings = new ArrayList<PastMeeting>();
    for (Meeting meeting : this.meetings) {
      try {
        final PastMeeting pastMeeting = (PastMeeting) meeting;
        if (this.meetingContainsContact(pastMeeting, contact)) {
          meetings.add(pastMeeting);
        }
      } catch (Exception e) {
        continue;
      }
    }
    Collections.sort(meetings, new Comparator<Meeting>(){
      public int compare(final Meeting meetingOne, final Meeting meetingTwo){
        return meetingOne.getDate().compareTo(meetingTwo.getDate());
      }
    });
    return meetings;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public int addNewPastMeeting(final Set<Contact> contacts, final Calendar date, final String text) {
    if (contacts == null) {
      throw new NullPointerException(CONTACTS_CANNOT_BE_NULL);
    } else if (date == null) {
      throw new NullPointerException(DATE_CANNOT_BE_NULL);
    } else if (date.after(Calendar.getInstance())) {
      throw new IllegalArgumentException("date cannot be in the future");
    } else if (text == null) {
      throw new NullPointerException("text cannot be null");
    } else if (!this.exists(contacts)) {
      throw new IllegalArgumentException(CONTACT_DOES_NOT_EXIST);
    }
    final int id = this.meetings.size() + 1;
    final PastMeeting meeting = new PastMeetingImpl(id, date, contacts, text);
    this.meetings.add(meeting);
    return id;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public PastMeeting addMeetingNotes(final int id, final String text) {
    if (text == null) {
      throw new NullPointerException("text cannot be null");
    }
    final Meeting meeting = this.getMeeting(id);
    if (meeting == null) {
      throw new IllegalArgumentException("meeting not found");
    } else if (meeting.getDate().after(Calendar.getInstance())) {
      throw new IllegalStateException("cannot add notes to future meeting");
    }
    final PastMeeting pastMeeting = new PastMeetingImpl(id, meeting.getDate(), meeting.getContacts(), text);
    int index = 0;
    for (Meeting existingMeeting : this.meetings) {
      if (existingMeeting.getId() == id) {
        this.meetings.set(index, pastMeeting);
        break;
      }
      index++;
    }
    return pastMeeting;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public int addNewContact(final String name, final String notes) {
    final int id = this.contacts.size() + 1;
    final Contact contact = new ContactImpl(id, name, notes);
    if (name.isEmpty()) {
      throw new IllegalArgumentException("name cannot be empty");
    } else if (notes.isEmpty()) {
      throw new IllegalArgumentException("notes cannot be empty");
    }
    this.contacts.add(contact);
    return id;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public Set<Contact> getContacts(final String name) {
    if (name == null) {
      throw new NullPointerException("name cannot be null");
    }
    final HashSet<Contact> contacts = new HashSet<Contact>();
    for (Contact contact : this.contacts) {
      if (name.isEmpty() || name.equals(contact.getName())) {
        contacts.add(contact);
      }
    }
    return contacts;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public Set<Contact> getContacts(final int... ids) {
    if (ids.length == 0) {
      throw new IllegalArgumentException("ids cannot be empty");
    }
    final HashSet<Contact> contacts = new HashSet<Contact>();
    for (Contact contact : this.contacts) {
      for (int id : ids) {
        if (id == contact.getId()) {
          contacts.add(contact);
        }
      }
    }
    if (ids.length != contacts.size()) {
      throw new IllegalArgumentException(CONTACT_DOES_NOT_EXIST);
    }
    return contacts;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void flush() {
    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("contacts.txt"))) {
      outputStream.writeObject(this.contacts);
      outputStream.writeObject(this.meetings);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
