package impl;

import spec.Contact;

/**
 * MockContactImpl is a mock class implementing {@see Contact}.
 *
 * @author BBK-PiJ-2016-67
 */
public final class MockContactImpl implements Contact {
    public MockContactImpl(int ID, String name) {}

    public MockContactImpl(int ID, String name, String notes) {}

    public int getId() {
        return 1;
    }

    public String getName() {
        return "joe";
    }

    public String getNotes() {
        return "good meeting";
    }

    public void addNotes(String note) {}
}
