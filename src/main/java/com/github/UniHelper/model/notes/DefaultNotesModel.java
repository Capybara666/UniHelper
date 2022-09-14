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
import java.util.UUID;

public class DefaultNotesModel implements NotesModel {

    private static volatile DefaultNotesModel instance;

    private final String saveFileName;
    private ArrayList<Note> notes;

    public static DefaultNotesModel getInstance() {
        DefaultNotesModel result = instance;
        if (result != null) {
            return result;
        }
        synchronized (DefaultNotesModel.class) {
            if (instance == null) {
                instance = new DefaultNotesModel();
            }
            return instance;
        }
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
    public void updateNoteById(UUID id, Note note) {
        Note noteToUpdate = notes.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (noteToUpdate != null) {
            noteToUpdate.setTitle(note.getTitle());
            noteToUpdate.setText(note.getText());
            noteToUpdate.setCategory(note.getCategory());
            save();
        }
    }

    private DefaultNotesModel() {
        saveFileName = "saved_notes.txt";
        notes = new ArrayList<>();
        load();
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

    private ArrayList<Note> getDeepNotesCopy(ArrayList<Note> originalNotes) {
        ArrayList<Note> deepCopy;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            deepCopy = objectMapper
                    .readValue(objectMapper.writeValueAsString(originalNotes), new TypeReference<>() {
                    });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return deepCopy;
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
}
