package emmek.entities;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Library {
    private List<LibraryItem> library;

    public Library() {
        this.library = new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.library.stream().map(LibraryItem::toString).collect(Collectors.joining());
    }

    public void save() {
        File file = new File("src/library.json");

        Gson gson = new Gson();
        String jsonStr = gson.toJson(this.library);

        try {
            FileUtils.writeStringToFile(file, jsonStr + System.lineSeparator(), StandardCharsets.UTF_8, false);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void load() {
        File file = new File("src/library.json");
        String jsonStr = "";
        List<LibraryItem> list = new ArrayList<>();
        try {
            jsonStr = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        Gson gson = new Gson();
        List<JsonObject> libraryList = gson.fromJson(jsonStr, new TypeToken<List<JsonObject>>() {
        }.getType());

        libraryList.forEach(elm -> {
            if (elm.asMap().get("author") != null && elm.asMap().get("genre") != null) {
                list.add(gson.fromJson(elm, Book.class));
            } else list.add(gson.fromJson(elm, Magazine.class));
        });

        this.library = list;
    }

    public void add(LibraryItem item) {
        this.library.add(item);
    }

    public void printIndex() {
        for (int i = 0; i < this.library.size(); i++) {
            System.out.print((i + 1) + " - " + this.library.get(i));
        }
    }

    public void rem(int index) {
        if (index < this.library.size() && index >= 0) this.library.remove(index);
    }

    public void rem(String isbn) {
        this.library = this.library.stream().filter(elm -> (!elm.getIsbn().equals(isbn))).collect(toList());
    }

    public List<LibraryItem> findIsbn(String filter) {
        return this.library.stream().filter(elm -> (elm.getIsbn().contains(filter))).toList();
    }

    public List<LibraryItem> findYear(String filter) {
        return this.library.stream().filter(elm -> (elm.getYear().contains(filter))).toList();
    }

    public List<LibraryItem> findAuthor(String filter) {
        return this.library.stream().filter(elm -> (elm instanceof Book)).filter(book -> ((Book) book).getAuthor().toLowerCase().contains(filter)).toList();
    }

}
