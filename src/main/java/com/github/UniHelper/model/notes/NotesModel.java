package com.github.UniHelper.model.notes;

import java.util.ArrayList;
import java.util.UUID;

public interface NotesModel {

    void addNote(Note note);

    void deleteNote(Note note);

    void updateNoteById(UUID id, Note note);

    ArrayList<Note> getAllNotes();

    void setNotes(ArrayList<Note> notes);
}
