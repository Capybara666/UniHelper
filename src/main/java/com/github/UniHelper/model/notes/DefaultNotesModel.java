package com.github.UniHelper.model.notes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DefaultNotesModel implements NotesModel {
    private final String saveFileName;
    ArrayList<Note> notes;

    public DefaultNotesModel() {
        notes = new ArrayList<>();
        saveFileName = "saved_notes.txt";
        load();
    }

    @Override
    public void addNote(Note note) {
        Note noteCopy = new Note(note);
        notes.add(noteCopy);
        save();
    }

    @Override
    public void deleteNote(Note note) {
        notes.remove(note);
        save();
    }

    @Override
    public void setNotes(ArrayList<Note> notes) {
        this.notes = getDeepNotesCopy(notes);
        save();
    }

    @Override
    public ArrayList<Note> getAllNotes() {
        return getDeepNotesCopy(notes);
    }

    @Override
    public void updateNote(Note note) {
        Note noteToUpdate = notes.stream()
                .filter(n -> n.getId().equals(note.getId()))
                .findFirst()
                .orElse(null);
        if (noteToUpdate == null)
            addNote(note);
        else {
            noteToUpdate.setTitle(note.getTitle());
            noteToUpdate.setText(note.getText());
        }
        save();
    }

    private void load() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            notes = mapper.readValue(new File(saveFileName), new TypeReference<>() {});
        } catch (IOException e) {
            createNewSaveFile();
        }
    }

    private void save() {
        try (PrintWriter out = new PrintWriter(saveFileName, StandardCharsets.UTF_8)) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
            String json = writer.writeValueAsString(notes);
            out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createNewSaveFile() {
        try {
            File f = new File(saveFileName);
            f.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Note> getDeepNotesCopy(ArrayList<Note> originalNotes) {
        ArrayList<Note> deepCopy;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            deepCopy = objectMapper
                    .readValue(objectMapper.writeValueAsString(originalNotes), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return deepCopy;
    }
}