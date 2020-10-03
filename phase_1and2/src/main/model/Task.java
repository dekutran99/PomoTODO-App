package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import parsers.AdvancedTagParser;
import parsers.TagParser;
import parsers.exceptions.ParsingException;

import java.util.*;

// Represents a Task having a description, status, priorities, set of tags and due date.
public class Task {

    public static final DueDate NO_DUE_DATE = null;

    private String description;
    private DueDate dueDate;
    private Priority priority;
    private Status status;
    private List<Tag> tags;
    private Set<Tag> tagSet = new HashSet<Tag>();

    // REQUIRES: description is non-empty
    // MODIFIES: this
    // EFFECTS: constructs a task with the given description
    //    parses the description to extract meta-data (i.e., tags, status, priority and deadline).
    //    If description does not contain meta-data, the task is set to have no due date,
    //    status of 'To Do', and default priority level (i.e., not important nor urgent)
    public Task(String description) {
        try {
            if (description.equals("")) {
                throw new EmptyStringException("Description of this task is an empty-string.");
            }
        } catch (NullPointerException n) {
            throw new EmptyStringException("Description of this task is null.");
        }
        this.description = description;
        dueDate = NO_DUE_DATE;
        priority = new Priority();
        status = Status.TODO;
        tags = new ArrayList<Tag>();
        if (description.contains("##")) {
            try {
                TagParser tp = new AdvancedTagParser();
                tp.parse(description, this);
                //System.out.println(tp.getDescription());
            } catch (ParsingException e) {
                e.printStackTrace();
            }
        }
    }

    // REQUIRES: name is non-empty
    // MODIFIES: this
    // EFFECTS: creates a tag with name tagName and adds it to this task
    // Note: no two tags are to have the same name
    public void addTag(String tagName) {
        try {
            if (tagName.equals("")) {
                throw new EmptyStringException("The name of this tag is an empty-string.");
            }
        } catch (NullPointerException n) {
            throw new EmptyStringException("The name of this tag is null.");
        }
        boolean boo = false;
        Iterator<Tag> itr = tags.iterator();
        while (itr.hasNext()) {
            if (itr.next().getName().toLowerCase().equals(tagName.toLowerCase())) {
                boo = true;
            }
        }
        if (boo == false) {
            tags.add(new Tag(tagName));
        }
    }

    // REQUIRES: name is non-empty
    // MODIFIES: this
    // EFFECTS: removes the tag with name tagName from this task
    public void removeTag(String tagName) {
        try {
            if (tagName.equals("")) {
                throw new EmptyStringException("The name of this tag is an empty-tring or null.");
            }
        } catch (NullPointerException n) {
            throw new EmptyStringException("The name of this tag is null.");
        }
        Iterator<Tag> itr = tags.iterator();
        while (itr.hasNext()) {
            if (itr.next().getName().toLowerCase().equals(tagName.toLowerCase())) {
                itr.remove();
            }
        }
        removeTagFromTagSet(tagName);
    }

    private void removeTagFromTagSet(String tagName) {
        Iterator<Tag> itr = tagSet.iterator();
        while (itr.hasNext()) {
            if (itr.next().getName().toLowerCase().equals(tagName.toLowerCase())) {
                itr.remove();
            }
        }
    }

    // EFFECTS: returns an unmodifiable set of tags
    public Set<Tag> getTags() {
        for (Tag tag : tags) {
            tagSet.add(tag);
        }
        return tagSet;
    }

    // EFFECTS: returns the priority of this task
    public Priority getPriority() {
        return priority;
    }

    // REQUIRES: priority != null
    // MODIFIES: this
    // EFFECTS: sets the priority of this task
    public void setPriority(Priority priority) {
        try {
            priority.isImportant();
            this.priority = priority;
        } catch (NullPointerException n) {
            throw new NullArgumentException("Priority is null.");
        }
    }

    // EFFECTS: returns the status of this task
    public Status getStatus() {
        return status;
    }

    // REQUIRES: status != null
    // MODIFIES: this
    // EFFECTS: sets the status of this task
    public void setStatus(Status status) {
        try {
            status.getDescription();
            this.status = status;
        } catch (NullPointerException n) {
            throw new NullArgumentException("Status is null.");
        }
    }

    // EFFECTS: returns the description of this task
    public String getDescription() {
        return description;
    }

    // REQUIRES: description is non-empty
    // MODIFIES: this
    // EFFECTS:  sets the description of this task
    //     parses the description to extract meta-data (i.e., tags, status, priority and deadline).
    public void setDescription(String description) {
        try {
            if (description.equals("")) {
                throw new EmptyStringException("This description is an empty-string.");
            }
        } catch (NullPointerException n) {
            throw new EmptyStringException("This description is null.");
        }
        this.description = description;
    }

    // EFFECTS: returns the due date of this task
    public DueDate getDueDate() {
        return dueDate;
    }

    // MODIFIES: this
    // EFFECTS: sets the due date of this task
    public void setDueDate(DueDate dueDate) {
        this.dueDate = dueDate;
    }

    // EFFECTS: returns true if task contains a tag with tagName,
    //     returns false otherwise
    public boolean containsTag(String tagName) {
        Iterator<Tag> itr = tags.iterator();
        while (itr.hasNext()) {
            if (itr.next().getName().toLowerCase().equals(tagName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: returns a string representation of this task in the following format
    //    {
    //        Description: Read collaboration policy of the term project
    //        Due date: Sat Feb 2 2019 11:59
    //        Status: IN PROGRESS
    //        Priority: IMPORTANT & URGENT
    //        Tags: #cpsc210, #project
    //    }
    @Override
    public String toString() {
        String newline = System.getProperty("line.separator");
        String strDueDate = "Due date: " + (dueDate != null ? dueDate.toString() : "");
        String strStatus = "Status: " + status.toString();
        String strPriority = "Priority: " + priority.toString();
        String strTags = "";
        String strTags1 = "Tags: ";
        String strTags2 = "  ";
        Iterator<Tag> itr = tags.iterator();
        while (itr.hasNext()) {
            strTags2 += "#" + itr.next() + ", ";
        }
        strTags2 = strTags2.substring(0, strTags2.length() - 2);
        strTags = strTags1 + strTags2.trim();
        return "Description: " + description + newline + strDueDate + newline
                + strStatus + newline + strPriority + newline + strTags;
    }

    public static void main(String[] args) {
        Task task = new Task("Buy milk! ## next Tuesday; important");
        System.out.println(task);
    }
}
