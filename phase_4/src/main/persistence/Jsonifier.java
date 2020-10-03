package persistence;

import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();

        tagJson.put("name", tag.getName());

        return tagJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityJson = new JSONObject();

        priorityJson.put("important", priority.isImportant());
        priorityJson.put("urgent", priority.isUrgent());

        return priorityJson;
    }

    // EFFECTS: returns JSON respresentation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject dueDateJson = new JSONObject();
        if (dueDate != null) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(dueDate.getDate());

            dueDateJson.put("year", cal.get(GregorianCalendar.YEAR));
            dueDateJson.put("month", cal.get(GregorianCalendar.MONTH));
            dueDateJson.put("day", cal.get(GregorianCalendar.DAY_OF_MONTH));
            dueDateJson.put("hour", cal.get(GregorianCalendar.HOUR_OF_DAY));
            dueDateJson.put("minute", cal.get(GregorianCalendar.MINUTE));
        }
        return dueDateJson;
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject taskJson = new JSONObject();

        taskJson.put("description", task.getDescription());
        taskJson.put("tags", tagListToJson(task.getTags()));
        if (task.getDueDate() != null) {
            taskJson.put("due-date", dueDateToJson(task.getDueDate()));
        } else {
            taskJson.put("due-date", JSONObject.NULL);
        }
        taskJson.put("priority", priorityToJson(task.getPriority()));

        taskJson.put("status", task.getStatus().toString().replace(" ", "_"));

        return taskJson;
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray tasksArray = new JSONArray();

        for (Task task: tasks) {
            tasksArray.put(taskToJson(task));
        }

        return tasksArray;
    }

    // EFFECTS: return JSON array representing list of tags
    public static JSONArray tagListToJson(Set<Tag> tags) {
        JSONArray tagsArray = new JSONArray();

        for (Tag tag : tags) {
            tagsArray.put(tagToJson(tag));
        }

        return tagsArray;
    }
}