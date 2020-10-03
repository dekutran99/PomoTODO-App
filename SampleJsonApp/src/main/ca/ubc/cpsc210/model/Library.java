package ca.ubc.cpsc210.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a library having a collection of books
public class Library {
    private List<Book> books;

    // EFFECTS: constructs a library having no books
    public Library() {
        books = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a book to this library
    public void addBook(Book b) {
        books.add(b);
    }

    // EFFECTS: returns unmodifiable list of books in library
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    // EFFECTS: returns number of books in library
    public int getNumBooks() {
        return books.size();
    }
}
