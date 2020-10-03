package model;

import model.exceptions.EmptyStringException;

// Represents a tag having a name
public class Tag {

    private String name;

    // REQUIRES: name is non-empty
    // MODIFIES: this
    // EFFECTS: creates a Tag with the given name
    public Tag(String name) {
        try {
            if (name.equals("")) {
                throw new EmptyStringException("Name of this tag is an empty-string.");
            }
            this.name = name;
        } catch (NullPointerException n) {
            throw new EmptyStringException("Name of this tag is null.");
        }
    }
    
    // EFFECTS: returns the name of this tag
    public String getName() {
        return name;
    }
    
    // EFFECTS: returns the tag name preceded by #
    @Override
    public String toString() {
        return name;
    }
}
