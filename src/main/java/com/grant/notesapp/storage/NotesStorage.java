package com.grant.notesapp.storage;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grant.notesapp.model.Note;

public class NotesStorage {

    // Saving file name as FILE for future use
    private static final String FILE = "notes.json";
    // GSON object to convert Java Object to JSON
    private static final Gson gson = new Gson();

    // Load notes from JSON 
    public static List<Note> loadNotes() {

        // Try for auto closing file and catching exceptions
        try (FileReader reader = new FileReader(FILE)) {
            // Puts JSON data into a list
            List<Note> notes = gson.fromJson(reader, new TypeToken<List<Note>>(){}.getType());

            if (notes == null) 
            {
                return new ArrayList<>();
            }

            return notes;

        // if theres any issues creating the list create a new one and catch the exception
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void saveNotes(List<Note> notes) {
        try (FileWriter writer = new FileWriter(FILE)) {
            gson.toJson(notes, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
