package parsers;

import model.DueDate;
import model.Priority;
import model.Status;
import model.Task;
import model.exceptions.EmptyStringException;
import parsers.exceptions.ParsingException;

public class TagParser extends Parser {

    private int parsedStatus = 0;
    private String s1;
    private String s2;

    // MODIFIES: this, task
    // EFFECTS: parses the input to extract properties such as priority or deadline
    //    and updates task with those extracted properties
    //  throws ParsingException if description does not contain the tag deliminator
    @Override
    public void parse(String input, Task task) throws ParsingException {
        if (!input.contains("##")) {
            super.description = input;
            task.setDescription(this.getDescription());
            throw new ParsingException("Description of this tag does not contain the tag deliminator.");
        } else {
            String[] strArr = input.split("##");
            this.description = strArr[0];
            task.setDescription(this.getDescription());
            String[] tagArr = strArr[1].split(";");
            for (String tag: tagArr) {
                parseTag(tag.trim(), task);
            }
        }
    }

    protected void parseTag(String tag, Task task) {
        if (tag.toLowerCase().equals("important") || tag.toLowerCase().equals("urgent")) {
            parsePriority(tag, task);
        } else if (tag.toLowerCase().equals("today") || tag.toLowerCase().equals("tomorrow")) {
            parseDate(tag, task);
        } else if (tag.toLowerCase().equals("to do") || tag.toLowerCase().equals("up next")
                || tag.toLowerCase().equals("in progress") || tag.toLowerCase().equals("done")) {
            parseStatus(tag, task);
        } else if (!tag.equals("")) {
            task.addTag(tag);
        }
    }

    protected void parsePriority(String priority, Task task) {
        if (priority.toLowerCase().equals("important")) {
            if (task.getPriority().isUrgent()) {
                task.setPriority(new Priority(1));
            } else {
                task.setPriority(new Priority(2));
            }
        }  else if (priority.toLowerCase().equals("urgent")) {
            if (task.getPriority().isImportant()) {
                task.setPriority(new Priority(1));
            } else {
                task.setPriority(new Priority(3));
            }
        }
    }

    protected void parseDate(String date, Task task) {
        DueDate dueDate = new DueDate();
        if (date.toLowerCase().equals("today")) {
            if (task.getDueDate() == null) {
                task.setDueDate(dueDate);
            } else if (!task.getDueDate().isDueToday()) {
                task.addTag(date);
            }
        } else if (date.toLowerCase().equals("tomorrow")) {
            if (task.getDueDate() == null) {
                dueDate.postponeOneDay();
                task.setDueDate(dueDate);
            } else if (!task.getDueDate().isDueTomorrow()) {
                task.addTag(date);
            }
        }
    }

    protected void parseStatus(String status, Task task) {
        if (status.toLowerCase().equals("to do")) {
            parseToDo(status,task);
        } else if (status.toLowerCase().equals("up next")) {
            parseUpNext(status, task);
        } else if (status.toLowerCase().equals("in progress")) {
            parseInProgress(status, task);
        } else if (status.toLowerCase().equals("done")) {
            parseDone(status, task);
        }
    }

    protected void parseToDo(String status, Task task) {
        if (!task.getStatus().getDescription().equals("TODO") && parsedStatus >= 1) {
            task.addTag(status);
        } else {
            task.setStatus(Status.TODO);
            parsedStatus++;
        }
    }

    protected void parseUpNext(String status, Task task) {
        if (!task.getStatus().getDescription().equals("UP NEXT") && parsedStatus >= 1) {
            task.addTag(status);
        } else {
            task.setStatus(Status.UP_NEXT);
            parsedStatus++;
        }
    }

    protected void parseInProgress(String status, Task task) {
        if (!task.getStatus().getDescription().equals("IN PROGRESS") && parsedStatus >= 1) {
            task.addTag(status);
        } else {
            task.setStatus(Status.IN_PROGRESS);
            parsedStatus++;
        }
    }

    protected void parseDone(String status, Task task) {
        if (!task.getStatus().getDescription().equals("DONE") && parsedStatus >= 1) {
            task.addTag(status);
        } else {
            task.setStatus(Status.DONE);
            parsedStatus++;
        }
    }


}
