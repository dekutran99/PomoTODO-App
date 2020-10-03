package ca.ubc.cpsc210.json;

import ca.ubc.cpsc210.model.Book;
import ca.ubc.cpsc210.model.Library;
import org.json.JSONArray;
import org.json.JSONObject;

public class LibraryParser {

    // EFFECTS: parses JSONArray represented by input as a library and returns it
    public static Library parse(String input) {
        Library library = new Library();
        JSONArray booksArray = new JSONArray(input);

        for (Object object : booksArray) {
            JSONObject bookJson = (JSONObject) object;
            String title = bookJson.getString("title");
            int numPages = bookJson.getInt("num-pages");
            boolean isFiction = bookJson.getBoolean("is-fiction");
            library.addBook(new Book(title, numPages, isFiction));
        }

        return library;
    }
}
