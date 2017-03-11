package test;

import impl.ContactImpl;
import org.junit.Before;
import org.junit.Test;
import spec.Contact;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ContactTest {

    @Before
    public void setUp() {

    }

    @Test
    public void testContactConstructorSetIDName() {
        try {
            new ContactImpl(-1, "");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("ID must be greater than zero", e.getMessage());
        }

        try {
            new ContactImpl(0, "");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("ID must be greater than zero", e.getMessage());
        }

        try {
            new ContactImpl(1, null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("name cannot be null", e.getMessage());
        }

        Contact joe = new ContactImpl(1, "joe");
        assertEquals(1, joe.getId());
        assertEquals("joe", joe.getName());
        assertEquals("", joe.getNotes());
    }

    @Test
    public void testContactConstructorSetIDNameNotes() {
        try {
            new ContactImpl(1, "joe", null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("notes cannot be null", e.getMessage());
        }

        Contact joe = new ContactImpl(1, "joe", "good meeting");
        assertEquals(1, joe.getId());
        assertEquals("joe", joe.getName());
        assertEquals("good meeting", joe.getNotes());
    }

    @Test
    public void testAddNotesSingle() {
        Contact joe = new ContactImpl(1, "joe");
        assertEquals("", joe.getNotes());
        joe.addNotes("good meeting");
        assertEquals("good meeting", joe.getNotes());
    }

    @Test
    public void testAddNotesMultiple() {
        Contact joe = new ContactImpl(1, "joe");
        assertEquals("", joe.getNotes());
        joe.addNotes("good meeting");
        joe.addNotes("best business");
        assertTrue(joe.getNotes().contains("good meeting"));
        assertTrue(joe.getNotes().contains("best business"));
    }

}
