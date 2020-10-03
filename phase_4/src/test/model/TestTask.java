package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TestTask {

    Task task1;
    Task task2;
    DueDate postponeDueDate;
    Priority testPriority;
    Status testStatus;

    @BeforeEach
    void init() {
        task1 = new Task("play");
        task2 = new Task("work");
        postponeDueDate = new DueDate();
        postponeDueDate.postponeOneDay();
        testPriority = new Priority(1);
        testStatus = Status.UP_NEXT;
    }

    @Test
    void testTaskNotEqualDefault() {
        assertFalse(task1.equals(task2));
    }

    @Test
    void testTaskEqualDefault() {
        Task task3 = new Task("play");
        assertTrue(task1.equals(task3));
        assertEquals(task1.hashCode(), task3.hashCode());
        assertEquals(task3.hashCode(), task1.hashCode());
    }

    @Test
    void testTaskNotEqualDifferentDueDate() {
        Task task3 = new Task("play");
        task1.setDueDate(postponeDueDate);
        assertFalse(task3.equals(task1));
    }

    @Test
    void testTaskNotEqualDifferentStatus() {
        Task task3 = new Task("play");
        task1.setStatus(testStatus);
        assertFalse(task3.equals(task1));
    }

    @Test
    void testTaskNotEqualDifferentPriority() {
        Task task3 = new Task("play");
        task1.setPriority(testPriority);
        assertFalse(task3.equals(task1));
    }

    @Test
    void testTaskAddSingleTag() {
        Task task3 = new Task("play");
        task3.addTag(new Tag("test"));
        assertTrue(task3.equals(task1));
    }

    @Test
    void testEqualsTaskEmptyExpectException() {
        try {
            Task task3 = new Task("");
            fail();
        } catch (EmptyStringException e) {

        }
    }

    @Test
    void testEqualsTaskIsNull() {
        try {
            Task task3 = null;
            task3.equals(task1);
            fail();
        } catch (NullPointerException e) {

        }
    }

    @Test
    void testAddTagNormalNoExceptionExpected() {
        try {
            Tag tag = new Tag("test");
            task1.addTag(tag);
            assertEquals(true, task1.getTags().contains(tag));
        } catch (NullArgumentException e) {
            fail();
        }
    }

    @Test
    void testAddTagNormalNullArgumentExceptionExpected() {
        try {
            Tag tag = null;
            task1.addTag(tag);
            fail();
        } catch (NullArgumentException e) {

        }
    }

    @Test
    void testAddTagNoDuplicatesNoExceptionExpected() {
        try {
            Tag tag = new Tag("test");
            Set<Tag> tags = new HashSet<>();
            tags.add(tag);
            Tag tag2 = new Tag("test");
            task1.addTag(tag);
            task1.addTag(tag2);
            assertEquals(tags, task1.getTags());
        } catch (NullArgumentException e) {
            fail();
        }
    }

    @Test
    void testAddTagNormalMultipleTagsNoExceptionExpected() {
        try {
            Tag tag = new Tag("test");
            Tag tag2 = new Tag("hello");
            task1.addTag(tag);
            task1.addTag(tag2);
            assertTrue(task1.getTags().contains(tag) && task1.getTags().contains(tag2));
        } catch (NullArgumentException e) {
            fail();
        }
    }

    @Test
    void testRemoveTagNormalBidirectional() {
        try {
            Tag tag = new Tag("test");
            task1.addTag(tag);
            task1.removeTag(tag);
            assertTrue(!task1.getTags().contains(tag));
            assertTrue(!tag.getTasks().contains(task1));
        } catch (NullArgumentException e) {
            fail();
        }
    }

    @Test
    void testRemoveTagNormalNullArgumentExceptionExpected() {
        try {
            Tag tag = new Tag("test");
            Tag tag2 = null;
            task1.addTag(tag);
            task1.removeTag(tag2);
            fail();
        } catch (NullArgumentException e) {

        }
    }

    @Test
    void testAddTagAndRemoveTagString() {
        task1.addTag("math");
        List<Tag> testTagList = new ArrayList<Tag>();
        testTagList.add(new Tag("math"));
        assertEquals(task1.getTags().toString(), testTagList.toString());

        task1.removeTag("math");
        testTagList.clear();
        assertEquals(task1.getTags().toString(), testTagList.toString());
    }

    @Test
    void testTaskExceptionSetPriority() {
        try {
            task1.setPriority(null);
            fail();
        } catch (NullArgumentException e) {
        }
    }

    @Test
    void testTaskExceptionSetStatus() {
        try {
            task1.setStatus(null);
            fail();
        } catch (NullArgumentException e) {
        }
    }

    @Test
    void testTaskExceptionSetDescription() {
        try {
            task1.setDescription(null);
            fail();
        } catch (EmptyStringException e) {
        }
    }

    @Test
    void testNegativeInputException() {
        NegativeInputException testException = new NegativeInputException();
        NegativeInputException exceptionName = new NegativeInputException("test");
        try {
            task1.setEstimatedTimeToComplete(10);
        } catch (InvalidProgressException e) {
            fail();
        }

        try {
            task1.setProgress(-1);
            fail();
        } catch (InvalidProgressException e) {
        }
    }

    @Test
    void testTaskContainsTagEmptyTag() {
        try {
            task1.containsTag("");
            fail();
        } catch (EmptyStringException e) {
        }
    }

    @Test
    void testTaskContainsTag() {
        task1.addTag("math");
        assertTrue(task1.containsTag("math"));
    }

    @Test
    void testParseDescription() {
        task1.setDescription("cs");
        assertEquals("cs", task1.getDescription());
    }

    @Test
    void testInvalidProgressException() {
        InvalidProgressException testException = new InvalidProgressException();
        InvalidProgressException exceptionName = new InvalidProgressException("test");
        try {
            task1.setProgress(101);
            fail();
        } catch (InvalidProgressException e) {
        }

        try {
            task1.setProgress(-1);
            fail();
        } catch (InvalidProgressException e) {
        }

        try {
            task1.setProgress(50);
        } catch (InvalidProgressException e) {
            fail();
        }
    }

    @Test
    void testGetStatus() {
        task1.setStatus(Status.UP_NEXT);
        assertEquals(Status.UP_NEXT, task1.getStatus());
    }

    @Test
    void testGetPriority() {
        task1.setPriority(new Priority(1));
        assertEquals(new Priority(1), task1.getPriority());
    }

    @Test
    void testGetDueDate() {
        task1.setDueDate(new DueDate());
        assertEquals(new DueDate(), task1.getDueDate());
    }



    @Test
    void testEqualsNotTask() {
        Project project1 = new Project("test");

        assertEquals(false, task1.equals(project1));
    }

    @Test
    void testSetEstimatedTimeToComplete() {
        task1.setEstimatedTimeToComplete(100);
        assertEquals(100, task1.getEstimatedTimeToComplete());
    }

    @Test
    void testSetEstimatedTimeToCompleteExceptionExpected() {
        try {
            task1.setEstimatedTimeToComplete(-1);
            fail();
        } catch (NegativeInputException e) {
        }
    }

    @Test
    void testSetProgress() {
        task1.setProgress(50);
        assertEquals(50, task1.getProgress());
    }

    @Test
    void testTagsToString() {
        task1.addTag("cs");
        task1.addTag("math");

        task1.setDueDate(new DueDate());

        task1.setStatus(Status.UP_NEXT);

        task1.setPriority(new Priority(3));

        assertEquals("\n" +
                "{\n" +
                "\tDescription: play\n" +
                "\tDue date: Fri Mar 29 2019 11:59 PM\n" +
                "\tStatus: UP NEXT\n" +
                "\tPriority: URGENT\n" +
                "\tTags: #math, #cs\n" +
                "}", task1.toString());
    }

    @Test
    void testTaskEqualsSameObject() {
        assertEquals(true, task1.equals(task1));
    }

    @Test
    void testTagsToStringEmptyTags() {

        task1.setDueDate(new DueDate());

        task1.setStatus(Status.UP_NEXT);

        task1.setPriority(new Priority(3));

        assertEquals("\n" +
                "{\n" +
                "\tDescription: play\n" +
                "\tDue date: Fri Mar 29 2019 11:59 PM\n" +
                "\tStatus: UP NEXT\n" +
                "\tPriority: URGENT\n" +
                "\tTags:  \n" +
                "}", task1.toString());
    }

    @Test
    void testContainsTag() {
        try {
            task1.containsTag((String) null);
            fail();
        } catch (EmptyStringException e) {
        }
    }

    @Test
    void testSetDescription() {
        try {
            task1.setDescription("");
            fail();
        } catch (EmptyStringException e) {
        }
    }





}
