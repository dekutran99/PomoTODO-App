package model;

import java.util.HashSet;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class TestTaskPhase3 {
    private Task task;
    private Tag tag;

    @BeforeEach
    void runBefore() {
        task = new Task("Test Task class");
        tag = new Tag("JUnit test.");
    }

    @Test
    void testConstructor() {
        assertEquals(0, task.getTags().size());
        task.addTag("Test");
        assertTrue(task.containsTag("Test"));
        assertEquals(1, task.getTags().size());
        assertTrue(task.containsTag("Test"));
        task.addTag("Test");
        assertEquals(1, task.getTags().size());
        task.addTag("Test2");
        assertEquals(2, task.getTags().size());
        task.removeTag("Test");
        assertEquals(1, task.getTags().size());
        task.removeTag("abc");
        task.removeTag("Test2");
        assertEquals(0, task.getTags().size());
        assertFalse(task.containsTag("Test"));
        assertEquals("DEFAULT", task.getPriority().toString());
        Priority priority = new Priority(1);
        task.setPriority(priority);
        assertEquals("IMPORTANT & URGENT", task.getPriority().toString());
        assertEquals("TODO", task.getStatus().toString());
        Status status = Status.DONE;
        task.setStatus(status);
        assertEquals("DONE", task.getStatus().toString());
        assertEquals("Test Task class", task.getDescription());
        task.setDescription("Stub");
        assertEquals("Stub", task.getDescription());
        assertEquals(null, task.getDueDate());
        DueDate dueDate = new DueDate();
        task.setDueDate(dueDate);
        assertEquals(dueDate.toString(), task.getDueDate().toString());
    }

    @Test
    void testToString() {
        String str = "\n{"
                + "\n\tDescription: Test Task class"
                + "\n\tDue date: Fri Mar 08 2019 11:59 PM"
                + "\n\tStatus: IN PROGRESS"
                + "\n\tPriority: IMPORTANT & URGENT"
                + "\n\tTags: #test"
                + "\n}";
        DueDate dueDate = new DueDate();
        task.setDueDate(dueDate);
        task.setStatus(Status.IN_PROGRESS);
        Priority priority = new Priority(1);
        task.setPriority(priority);
        task.addTag("test");
        assertEquals(str, task.toString());
    }

    @Test
    void testEmptyNullDescription() {
        String str = "";
        try {
            task = new Task(str);
            fail();
        } catch (EmptyStringException e) {
        }
        str = null;
        try {
            task = new Task(str);
            fail();
        } catch (EmptyStringException e) {
        }
        str = "a";
        try {
            task = new Task(str);
        } catch (EmptyStringException e) {
            fail();
        }
        str = "";
        try {
            task.setDescription(str);
            fail();
        } catch (EmptyStringException e) {
        }
        str = null;
        try {
            task.setDescription(str);
            fail();
        } catch (EmptyStringException e) {
        }
        str = "a";
        try {
            task.setDescription(str);
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testEmptyNullTagName() {
        String str = "";
        try {
            task.addTag(str);
            fail();
        } catch (EmptyStringException e) {
        }
        str = null;
        try {
            task.addTag(str);
            fail();
        } catch (EmptyStringException e) {
        }
        str = "a";
        try {
            task.addTag(str);
        } catch (EmptyStringException e) {
            fail();
        }
        str = "";
        try {
            task.removeTag(str);
            fail();
        } catch (EmptyStringException e) {
        }
        str = null;
        try {
            task.removeTag(str);
            fail();
        } catch (EmptyStringException e) {
        }
        str = "a";
        try {
            task.removeTag(str);
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testNullPriority() {
        Priority priority = null;
        try {
            task.setPriority(priority);
            fail();
        } catch (NullArgumentException n) {
        }
        priority = new Priority(1);
        try {
            task.setPriority(priority);
        } catch (NullArgumentException n) {
            fail();
        }
    }

    @Test
    void testNullStatus() {
        Status status = null;
        try {
            task.setStatus(status);
            fail();
        } catch (NullArgumentException n) {
        }
        status = Status.IN_PROGRESS;
        try {
            task.setStatus(status);
        } catch (NullArgumentException n) {
            fail();
        }
    }

    @Test
    void testAddTag() {
        assertFalse(tag.containsTask(task));
        assertFalse(task.containsTag(tag));
        task.addTag(tag);
        assertTrue(tag.containsTask(task));
        assertTrue(task.containsTag(tag));
        task.addTag(tag);
        assertTrue(tag.containsTask(task));
        assertTrue(task.containsTag(tag));
    }

    @Test
    void testRemoveTag() {
        task.addTag(tag);
        assertTrue(tag.containsTask(task));
        assertTrue(task.containsTag(tag));
        task.removeTag(tag);
        assertFalse(tag.containsTask(task));
        assertFalse(task.containsTag(tag));
        task.removeTag(tag);
        assertFalse(tag.containsTask(task));
        assertFalse(task.containsTag(tag));
    }
}