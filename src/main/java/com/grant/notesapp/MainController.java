package com.grant.notesapp;

import java.util.List;

import com.grant.notesapp.model.Note;
import com.grant.notesapp.storage.NotesStorage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea noteArea;

    private List<Note> notes = NotesStorage.loadNotes();

    public void saveNote() {
        String title = titleField.getText().trim();
        String description = noteArea.getText().trim();

        System.out.println("Saving: " + title + " | " + description);

        notes.add(new Note(title, description));
        NotesStorage.saveNotes(notes);
    }

    @FXML
    public void goToViewNotes() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view-notes.fxml"));
        Scene scene = new Scene(loader.load());

        // Get the current window
        Stage stage = (Stage) titleField.getScene().getWindow();

        // Switch the scene
        stage.setScene(scene);
    }
}
