package com.github.UniHelper.views.notes.note.editedNote;

import com.github.UniHelper.presenters.commands.Command;
import com.github.UniHelper.views.notes.note.MainPanel;
import com.github.UniHelper.views.notes.note.TextPanel;
import com.github.UniHelper.views.notes.note.TitlePanel;
import com.github.UniHelper.views.notes.note.previewNote.PreviewNoteView;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class DefaultEditedNoteView implements EditedNoteView {

    private final MainPanel mainPanel;
    private final TitlePanel titlePanel;
    private final TextPanel textPanel;
    private final OptionsPanel optionsPanel;
    private final ArrayList<Command> onNoteDeletedCommands;
    private final ArrayList<Command> onNoteModifiedCommands;
    private final ArrayList<Command> onNoteSavedCommands;
    @Getter
    @Setter
    private UUID id;

    public DefaultEditedNoteView() {
        mainPanel = new MainPanel();
        titlePanel = new TitlePanel();
        textPanel = new TextPanel();
        optionsPanel = new OptionsPanel();
        onNoteDeletedCommands = new ArrayList<>();
        onNoteModifiedCommands = new ArrayList<>();
        onNoteSavedCommands = new ArrayList<>();
        assembleView();
    }

    public DefaultEditedNoteView(PreviewNoteView noteView) {
        mainPanel = new MainPanel();
        titlePanel = new TitlePanel();
        textPanel = new TextPanel();
        optionsPanel = new OptionsPanel();
        onNoteDeletedCommands = new ArrayList<>();
        onNoteModifiedCommands = new ArrayList<>();
        onNoteSavedCommands = new ArrayList<>();
        setNoteTitle(noteView.getNoteTitle());
        setNoteText(noteView.getNoteText());
        textPanel.setTextBackground(noteView.getColor());
        assembleView();
    }

    @Override
    public Container getContainer() {
        return mainPanel;
    }

    @Override
    public void addOnNoteDeletedCommand(Command command) {
        onNoteDeletedCommands.add(command);
    }

    @Override
    public void addOnNoteModifiedCommand(Command command) {
        onNoteModifiedCommands.add(command);
    }

    @Override
    public void addOnNoteSavedCommand(Command command) {
        onNoteSavedCommands.add(command);
    }

    @Override
    public String getNoteTitle() {
        return titlePanel.getTitle();
    }

    @Override
    public void setNoteTitle(String title) {
        titlePanel.setTitle(title);
    }

    @Override
    public String getNoteText() {
        return textPanel.getText();
    }

    @Override
    public void setNoteText(String text) {
        textPanel.setText(text);
    }

    @Override
    public Color getColor() {
        return textPanel.getTextBackground();
    }

    @Override
    public void setColor(Color color) {
        titlePanel.setTitleBackground(color.darker().darker());
        textPanel.setTextBackground(color);
    }

    private void assembleView() {
        titlePanel.setEditable(true);
        textPanel.setEditable(true);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(optionsPanel, BorderLayout.SOUTH);
        optionsPanel.addDeleteButtonCommand(this::executeOnNoteDeletedCommands);
        optionsPanel.addSaveButtonCommand(this::executeOnNoteSavedCommands);
    }

    private void executeOnNoteDeletedCommands() {
        for (Command c : onNoteDeletedCommands) {
            c.execute();
        }
    }

    private void executeOnNoteModifiedCommands() {
        for (Command c : onNoteModifiedCommands) {
            c.execute();
        }
    }

    private void executeOnNoteSavedCommands() {
        for (Command c : onNoteSavedCommands) {
            c.execute();
        }
    }
}
