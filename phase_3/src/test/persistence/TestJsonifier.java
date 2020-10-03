package persistence;

import model.DueDate;
import model.Priority;
import model.Task;
import model.Tag;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class TestJsonifier {
    private Jsonifier jsonifier;
    private Task task1;
    private Task task2;
    private List<Task> tasks;

    @BeforeEach
    void runBefore() {
        jsonifier = new Jsonifier();
        task1 = new Task("Register for the course. ## cpsc210; " +
                "tomorrow; important; urgent; in progress");
        task2 = new Task("Complete phase 3 of the project. ## cpsc210; " +
                "phase3; tomorrow; important; urgent; in progress");
        tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
    }

    @Test
    void testTagToJson() {
        Tag tag = new Tag("phase3");
        JSONObject tagJson = Jsonifier.tagToJson(tag);
        assertEquals("{\"name\":\"phase3\"}", tagJson.toString());
    }

    @Test
    void testPriorityToJson() {
        Priority priority = new Priority(1);
        JSONObject priorityJson = Jsonifier.priorityToJson(priority);
        assertEquals("{\"important\":true,\"urgent\":true}", priorityJson.toString());
    }

    @Test
    void testDueDateToJsonNotNull() {
        DueDate dueDate = new DueDate();
        JSONObject dueDateJson = Jsonifier.dueDateToJson(dueDate);
        assertEquals(2019, dueDateJson.get("year"));
        assertEquals(2, dueDateJson.get("month"));
        assertEquals(8, dueDateJson.get("day"));
        assertEquals(23, dueDateJson.get("hour"));
        assertEquals(59, dueDateJson.get("minute"));
    }

    @Test
    void testTaskToJson() {
        JSONObject taskJson = Jsonifier.taskToJson(task1);
        assertEquals("Register for the course. ", taskJson.get("description"));
        assertEquals("[{\"name\":\"cpsc210\"}]", taskJson.get("tags").toString());

        DueDate dueDate = new DueDate();
        dueDate.postponeOneDay();
        JSONObject dueDateJson = Jsonifier.dueDateToJson(dueDate);
        JSONObject taskDueDateJson = (JSONObject) taskJson.get("due-date");
        assertEquals(dueDateJson.get("year"), taskDueDateJson.get("year"));
        assertEquals(dueDateJson.get("month"), taskDueDateJson.get("month"));
        assertEquals(dueDateJson.get("day"), taskDueDateJson.get("day"));
        assertEquals(dueDateJson.get("hour"), taskDueDateJson.get("hour"));
        assertEquals(dueDateJson.get("minute"), taskDueDateJson.get("minute"));

        task1.setDueDate(null);
        taskJson = Jsonifier.taskToJson(task1);
        assertEquals(JSONObject.NULL, taskJson.get("due-date"));



        Priority priority = new Priority(1);
        JSONObject priorityJson = Jsonifier.priorityToJson(priority);
        JSONObject taskPriorityJson = (JSONObject) taskJson.get("priority");
        assertEquals(priorityJson.get("important"), taskPriorityJson.get("important"));
        assertEquals(priorityJson.get("urgent"), taskPriorityJson.get("urgent"));
        assertEquals("IN_PROGRESS", taskJson.get("status"));
    }

    @Test
    void testtaskListToJson() {
        JSONArray tasksArray = Jsonifier.taskListToJson(tasks);

        JSONObject task1Json = (JSONObject) tasksArray.get(0);
        assertEquals("Register for the course. ", task1Json.get("description"));
        assertEquals("[{\"name\":\"cpsc210\"}]", task1Json.get("tags").toString());

        DueDate dueDate = new DueDate();
        dueDate.postponeOneDay();
        JSONObject dueDateJson = Jsonifier.dueDateToJson(dueDate);
        JSONObject taskDueDateJson = (JSONObject) task1Json.get("due-date");
        assertEquals(dueDateJson.get("year"), taskDueDateJson.get("year"));
        assertEquals(dueDateJson.get("month"), taskDueDateJson.get("month"));
        assertEquals(dueDateJson.get("day"), taskDueDateJson.get("day"));
        assertEquals(dueDateJson.get("hour"), taskDueDateJson.get("hour"));
        assertEquals(dueDateJson.get("minute"), taskDueDateJson.get("minute"));


        Priority priority = new Priority(1);
        JSONObject priorityJson = Jsonifier.priorityToJson(priority);
        JSONObject taskPriorityJson = (JSONObject) task1Json.get("priority");
        assertEquals(priorityJson.get("important"), taskPriorityJson.get("important"));
        assertEquals(priorityJson.get("urgent"), taskPriorityJson.get("urgent"));
        assertEquals("IN_PROGRESS", task1Json.get("status"));

        JSONObject task2Json = (JSONObject) tasksArray.get(1);
        assertEquals("Complete phase 3 of the project. ", task2Json.get("description"));
        Set<Tag> tags = task2.getTags();
        JSONArray tagsArray = Jsonifier.tagListToJson(tags);
        JSONArray task2TagsArray = (JSONArray) task2Json.get("tags");
        JSONObject tag1Json = (JSONObject) tagsArray.get(0);
        JSONObject task2Tag1Json = (JSONObject) task2TagsArray.get(0);
        assertEquals(tag1Json.get("name"), task2Tag1Json.get("name"));
        JSONObject tag2Json = (JSONObject) tagsArray.get(1);
        JSONObject task2Tag2Json = (JSONObject) task2TagsArray.get(1);
        assertEquals(tag2Json.get("name"), task2Tag2Json.get("name"));

        taskDueDateJson = (JSONObject) task2Json.get("due-date");
        assertEquals(dueDateJson.get("year"), taskDueDateJson.get("year"));
        assertEquals(dueDateJson.get("month"), taskDueDateJson.get("month"));
        assertEquals(dueDateJson.get("day"), taskDueDateJson.get("day"));
        assertEquals(dueDateJson.get("hour"), taskDueDateJson.get("hour"));
        assertEquals(dueDateJson.get("minute"), taskDueDateJson.get("minute"));


        taskPriorityJson = (JSONObject) task2Json.get("priority");
        assertEquals(priorityJson.get("important"), taskPriorityJson.get("important"));
        assertEquals(priorityJson.get("urgent"), taskPriorityJson.get("urgent"));
        assertEquals("IN_PROGRESS", task2Json.get("status"));
    }



}
