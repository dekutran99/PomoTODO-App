package ca.ubc.cpsc210.model;

// Represents a book with a title, number of pages and category (fiction or not)
public class Book {
    private String title;
    private int numPages;
    private boolean isFiction;

    // EFFECTS: constructs a book with given title, number of pages and category (isFiction or not)
    public Book(String title, int numPages, boolean isFiction) {
        this.title = title;
        this.numPages = numPages;
        this.isFiction = isFiction;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public int getNumPages() {
        return numPages;
    }

    public boolean isFiction() {
        return isFiction;
    }

    @Override
    public String toString() {
        return title + "\n" + "Pages: " + numPages + "\n" + "Is " + (isFiction ? "" : "not ") + "fiction\n";
    }

}
