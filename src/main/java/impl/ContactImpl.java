package impl;

import spec.Contact;

/**
 * ContactImpl is a class implementing {@see Contact}.
 *
 * @author BBK-PiJ-2016-67
 */
public class ContactImpl implements Contact {
    // TODO - two constructors
    /** The implementation of this interface must have two constructors. The most general constructor
        must have three parameters: int, String, String. The first one corresponds to the ID
        provided by the ContactManager, the next one corresponds to the name, and the last one
        corresponds to the initial set of notes about the contact. Another, more restricted constructor
        must have two parameters only: ID and name. If the ID provided is zero or negative, a
        IllegalArgumentException must be thrown. If any of the references / pointers passed as
        parameters to the constructor is null, a NullPointerException must be thrown.
    */

    /**
     * {@inheritDoc}.
     */
    public int getId() {
        return 0;
    }

    /**
     * {@inheritDoc}.
     */
    public String getName() {
        return "";
    }

    /**
     * {@inheritDoc}.
     */
    public String getNotes() {
        return "";
    }

    /**
     * {@inheritDoc}.
     */
    public void addNotes(String note) {

    }
}
