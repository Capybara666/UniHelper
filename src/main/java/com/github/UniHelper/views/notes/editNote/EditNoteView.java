package com.github.UniHelper.views.notes.editNote;

import com.github.UniHelper.model.categories.Category;
import com.github.UniHelper.presenters.commands.Command;
import com.github.UniHelper.views.FeatureView;
import com.github.UniHelper.views.notes.note.editedNote.EditedNoteView;

import java.util.ArrayList;

public interface EditNoteView extends FeatureView {

    void setNoteView(EditedNoteView noteView);

    void addOnEditFinishedCommand(Command command);

    void addOnCategoryChangedCommand(Command command);

    void setCategorySelectorPanelActiveCategory(Category category);

    EditedNoteView getEditedNoteView();

    Category getActiveCategory();

    void setCategories(ArrayList<Category> categories);
}
