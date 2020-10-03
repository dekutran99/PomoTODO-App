package cs.ubc.cpsc210.ui;

import ca.ubc.cpsc210.json.Jsonifier;
import ca.ubc.cpsc210.json.LibraryParser;
import ca.ubc.cpsc210.model.Book;
import ca.ubc.cpsc210.model.Library;
import org.json.JSONArray;

public class Main {

    public static void main(String[] args) {
        // construct a library and add 3 books...
        Library library = initLibrary();

        // get representation of library as JSON array...
        JSONArray libJson = Jsonifier.libraryToJson(library);

        // print JSON representation to console...
        System.out.println("JSON representation of library:\n");
        System.out.println(libJson.toString(4));
        System.out.println("\n");

        // now go in the other direction...
        // parse libJson to reconstruct library
        Library reconstructedLibrary = LibraryParser.parse(libJson.toString());

        System.out.println("Here are the books in the library constructed from the JSON data...\n");
        for (Book b : reconstructedLibrary.getBooks()) {
            System.out.println(b.toString());
        }
    }

    // EFFECTS: initializes a library with a collection of books and returns it
    private static Library initLibrary() {
        Library library = new Library();

        Book b1 = new Book("Gone with the wind", 456, true);
        Book b2 = new Book("War and Peace", 532, true);
        Book b3 = new Book("History of the Universe", 345, false);

        library.addBook(b1);
        library.addBook(b2);
        library.addBook(b3);

        return library;
    }
}
