package com.grant.notesapp;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.grant.notesapp.model.Note;
import com.grant.notesapp.storage.NotesStorage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


public class ViewNotesController {

    @FXML
    private ListView<Note> notesList;

    // Track which note is expanded
    private Note expandedNote = null;

    @FXML
    public void initialize() {
        List<Note> notes = NotesStorage.loadNotes();
        notesList.getItems().addAll(notes);

        notesList.setCellFactory(list -> new ListCell<Note>() {
            @Override
            protected void updateItem(Note note, boolean empty) {
                super.updateItem(note, empty);

                if (empty || note == null) {
                    setText(null);
                    return;
                }

                // Expanded view
                if (note.equals(expandedNote)) {
                    setText(note.title + "\n" + note.description);
                } 
                // Collapsed view
                else {
                    setText(note.title);
                }

                // Toggle expand/collapse on click
                setOnMouseClicked(event -> {
                    if (expandedNote == note) {
                        expandedNote = null; // collapse
                    } else {
                        expandedNote = note; // expand
                    }
                    notesList.refresh(); // redraw cells
                });
            }
        });
    }

    @FXML
    public void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) notesList.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    public void deleteSelectedNote() {
        Note selected = notesList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("No note selected.");
            return;
        }

        // Confirmation popup
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Note");
        alert.setHeaderText("Are you sure you want to delete this note?");
        alert.setContentText("Title: " + selected.title);

        // Show popup and wait for user choice
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Remove from list
            notesList.getItems().remove(selected);

            // Save updated list to JSON
            NotesStorage.saveNotes(notesList.getItems());

            // Collapse expanded note if needed
            if (expandedNote == selected) {
                expandedNote = null;
            }

            // Refresh UI
            notesList.refresh();

            System.out.println("Deleted: " + selected.title);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }


}
