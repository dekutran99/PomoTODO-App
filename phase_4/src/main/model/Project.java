package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
    private List<Todo> tasks;
    
    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        tasks = new ArrayList<>();
    }
    
    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (!contains(task)) {
            if (!task.equals(this)) {
                tasks.add(task);
            }
        }
    }
    
    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (contains(task)) {
            tasks.remove(task);
        }
    }
    
    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        //return Collections.unmodifiableList(tasks);
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the nearest integer).
    //     the value returned is the average of the percentage of completion of
    //     all the tasks and sub-projects in this project.
    public int getProgress() {
        if (tasks.size() == 0) {
            return 0;
        }
        int progress = 0;
        int count = 0;
        for (Todo t : tasks) {
            progress += t.getProgress();
            count++;
        }
        return progress / count;
    }

    /*
    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completed tasks (rounded down to the closest integer).
    //     returns 100 if this project has no tasks!
    public int getProgress() {
        int numerator = getNumberOfCompletedTasks();
        int denominator = getNumberOfTasks();
        if (numerator == denominator) {
            return 100;
        } else {
            return (int) Math.floor(numerator * 100.0 / denominator);
        }
    }
    */

    /*
    // EFFECTS: returns the number of completed tasks in this project
    private int getNumberOfCompletedTasks() {
        int done = 0;
        for (Task t : tasks) {
            if (t.getStatus() == Status.DONE) {
                done++;
            }
        }
        return done;
    }
    */

    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
    //     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }
    
    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }

    // EFFECTS: return a non-negative integer as the Estimated Time To Complete
    // Note: Estimated time to complete is a value that is expressed in
    //       hours of work required to complete a task or project.
    public int getEstimatedTimeToComplete() {
        int hours = 0;
        for (Todo t : tasks) {
            hours += t.getEstimatedTimeToComplete();
        }
        return hours;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public Iterator<Todo> iterator() {
        return new ProjectIterator(new FirstIterator(), new SecondIterator(), new ThirdIterator(),
                new FourthIterator());
    }

    private class ProjectIterator implements Iterator<Todo> {

        private Iterator<Todo> it1;
        private Iterator<Todo> it2;
        private Iterator<Todo> it3;
        private Iterator<Todo> it4;

        public ProjectIterator(Iterator<Todo> it1, Iterator<Todo> it2,
                Iterator<Todo> it3, Iterator<Todo> it4) {
            this.it1 = it1;
            this.it2 = it2;
            this.it3 = it3;
            this.it4 = it4;
        }

        @Override
        public boolean hasNext() {
            return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext();
        }

        @Override
        public Todo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (it1.hasNext()) {
                return it1.next();
            } else if (it2.hasNext()) {
                return it2.next();
            } else if (it3.hasNext()) {
                return it3.next();
            } else {
                return it4.next();
            }
        }
    }

    private class FirstIterator implements Iterator<Todo> {


        private Iterator todoIterator = tasks.iterator();

        @Override
        public boolean hasNext() {
            return todoIterator.hasNext();
        }

        @Override
        public Todo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Todo todo = (Todo) todoIterator.next();
            if (todo.getPriority().isUrgent() && todo.getPriority().isImportant()) {

                return todo;
            } else {
                return null;
            }
        }
    }

    private class SecondIterator implements Iterator<Todo> {

        private Iterator todoIterator = tasks.iterator();

        @Override
        public boolean hasNext() {
            return todoIterator.hasNext();
        }

        @Override
        public Todo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Todo todo = (Todo) todoIterator.next();
            if (todo.getPriority().isImportant() && !todo.getPriority().isUrgent()) {
                return todo;
            } else {
                return null;
            }
        }
    }

    private class ThirdIterator implements Iterator<Todo> {

        private Iterator todoIterator = tasks.iterator();

        @Override
        public boolean hasNext() {
            return todoIterator.hasNext();
        }

        @Override
        public Todo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Todo todo = (Todo) todoIterator.next();
            if (todo.getPriority().isUrgent() && !todo.getPriority().isImportant()) {
                return todo;
            } else {
                return null;
            }
        }
    }

    private class FourthIterator implements Iterator<Todo> {

        private Iterator todoIterator = tasks.iterator();

        @Override
        public boolean hasNext() {
            return todoIterator.hasNext();
        }

        @Override
        public Todo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Todo todo = (Todo) todoIterator.next();
            if (!todo.getPriority().isUrgent() && !todo.getPriority().isImportant()) {
                return todo;
            } else {
                return null;
            }
        }
    }

    public void sorted() {
        for (Todo t : this) {
            System.out.println(t);
        }
    }
    
}