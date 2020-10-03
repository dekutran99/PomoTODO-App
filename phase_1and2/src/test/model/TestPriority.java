package model;

import model.exceptions.InvalidPriorityLevelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPriority {

    private Priority priority1;
    private Priority priority2;
    @BeforeEach
    public void runBefore() {
        priority1 = new Priority();
        priority2 = new Priority(1);
    }

    @Test
    void testConstructor() {
        assertEquals("DEFAULT", priority1.toString());
        assertEquals("IMPORTANT & URGENT", priority2.toString());
        assertTrue(priority2.isImportant());
        assertTrue(priority2.isUrgent());
        priority2.setImportant(false);
        assertFalse(priority2.isImportant());
        priority2.setUrgent(false);
        assertFalse(priority2.isUrgent());
        priority1.setUrgent(true);
        assertTrue(priority1.isUrgent());
        assertEquals("URGENT", priority1.toString());
        priority2.setImportant(true);
        assertEquals("IMPORTANT", priority2.toString());
        Priority priority = new Priority(2);
        assertTrue(priority.isImportant());
        priority = new Priority(3);
        assertTrue(priority.isUrgent());
    }

    @Test
    void testConstructor2() {
        priority1 = new Priority(2);
        priority2 = new Priority(3);
        assertEquals("IMPORTANT", priority1.toString());
        assertEquals("URGENT", priority2.toString());
        priority1 = new Priority(4);
        assertEquals("DEFAULT", priority1.toString());
    }

    @Test
    void testInvalidPriorityLevel() {
        try {
            Priority priority = new Priority(6);
            fail();
        } catch (InvalidPriorityLevelException i) {}

        try {
            Priority priority = new Priority(1);
        } catch (InvalidPriorityLevelException i) {
            fail();
        }
    }
}
