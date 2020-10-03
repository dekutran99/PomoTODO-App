package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

// Represents Task parser
public class TaskParser {

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {
        List<Task> tasks = new ArrayList<>();
        JSONArray tasksArray = new JSONArray(input);

        for (Object object : tasksArray) {
            JSONObject taskJson = (JSONObject) object;
            Task task = new Task(" ");
            try {
                parseTaskJson(taskJson, task);
            } catch (JSONException e) {
                continue;
            }
            tasks.add(task);
        }
        return tasks;
    }

    private void parseTaskJson(JSONObject taskJson, Task task) {
        if (taskJson.get("description") instanceof String) {
            task.setDescription(taskJson.getString("description"));
        } else {
            throw new JSONException("Invalid data type");
        }
        parseTagListJson(taskJson.getJSONArray("tags"), task);
        if (taskJson.get("due-date") != JSONObject.NULL) {
            parseDueDateJson(taskJson.getJSONObject("due-date"), task);
        } else {
            task.setDueDate(null);
        }
        parsePriorityJson(taskJson.getJSONObject("priority"), task);
        parseStatusJson(taskJson.getString("status"), task);
    }

    private void parseTagListJson(JSONArray tagsArray, Task task) {
        for (Object objectTag : tagsArray) {
            JSONObject tagJson = (JSONObject) objectTag;
            task.addTag(tagJson.getString("name"));
        }
    }

    private void parseDueDateJson(JSONObject dueDateJson, Task task) {
        checkClassDueDateJsonValues(dueDateJson);
        int year = (dueDateJson.getInt("year"));
        int month = dueDateJson.getInt("month");
        int day = dueDateJson.getInt("day");
        int hrs = dueDateJson.getInt("hour");
        int min = dueDateJson.getInt("minute");
        DueDate dueDate = new DueDate(new Date(year - 1900, month, day, hrs, min));
        task.setDueDate(dueDate);
    }

    private void checkClassDueDateJsonValues(JSONObject dueDateJson) {
        if (!(dueDateJson.get("year") instanceof Integer)
                || !(dueDateJson.get("month") instanceof Integer)
                || !(dueDateJson.get("day") instanceof Integer)
                || !(dueDateJson.get("day") instanceof Integer)
                || !(dueDateJson.get("hour") instanceof Integer)
                || !(dueDateJson.get("minute") instanceof Integer)) {
            throw new JSONException("Invalid data type.");
        }
    }

    private void parsePriorityJson(JSONObject priorityJson, Task task) {
        checkClassPriorityJsonValues(priorityJson);
        boolean important = priorityJson.getBoolean("important");
        boolean urgent = priorityJson.getBoolean("urgent");
        if (important == true && urgent == true) {
            task.setPriority(new Priority(1));
        } else if (important == true && urgent == false) {
            task.setPriority(new Priority(2));
        } else if (important == false && urgent == true) {
            task.setPriority(new Priority(3));
        } else if (important == false && urgent == false) {
            task.setPriority(new Priority(4));
        }
    }

    private void checkClassPriorityJsonValues(JSONObject priorityJson) {
        if (!(priorityJson.get("important") instanceof Boolean)
                || !(priorityJson.get("urgent") instanceof Boolean)) {
            throw new JSONException("Invalid data type.");
        }
    }

    private void parseStatusJson(String status, Task task) {
        if (status.equals("TODO")) {
            task.setStatus(Status.TODO);
        } else if (status.equals("UP_NEXT")) {
            task.setStatus(Status.UP_NEXT);
        } else if (status.equals("IN_PROGRESS")) {
            task.setStatus(Status.IN_PROGRESS);
        } else if (status.equals("DONE")) {
            task.setStatus(Status.DONE);
        } else {
            throw new JSONException("Invalid value for this key");
        }
    }
}
