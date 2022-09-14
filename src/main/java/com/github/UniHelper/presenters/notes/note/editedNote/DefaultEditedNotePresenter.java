package com.github.UniHelper.presenters.notes.note.editedNote;

import com.github.UniHelper.model.categories.Category;
import com.github.UniHelper.model.categories.DefaultCategoriesModel;
import com.github.UniHelper.model.notes.DefaultNotesModel;
import com.github.UniHelper.model.notes.Note;
import com.github.UniHelper.model.notes.NotesModel;
import com.github.UniHelper.views.notes.note.editedNote.EditedNoteView;
import lombok.Getter;

import java.awt.*;

public class DefaultEditedNotePresenter implements EditedNotePresenter {

    private final EditedNoteView view;
    private final NotesModel model;
    @Getter
    private final Note note;

    public DefaultEditedNotePresenter(EditedNoteView view, Note note) {
        this.view = view;
        this.model = DefaultNotesModel.getInstance();
        this.note = note;
        view.setNoteText(note.getText());
        view.setNoteTitle(note.getTitle());
        view.setId(note.getId());
        view.setColor(getNoteColor());
        setViewCommands();
    }

    private Color getNoteColor() {
        return DefaultCategoriesModel.getInstance().getAllCategories()
                .stream()
                .filter(c -> c.getName().equals(note.getCategory()))
                .map(Category::getColor)
                .findFirst()
                .orElse(null);
    }

    private void setViewCommands() {
        view.addOnNoteDeletedCommand(this::deleteNote);
        view.addOnNoteSavedCommand(this::updateNote);
    }

    private void deleteNote() {
        model.deleteNote(note);
    }

    private void updateNote() {
        Note oldNote = new Note(note);
        note.setTitle(view.getNoteTitle());
        note.setText(view.getNoteText());
        note.setCategory(getCategory(view.getColor()).getName());
        model.updateNoteById(view.getId(), note);
    }

    private Category getCategory(Color color) {
        return DefaultCategoriesModel.getInstance().getAllCategories()
                .stream()
                .filter(c -> c.getColor().equals(color))
                .findFirst()
                .orElse(null);
    }
}
