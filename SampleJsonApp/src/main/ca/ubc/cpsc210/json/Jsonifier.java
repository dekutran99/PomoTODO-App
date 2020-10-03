package ca.ubc.cpsc210.json;

import ca.ubc.cpsc210.model.Book;
import ca.ubc.cpsc210.model.Library;
import org.json.JSONArray;
import org.json.JSONObject;

public class Jsonifier {

    // EFFECTS: returns JSONObject representing book
    public static JSONObject bookToJson(Book b) {
        JSONObject bookJson = new JSONObject();

        bookJson.put("title", b.getTitle());
        bookJson.put("num-pages", b.getNumPages());
        bookJson.put("is-fiction", b.isFiction());

        return bookJson;
    }

    // EFFECTS: returns JSONArray representing list of books found in library
    public static JSONArray libraryToJson(Library library) {
        JSONArray booksArray = new JSONArray();

        for (Book b : library.getBooks()) {
            booksArray.put(bookToJson(b));
        }

        return booksArray;
    }
}
