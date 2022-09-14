package com.github.UniHelper.views.notes.note.previewNote;

import com.github.UniHelper.presenters.commands.Command;
import com.github.UniHelper.views.notes.note.MainPanel;
import com.github.UniHelper.views.notes.note.TextPanel;
import com.github.UniHelper.views.notes.note.TitlePanel;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class DefaultPreviewNoteView implements PreviewNoteView {

    private final MainPanel mainPanel;
    private final TitlePanel titlePanel;
    private final TextPanel textPanel;
    private final OptionsPanel optionsPanel;
    private final ArrayList<Command> onNoteDeletedCommands;
    private final ArrayList<Command> onEditRequestCommands;
    @Getter
    @Setter
    private UUID id;

    public DefaultPreviewNoteView() {
        mainPanel = new MainPanel();
        titlePanel = new TitlePanel();
        textPanel = new TextPanel();
        optionsPanel = new OptionsPanel();
        onNoteDeletedCommands = new ArrayList<>();
        onEditRequestCommands = new ArrayList<>();
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
    public void addOnEditRequestCommand(Command command) {
        onEditRequestCommands.add(command);
    }

    @Override
    public Color getColor() {
        return textPanel.getBackground();
    }

    @Override
    public void setColor(Color color) {
        titlePanel.setTitleBackground(color.darker().darker());
        textPanel.setTextBackground(color);
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

    private void assembleView() {
        titlePanel.setEditable(false);
        textPanel.setEditable(false);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(optionsPanel, BorderLayout.SOUTH);
        optionsPanel.addDeleteButtonCommand(this::executeOnNoteDeletedCommands);
        optionsPanel.addEditButtonCommand(this::executeOnEditRequestCommands);
    }

    private void executeOnNoteDeletedCommands() {
        for (Command c : onNoteDeletedCommands) {
            c.execute();
        }
    }

    private void executeOnEditRequestCommands() {
        for (Command c : onEditRequestCommands) {
            c.execute();
        }
    }
}
