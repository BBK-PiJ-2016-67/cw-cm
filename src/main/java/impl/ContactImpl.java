package impl;

import spec.Contact;

/**
 * ContactImpl is a class implementing {@see Contact}.
 *
 * @author BBK-PiJ-2016-67
 */
public class ContactImpl implements Contact {
    private int ID;
    private String name;
    private String notes = "";

    /**
     * Initialises the ContactImpl class.
     *
     * @param ID       the ID of the contact
     * @param name     the name of the contact
     */
    public ContactImpl(int ID, String name) {
        this(ID, name, "");
    }

    /**
     * Initialises the ContactImpl class.
     *
     * @param ID       the ID of the contact
     * @param name     the name of the contact
     * @param notes    initial notes about the contact
     */
    public ContactImpl(int ID, String name, String notes) {
        if (ID <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero");
        } else if (name == null) {
            throw new NullPointerException("name cannot be null");
        } else if (notes == null) {
            throw new NullPointerException("notes cannot be null");
        }
        this.ID = ID;
        this.name = name;
        this.notes = notes;
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
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}.
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * {@inheritDoc}.
     */
    public void addNotes(String note) {
        if (note == null) {
            throw new NullPointerException("note cannot be null");
        }
        if (this.notes != "") {
            this.notes += ", ";
        }
        this.notes += note;
    }
}
