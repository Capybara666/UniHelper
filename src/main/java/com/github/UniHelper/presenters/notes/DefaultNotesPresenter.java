package com.github.UniHelper.presenters.notes;

import com.github.UniHelper.model.categories.Category;
import com.github.UniHelper.model.categories.DefaultCategoriesModel;
import com.github.UniHelper.model.notes.DefaultNotesModel;
import com.github.UniHelper.model.notes.Note;
import com.github.UniHelper.presenters.notes.editNote.DefaultEditNotePresenter;
import com.github.UniHelper.presenters.notes.editNote.EditNotePresenter;
import com.github.UniHelper.presenters.notes.note.editedNote.DefaultEditedNotePresenter;
import com.github.UniHelper.presenters.notes.note.editedNote.EditedNotePresenter;
import com.github.UniHelper.presenters.notes.showNotes.DefaultShowNotesPresenter;
import com.github.UniHelper.presenters.notes.showNotes.ShowNotesPresenter;
import com.github.UniHelper.views.notes.NotesView;
import com.github.UniHelper.views.notes.editNote.DefaultEditNoteView;
import com.github.UniHelper.views.notes.editNote.EditNoteView;
import com.github.UniHelper.views.notes.note.editedNote.DefaultEditedNoteView;
import com.github.UniHelper.views.notes.note.editedNote.EditedNoteView;
import com.github.UniHelper.views.notes.showNotes.DefaultShowNotesView;
import com.github.UniHelper.views.notes.showNotes.ShowNotesView;

import java.util.UUID;

public class DefaultNotesPresenter implements NotesPresenter {

    private final NotesView view;
    private final EditNoteView editNoteView;

    public DefaultNotesPresenter(NotesView notesView) {
        view = notesView;
        ShowNotesView showNotesView = new DefaultShowNotesView(view.getAccessibleWidth());
        ShowNotesPresenter showNotesPresenter = new DefaultShowNotesPresenter(showNotesView);
        view.addOnNoteEditRequestedCommand(this::showEditView);
        view.addOnNoteEditFinishedCommand(this::showPreviewsView);
        view.setShowNotesView(showNotesView);

        editNoteView = new DefaultEditNoteView();
        EditNotePresenter editNotePresenter = new DefaultEditNotePresenter(editNoteView);
        view.setEditNoteView(editNoteView);
    }

    private void showPreviewsView() {
        view.showShowNotesView();
    }

    private void showEditView() {
        EditedNoteView editedNoteView = new DefaultEditedNoteView(view.getNoteToEdit());
        Note editedNote = getEditedNote();
        EditedNotePresenter editedNotePresenter = new DefaultEditedNotePresenter(editedNoteView, editedNote);
        editNoteView.setNoteView(editedNoteView);
        editNoteView.setCategorySelectorPanelActiveCategory(getNoteCategory(editedNote));
        view.showEditNoteView();
    }

    private Note getEditedNote() {
        UUID editedNoteId = view.getNoteToEdit().getId();
        return DefaultNotesModel.getInstance().getAllNotes()
                .stream()
                .filter(n -> n.getId().equals(editedNoteId))
                .findFirst()
                .orElse(null);
    }

    private Category getNoteCategory(Note note) {
        return DefaultCategoriesModel.getInstance().getAllCategories()
                .stream()
                .filter(c -> c.getName().equals(note.getCategory()))
                .findFirst()
                .orElse(null);
    }
}
