package impl;

import spec.Contact;
import spec.ContactManager;
import spec.FutureMeeting;
import spec.Meeting;
import spec.PastMeeting;

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

    /**
     * Determines whether a contact exists.
     *
     * @return whether the contact exists.
     */
    private boolean exists(Contact contact) {
        if (contact == null) {
            return false;
        }
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
     * @return whether all contacts exist.
     */
    private boolean exists(Set<Contact> contacts) {
        if (contacts == null) {
            return false;
        }
        for (Contact contact : contacts) {
            if (!this.exists(contact)) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        if (contacts == null) {
            throw new NullPointerException("contacts cannot be null");
        } else if (date == null) {
            throw new NullPointerException("date cannot be null");
        } else if (!date.after(Calendar.getInstance())) {
            throw new IllegalArgumentException("date cannot be in the past");
        } else if (!this.exists(contacts)) {
            throw new IllegalArgumentException("contact does not exist");
        }
        int id = this.meetings.size() + 1;
        FutureMeeting meeting = new FutureMeetingImpl(id, date, contacts);
        this.meetings.add(meeting);
        return id;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public PastMeeting getPastMeeting(int id) {
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
    public FutureMeeting getFutureMeeting(int id) {
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
    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        if (contact == null) {
            throw new NullPointerException("contact cannot be null");
        }
        if (!this.exists(contact)) {
            throw new IllegalArgumentException("contact does not exist");
        }
        List<Meeting> meetings = new ArrayList<Meeting>();
        for (Meeting meeting : this.meetings) {
            if (!(meeting instanceof FutureMeeting)) {
                continue;
            }
            for (Contact meetingContact : meeting.getContacts()) {
                if (contact.getId() == meetingContact.getId()) {
                    meetings.add(meeting);
                    break;
                }
            }
        }
        Collections.sort(meetings, new Comparator<Meeting>(){
            public int compare(Meeting meetingOne, Meeting meetingTwo){
                return meetingOne.getDate().compareTo(meetingTwo.getDate());
            }
        });
        return meetings;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Meeting> getMeetingListOn(Calendar date) {
        if (date == null) {
            throw new NullPointerException("date cannot be null");
        }
        List<Meeting> meetings = new ArrayList<Meeting>();
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);
        for (Meeting meeting : this.meetings) {
            Calendar meetingDate = meeting.getDate();
            int meetingDay = meetingDate.get(Calendar.DAY_OF_MONTH);
            int meetingMonth = meetingDate.get(Calendar.MONTH);
            int meetingYear = meetingDate.get(Calendar.YEAR);
            if (day == meetingDay && month == meetingMonth && year == meetingYear) {
                meetings.add(meeting);
            }
        }
        Collections.sort(meetings, new Comparator<Meeting>(){
            public int compare(Meeting meetingOne, Meeting meetingTwo){
                return meetingOne.getDate().compareTo(meetingTwo.getDate());
            }
        });
        return meetings;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<PastMeeting> getPastMeetingListFor(Contact contact) {
        if (contact == null) {
            throw new NullPointerException("contact cannot be null");
        }
        if (!this.exists(contact)) {
            throw new IllegalArgumentException("contact does not exist");
        }
        List<PastMeeting> meetings = new ArrayList<PastMeeting>();
        for (Meeting meeting : this.meetings) {
            try {
                PastMeeting pastMeeting = (PastMeeting) meeting;
                for (Contact meetingContact : pastMeeting.getContacts()) {
                    if (contact.getId() == meetingContact.getId()) {
                        meetings.add(pastMeeting);
                        break;
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        Collections.sort(meetings, new Comparator<PastMeeting>(){
            public int compare(PastMeeting meetingOne, PastMeeting meetingTwo){
                return meetingTwo.getDate().compareTo(meetingOne.getDate());
            }
        });
        return meetings;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        if (contacts == null) {
            throw new NullPointerException("contacts cannot be null");
        } else if (date == null) {
            throw new NullPointerException("date cannot be null");
        } else if (date.after(Calendar.getInstance())) {
            throw new IllegalArgumentException("date cannot be in the future");
        } else if (text == null) {
            throw new NullPointerException("text cannot be null");
        } else if (!this.exists(contacts)) {
            throw new IllegalArgumentException("contact does not exist");
        }
        int id = this.meetings.size() + 1;
        PastMeeting meeting = new PastMeetingImpl(id, date, contacts, text);
        this.meetings.add(meeting);
        return id;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public PastMeeting addMeetingNotes(int id, String text) {
        if (text == null) {
            throw new NullPointerException("text cannot be null");
        }
        Meeting meeting = this.getMeeting(id);
        if (meeting == null) {
            throw new IllegalArgumentException("meeting not found");
        } else if (meeting.getDate().after(Calendar.getInstance())) {
            throw new IllegalStateException("cannot add notes to future meeting");
        }
        PastMeeting pastMeeting = new PastMeetingImpl(id, meeting.getDate(), meeting.getContacts(), text);
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
    public int addNewContact(String name, String notes) {
        int id = this.contacts.size() + 1;
        Contact contact = new ContactImpl(id, name, notes);
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
    public Set<Contact> getContacts(String name) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }
        HashSet<Contact> contacts = new HashSet<Contact>();
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
    public Set<Contact> getContacts(int... ids) {
        if (ids.length == 0) {
            throw new IllegalArgumentException("ids cannot be empty");
        }
        HashSet<Contact> contacts = new HashSet<Contact>();
        for (Contact contact : this.contacts) {
            for (int id : ids) {
                if (id == contact.getId()) {
                    contacts.add(contact);
                }
            }
        }
        if (ids.length != contacts.size()) {
            throw new IllegalArgumentException("contact does not exist");
        }
        return contacts;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void flush() {
        
    }
}
