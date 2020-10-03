package model;

import model.exceptions.InvalidPriorityLevelException;

// To model priority of a task according to the Eisenhower Matrix
//     https://en.wikipedia.org/wiki/Time_management#The_Eisenhower_Method
public class Priority {

    private boolean urgent;
    private boolean important;

    // MODIFIES: this
    // EFFECTS: construct a default priority (i.e., not important nor urgent)
    public Priority() {
        urgent = false;
        important = false;
    }
    
    // REQUIRES: 1 <= quadrant <= 4
    // MODIFIES: this
    // EFFECTS: constructs a Priority according to the value of "quadrant"
    //     the parameter "quadrant" refers to the quadrants of the Eisenhower Matrix
    public Priority(int quadrant) {
        if (quadrant == 1) {
            urgent = true;
            important = true;
        } else if (quadrant == 2) {
            urgent = false;
            important = true;
        } else if (quadrant == 3) {
            urgent = true;
            important = false;
        } else if (quadrant == 4) {
            urgent = false;
            important = false;
        } else {
            throw new InvalidPriorityLevelException("Invalid priority level.");
        }
    }
    
    // EFFECTS: returns the importance of Priority
    //     (i.e., whether it is "important" or not)
    public boolean isImportant() {
        return important;
    }
    
    // MODIFIES: this
    // EFFECTS: updates the importance of Priority
    public void setImportant(boolean important) {
        this.important = important;
    }
    
    // EFFECTS: returns the urgency of Priority
    //     (i.e., whether it is "urgent" or not)
    public boolean isUrgent() {
        return urgent;
    }
    
    // MODIFIES: this
    // EFFECTS: updates the urgency of Priority
    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }
    
    // EFFECTS: returns one of the four string representation of Priority
    //    "IMPORTANT & URGENT",  "IMPORTANT", "URGENT", "DEFAULT"
    @Override
    public String toString() {
        if (important == true && urgent == true) {
            return "IMPORTANT & URGENT";
        } else if (important == true) {
            return "IMPORTANT";
        } else if (urgent == true) {
            return  "URGENT";
        } else {
            return "DEFAULT";
        }
    }
}