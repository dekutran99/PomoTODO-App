package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order in which tasks are added to project is preserved
public class Project {

    private List<Task> tasks;
    private String description;
    
    // REQUIRES: description is non-empty
    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    public Project(String description) {
        try {
            if (description.equals("")) {
                throw new EmptyStringException("Description of this project is an empty-string.");
            }
            this.description = description;
        } catch (NullPointerException n) {
            throw new EmptyStringException("Description of this project is null.");
        }
        this.tasks = new LinkedList<>();
    }
    
    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    public void add(Task task) {
        try {
            task.getDescription();
            if (!(tasks.contains(task))) {
                tasks.add(task);
            }
        } catch (NullPointerException n) {
            throw new NullArgumentException("Task is null.");
        }
    }
    
    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: removes task from this project
    public void remove(Task task) {
        try {
            task.getDescription();
            tasks.remove(task);
        } catch (NullPointerException n) {
            throw new NullArgumentException("Task is null.");
        }
    }
    
    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }
    
    // EFFECTS: returns an unmodifiable list of tasks in this project.
    public List<Task> getTasks() {
        return tasks;
    }
    
    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completed tasks (rounded down to the closest integer).
    //     returns 100 if this project has no tasks!
    public int getProgress() {
        if (tasks.size() == 0) {
            return 100;
        }
        int count = 0;
        Iterator<Task> itr = tasks.iterator();
        while (itr.hasNext()) {
            if (itr.next().getStatus().getDescription().equals("DONE")) {
                count++;
            }
        }
        return count * 100 / (tasks.size());
    }
    
    // EFFECTS: returns the number of tasks in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }
    
    // EFFECTS: returns true if every task in this project is completed
    //     returns false if this project has no tasks!
    public boolean isCompleted() {
        if (this.getProgress() == 100) {
            return true;
        }
        return false;
    }
    
    // REQUIRES: task != null
    // EFFECTS: returns true if this project contains the task
    public boolean contains(Task task) {
        boolean boo = false;
        try {
            task.getDescription();
            boo = tasks.contains(task);
        } catch (NullPointerException n) {
            throw new NullArgumentException("Task is null");
        }
        return boo;
    }
}