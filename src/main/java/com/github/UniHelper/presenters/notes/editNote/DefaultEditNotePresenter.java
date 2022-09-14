package com.github.UniHelper.presenters.notes.editNote;

import com.github.UniHelper.model.categories.Category;
import com.github.UniHelper.model.categories.DefaultCategoriesModel;
import com.github.UniHelper.views.notes.editNote.EditNoteView;
import com.github.UniHelper.views.notes.note.editedNote.EditedNoteView;

import java.util.ArrayList;

public class DefaultEditNotePresenter implements EditNotePresenter {

    private final EditNoteView view;
    private final ArrayList<Category> categories;

    public DefaultEditNotePresenter(EditNoteView view) {
        this.view = view;
        categories = DefaultCategoriesModel.getInstance().getAllCategories();
        view.setCategories(categories);
        view.addOnCategoryChangedCommand(this::updateNoteCategory);
    }

    private void updateNoteCategory() {
        EditedNoteView editedNoteView = view.getEditedNoteView();
        editedNoteView.setColor(view.getActiveCategory().getColor());
    }
}
