package parsers;

import model.*;

import model.exceptions.NullArgumentException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestTaskParser {

    private TaskParser tp;
    private String json;

    @BeforeEach
    void runBefore() {
        tp = new TaskParser();
        json = "[\n" +
                "{\n" +
                "  \"description\":\"Register for the course. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":16,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":true,\"urgent\":true},\n" +
                "  \"status\":\"IN_PROGRESS\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Download the syllabus. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":null,\n" +
                "  \"priority\":{\"important\":true,\"urgent\":false},\n" +
                "  \"status\":\"UP_NEXT\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Read the syllabus! \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":null,\n" +
                "  \"priority\":{\"important\":false,\"urgent\":true},\n" +
                "  \"status\":\"TODO\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Make note of assignments deadlines. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"},{\"name\":\"assigns\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":17,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                "  \"status\":\"DONE\"\n" +
                "}" +
                "\n]";
    }

    @Test
    void testParse() {
        List<Task> tasks = tp.parse(json);

        //System.out.println(tasks);

        Task task1 = tasks.get(0);
        assertEquals("Register for the course. ", task1.getDescription());
        DueDate dueDate = new DueDate(new Date(2019 - 1900, 0, 16, 23, 59));
        assertEquals(dueDate, task1.getDueDate());
        assertEquals(Status.IN_PROGRESS, task1.getStatus());
        assertEquals(new Priority(1), task1.getPriority());
        assertTrue(task1.getTags().contains(new Tag("cpsc210")));

        Task task2 = tasks.get(1);
        assertEquals("Download the syllabus. ", task2.getDescription());
        assertEquals(null, task2.getDueDate());
        assertEquals(Status.UP_NEXT, task2.getStatus());
        assertEquals(new Priority(2), task2.getPriority());
        assertTrue(task2.getTags().contains(new Tag("cpsc210")));

        Task task3 = tasks.get(2);
        assertEquals("Read the syllabus! ", task3.getDescription());
        //dueDate = new DueDate(new Date(2019 - 1900, 0, 17, 23, 59));
        assertEquals(null, task3.getDueDate());
        assertEquals(Status.TODO, task3.getStatus());
        assertEquals(new Priority(3), task3.getPriority());
        assertTrue(task3.getTags().contains(new Tag("cpsc210")));

        Task task4 = tasks.get(3);
        assertEquals("Make note of assignments deadlines. ", task4.getDescription());
        dueDate = new DueDate(new Date(2019 - 1900, 0, 17, 23, 59));
        assertEquals(dueDate, task4.getDueDate());
        assertEquals(Status.DONE, task4.getStatus());
        assertEquals(new Priority(4), task4.getPriority());
        assertTrue(task4.getTags().contains(new Tag("cpsc210")));
        assertTrue(task4.getTags().contains(new Tag("assigns")));
    }

    @Test
    void testParseWithExceptionHandling() {
        json = "[\n" +
                "{\n" +
                "  \"descripton\":\"Register for the course. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":16,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":true,\"urgent\":true},\n" +
                "  \"status\":\"IN_PROGRESS\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Download the syllabus. \",\n" +
                "  \"tags\":[{\"nae\":\"cpsc210\"}],\n" +
                "  \"due-date\":null,\n" +
                "  \"priority\":{\"important\":true,\"urgent\":false},\n" +
                "  \"status\":\"UP_NEXT\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Read the syllabus! \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-dae\":null,\n" +
                "  \"priority\":{\"important\":false,\"urgent\":true},\n" +
                "  \"status\":\"TODO\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Make note of assignments deadlines. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"},{\"name\":\"assigns\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":17,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":abc,\"urgent\":false},\n" +
                "  \"status\":\"DONE\"\n" +
                "}" +
                "\n]";

        List<Task> tasks = tp.parse(json);
        assertTrue(tasks.isEmpty());
        //System.out.println(tasks);
    }

    @Test
    void testParseWithExceptionHandlingWithTwoIncorrectJson() {
        json = "[\n" +
                "{\n" +
                "  \"descrtion\":\"Register for the course. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,month:0,\"day\":16,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":true,\"urgent\":true},\n" +
                "  \"status\":\"IN_PROGRESS\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Download the syllabus. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":null,\n" +
                "  \"priority\":{\"important\":true,\"urgent\":false},\n" +
                "  \"status\":\"UP_NEXT\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Read the syllabus! \",\n" +
                "  \"tags\":[{\"nae\":\"cpsc210\"}],\n" +
                "  \"due-date\":null,\n" +
                "  \"priority\":{\"important\":false,\"urgent\":true},\n" +
                "  \"status\":\"TODO\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Make note of assignments deadlines. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"},{\"name\":\"assigns\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":17,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                "  \"status\":\"DONE\"\n" +
                "}" +
                "\n]";

        List<Task> tasks = tp.parse(json);

        //System.out.println(tasks);

        Task task2 = tasks.get(0);
        assertEquals("Download the syllabus. ", task2.getDescription());
        assertEquals(null, task2.getDueDate());
        assertEquals(Status.UP_NEXT, task2.getStatus());
        assertEquals(new Priority(2), task2.getPriority());
        assertTrue(task2.getTags().contains(new Tag("cpsc210")));

        Task task4 = tasks.get(1);
        assertEquals("Make note of assignments deadlines. ", task4.getDescription());
        DueDate dueDate = new DueDate(new Date(2019 - 1900, 0, 17, 23, 59));
        assertEquals(dueDate, task4.getDueDate());
        assertEquals(Status.DONE, task4.getStatus());
        assertEquals(new Priority(4), task4.getPriority());
        assertTrue(task4.getTags().contains(new Tag("cpsc210")));
        assertTrue(task4.getTags().contains(new Tag("assigns")));
    }

    @Test
    void testParseWithExceptionHandlingWithIncorrectDueDateValue() {
        json = "[\n" +
                "{\n" +
                "  \"description\":\"Register for the course. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":\"2019\",\"month\":0,\"day\":16,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":true,\"urgent\":true},\n" +
                "  \"status\":\"IN_PROGRESS\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Download the syllabus. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":\"0\",\"day\":16,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":true,\"urgent\":false},\n" +
                "  \"status\":\"UP_NEXT\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Read the syllabus! \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":\"16\",\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":false,\"urgent\":true},\n" +
                "  \"status\":\"TODO\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Make note of assignments deadlines. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"},{\"name\":\"assigns\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":17,\"hour\":\"23\",\"minute\":59},\n" +
                "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                "  \"status\":\"DONE\"\n" +
                "}, \n" +
                "{\n" +
                "  \"description\":\"Make note of assignments deadlines. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"},{\"name\":\"assigns\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":17,\"hour\":23,\"minute\":\"59\"},\n" +
                "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                "  \"status\":\"DONE\"\n" +
                "}" +
                "\n]";

                List<Task> tasks = tp.parse(json);
                assertTrue(tasks.isEmpty());
    }

    @Test
    void testParseWithExceptionHandlingWithIncorrectPriorityValue() {
        json = "[\n" +
                "{\n" +
                "  \"description\":\"Register for the course. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":16,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":\"true\",\"urgent\":true},\n" +
                "  \"status\":\"IN_PROGRESS\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Make note of assignments deadlines. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"},{\"name\":\"assigns\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":17,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":false,\"urgent\":\"false\"},\n" +
                "  \"status\":\"DONE\"\n" +
                "}" +
                "\n]";
        List<Task> tasks = tp.parse(json);
        assertTrue(tasks.isEmpty());
        //System.out.println(json);
    }
}
