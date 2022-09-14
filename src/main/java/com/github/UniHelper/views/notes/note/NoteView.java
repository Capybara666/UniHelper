package com.github.UniHelper.views.notes.note;

import com.github.UniHelper.presenters.commands.Command;

import java.awt.*;
import java.util.UUID;

public interface NoteView {

    Container getContainer();

    void addOnNoteDeletedCommand(Command command);

    String getNoteTitle();

    void setNoteTitle(String title);

    String getNoteText();

    void setNoteText(String text);

    Color getColor();

    void setColor(Color color);

    UUID getId();

    void setId(UUID id);

}
