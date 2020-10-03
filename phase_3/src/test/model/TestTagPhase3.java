package model;

import model.exceptions.EmptyStringException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTagPhase3 {
    private Tag tag;
    private Task task;

    @BeforeEach
    void runBefore() {
        tag = new Tag("Homework");
        task = new Task("JUnit Test.");
    }

    @Test
    void testContructor() {
        assertEquals("Homework", tag.getName());
        assertEquals("#Homework", tag.toString());
    }

    @Test
    void testEmptyNullName() {
        String name = "";
        try {
            tag = new Tag(name);
            fail();
        } catch (EmptyStringException e) {}
        name = null;
        try {
            tag = new Tag(name);
            fail();
        } catch (EmptyStringException e) {}
        name = "a";
        try {
            tag = new Tag(name);
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testAddTask() {
        assertFalse(tag.containsTask(task));
        assertFalse(task.containsTag(tag));
        tag.addTask(task);
        assertTrue(tag.containsTask(task));
        assertTrue(task.containsTag(tag));
        tag.addTask(task);
        assertTrue(tag.containsTask(task));
        assertTrue(task.containsTag(tag));
    }

    @Test
    void testRemoveTask() {
        tag.addTask(task);
        assertTrue(tag.containsTask(task));
        assertTrue(task.containsTag(tag));
        tag.removeTask(task);
        assertFalse(tag.containsTask(task));
        assertFalse(task.containsTag(tag));
        tag.removeTask(task);
        assertFalse(tag.containsTask(task));
        assertFalse(task.containsTag(tag));
    }
}
