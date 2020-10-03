package parser;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsers.TagParser;
import parsers.exceptions.ParsingException;
import sun.security.krb5.internal.PAData;
import sun.security.krb5.internal.PAEncTSEnc;

import static org.junit.jupiter.api.Assertions.*;

import model.DueDate;
import model.Priority;
import model.Status;
import model.Task;
import parsers.exceptions.ParsingException;

public class TestTagParser {


    @Test
    void testNoTag() {
        try {
            TagParser tp = new TagParser();
            Task task = new Task("");
            tp.parse("", task);
            fail();
        } catch (EmptyStringException e) {
        } catch (ParsingException e) {
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("abc");
            assertEquals(task.getDescription(), "abc");
            tp.parse("abc", task);
            fail();
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("abc ## ");
            tp.parse("abc ## ", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("## ");
            tp.parse("## ", task);
            fail();
        } catch (EmptyStringException e) {
        } catch (ParsingException e) {
            fail();
        }
    }

    @Test
    void testOneTag() {
        try {
            TagParser tp = new TagParser();
            Task task = new Task("abc ## important");
            tp.parse("abc ## important", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }
    }

    @Test
    void testMultipleTags() {
        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; tomorrow; important; urgent; in progress");
            tp.parse("Register for the course. ## cpsc210; tomorrow; important; urgent; in progress", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; today; important; important; up next");
            tp.parse("Register for the course. ## cpsc210; today; important; important; up next", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; today; urgent; urgent; to do");
            tp.parse("Register for the course. ## cpsc210; today; urgent; urgent; to do", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; tomorrow; urgent; important; to do");
            tp.parse("Register for the course. ## cpsc210; tomorrow; urgent; important; to do", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; today; today; tomorrow");
            tp.parse("Register for the course. ## cpsc210; today; today; tomorrow", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; tomorrow; today; today");
            tp.parse("Register for the course. ## cpsc210; tomorrow; today; today", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; to do; in progress");
            tp.parse("Register for the course. ## cpsc210; to do; in progress", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; to do; to do; in progress");
            tp.parse("Register for the course. ## cpsc210; to do; to do; in progress", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; in progress; up next");
            tp.parse("Register for the course. ## cpsc210; in progress; up next", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; in progress; in progress; up next");
            tp.parse("Register for the course. ## cpsc210; in progress; in progress; up next", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; up next; done");
            tp.parse("Register for the course. ## cpsc210; up next; done", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; up next; up next; done");
            tp.parse("Register for the course. ## cpsc210; up next; up next; done", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; done; to do");
            tp.parse("Register for the course. ## cpsc210; done; to do", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }

        try {
            TagParser tp = new TagParser();
            Task task = new Task("Register for the course. ## cpsc210; done; done; to do");
            tp.parse("Register for the course. ## cpsc210; done; done; to do", task);
        } catch (EmptyStringException e) {
            fail();
        } catch (ParsingException e) {
            fail();
        }
    }
}
