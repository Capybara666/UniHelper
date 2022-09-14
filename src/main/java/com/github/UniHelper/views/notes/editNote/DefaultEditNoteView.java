package com.github.UniHelper.views.notes.editNote;

import com.github.UniHelper.model.categories.Category;
import com.github.UniHelper.presenters.commands.Command;
import com.github.UniHelper.views.notes.note.editedNote.EditedNoteView;
import com.github.UniHelper.views.utils.categorySelectorPanel.CategorySelectorPanel;
import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;

public class DefaultEditNoteView implements EditNoteView {

    private final MainPanel mainPanel;
    private final CategorySelectorPanel categorySelectorPanel;
    private final ArrayList<Command> onEditFinishedCommands;
    private final ArrayList<Command> onCategoryChangedCommands;
    @Getter
    private EditedNoteView editedNoteView;

    public DefaultEditNoteView() {
        mainPanel = new MainPanel();
        categorySelectorPanel = new CategorySelectorPanel();
        onEditFinishedCommands = new ArrayList<>();
        onCategoryChangedCommands = new ArrayList<>();
        assembleView();
    }

    @Override
    public void setNoteView(EditedNoteView noteView) {
        editedNoteView = noteView;
        BorderLayout layout = (BorderLayout) mainPanel.getLayout();
        Component currentNote = layout.getLayoutComponent(BorderLayout.CENTER);
        if (currentNote != null) {
            mainPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        }
        mainPanel.add(noteView.getContainer(), BorderLayout.CENTER);
        noteView.addOnNoteSavedCommand(this::executeOnEditFinishedCommands);
        noteView.addOnNoteDeletedCommand(this::executeOnEditFinishedCommands);
    }

    @Override
    public void addOnEditFinishedCommand(Command command) {
        onEditFinishedCommands.add(command);
    }

    @Override
    public void addOnCategoryChangedCommand(Command command) {
        onCategoryChangedCommands.add(command);
    }

    @Override
    public void setCategorySelectorPanelActiveCategory(Category category) {
        categorySelectorPanel.setActiveCategory(category);
    }

    @Override
    public String getFeatureName() {
        return "edit note";
    }

    @Override
    public Container getContainer() {
        return mainPanel;
    }

    @Override
    public Category getActiveCategory() {
        return categorySelectorPanel.getActiveCategory();
    }

    @Override
    public void setCategories(ArrayList<Category> categories) {
        categorySelectorPanel.setCategories(categories);
    }

    private void assembleView() {
        categorySelectorPanel.setAllCategoriesButtonVisible(false);
        mainPanel.add(categorySelectorPanel, BorderLayout.NORTH);
        categorySelectorPanel.addOnCategoryChangedCommand(this::executeOnCategoryChangedCommands);
    }

    private void executeOnEditFinishedCommands() {
        for (Command c : onEditFinishedCommands) {
            c.execute();
        }
    }

    private void executeOnCategoryChangedCommands() {
        for (Command c : onCategoryChangedCommands) {
            c.execute();
        }
    }
}
