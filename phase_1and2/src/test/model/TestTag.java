package model;

import model.exceptions.EmptyStringException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTag {

    private Tag tag;

    @BeforeEach
    void runBefore() {
        tag = new Tag("Homework");
    }

    @Test
    void testContructor() {
        assertEquals("Homework", tag.getName());
        assertEquals("Homework", tag.toString());
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
}
