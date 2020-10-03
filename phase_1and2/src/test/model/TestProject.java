package model;

import java.util.LinkedList;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsers.exceptions.ParsingException;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {

    private Project project;

    @BeforeEach
    void runBefore() {
        project = new Project("Test Project class");
    }

    @Test
    void testConstructor() {
        assertEquals("Test Project class", project.getDescription());
        assertEquals(0, project.getNumberOfTasks());
        LinkedList<Task> tasks = new LinkedList<Task>();
        assertEquals(tasks, project.getTasks());
            Task task = new Task("a ## b");
            project.add(task);
            project.add(task);
            tasks.add(task);
            assertEquals(tasks.size(), project.getNumberOfTasks());
            assertEquals(0, project.getProgress());
            assertFalse(project.isCompleted());
            task.setStatus(Status.DONE);
            assertEquals(100, project.getProgress());
            assertTrue(project.isCompleted());
            assertTrue(project.contains(task));
            project.remove(task);
            tasks.remove(task);
            assertEquals(tasks, project.getTasks());
            assertEquals(100, project.getProgress());
            assertTrue(project.isCompleted());
            assertFalse(project.contains(task));
    }

    @Test
    void testEmptyNullDescription() {
        String str = "";
        try {
            project = new Project(str);
            fail();
        } catch (EmptyStringException e) {}
        str = null;
        try {
            project = new Project(str);
            fail();
        } catch (EmptyStringException e) {}
        str = "a";
        try {
            project = new Project(str);
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testNullTask() {
        Task task = null;
        try {
            project.add(task);
            fail();
        } catch (NullArgumentException n) {}
        try {
            project.remove(task);
            fail();
        } catch (NullArgumentException n) {}
        try {
            project.contains(task);
            fail();
        } catch (NullArgumentException n) {}
        try {
            task = new Task("a");
            project.add(task);
        } catch (NullArgumentException n) {
            fail();
        }
        try {
            task = new Task("a");
            project.remove(task);
        } catch (NullArgumentException n) {
            fail();
        }
        try {
            task = new Task("a");
            project.contains(task);
        } catch (NullArgumentException n) {
            fail();
        }
    }
}