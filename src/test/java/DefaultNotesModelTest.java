import com.github.UniHelper.model.notes.DefaultNotesModel;
import com.github.UniHelper.model.notes.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DefaultNotesModelTest {

    private DefaultNotesModel notesModel;

    @BeforeEach
    void create_model() {
        notesModel = DefaultNotesModel.getInstance();
    }

    @Test
    void after_addNotes_size_should_not_be_zero() {
        // Given
        Note note = new Note("title", "data", "category");

        // When
        notesModel.addNote(note);

        // Then
        Assertions.assertNotEquals(0, notesModel.getAllNotes().size());
    }

    @Test
    void given_two_equal_notes_both_should_be_present() {
        // Given
        Note note = new Note("", "", "");

        // When
        int sizeBeforeAddingNotes = notesModel.getAllNotes().size();
        notesModel.addNote(note);
        notesModel.addNote(note);
        int sizeAfterAddingNotes = notesModel.getAllNotes().size();

        // Then
        int expectedSize = sizeBeforeAddingNotes + 2;
        Assertions.assertEquals(expectedSize, sizeAfterAddingNotes);
    }

    @Test
    void when_setting_empty_collection_get_all_notes_should_return_empty_collection() {
        // Given
        notesModel.setNotes(new ArrayList<>());

        // When
        ArrayList<Note> returned = notesModel.getAllNotes();

        // Then
        Assertions.assertEquals(0, returned.size());
    }

    @Test
    void only_notes_with_equal_title_and_data_should_be_equal() {
        // Given
        Note n1 = new Note("", "", "");
        Note n2 = new Note("title", "", "");
        Note n3 = new Note("", "title", "");
        Note n4 = new Note("B", "A", "");
        Note n5 = new Note("B", "A", "");

        // When

        // Then
        Assertions.assertEquals(n1, n1);
        Assertions.assertEquals(n4, n5);
        Assertions.assertNotEquals(n1, n2);
        Assertions.assertNotEquals(n1, n3);
        Assertions.assertNotEquals(n2, n3);
        Assertions.assertNotEquals(n1, n4);
    }

    @Test
    void getAllNotes_should_return_all_the_notes() {
        // Given
        ArrayList<Note> notes = new ArrayList<>();
        Note n1 = new Note("title", "data", "category");
        Note n2 = new Note("title", "data", "category");
        Note n3 = new Note("title2", "data2", "category2");
        Note n4 = new Note("title2", "data3", "category3");
        notes.add(n1);
        notes.add(n2);
        notes.add(n3);
        notes.add(n4);

        // When
        notesModel.setNotes(notes);
        notes.add(n1);
        notes.add(n2);
        notes.add(n3);
        notes.add(n4);
        ArrayList<Note> returned = notesModel.getAllNotes();

        // Then
        for (Note n : notes) {
            Assertions.assertTrue(returned.contains(n));
        }
    }

    @Test
    void after_setNotes_should_have_only_given_notes() {
        // Given
        Note noteBefore1 = new Note("title", "data", "category");
        Note noteBefore2 = new Note("title2", "data2", "category2");
        Note noteBefore3 = new Note("title3", "data3", "category3");
        notesModel.addNote(noteBefore1);
        notesModel.addNote(noteBefore2);
        notesModel.addNote(noteBefore3);
        ArrayList<Note> notes = new ArrayList<>();
        Note noteAfter1 = new Note("title4", "data4", "category4");
        Note noteAfter2 = noteBefore2;
        Note noteAfter3 = new Note("title", "data2", "category3");
        notes.add(noteAfter1);
        notes.add(noteAfter2);
        notes.add(noteAfter3);

        // When
        notesModel.setNotes(notes);
        ArrayList<Note> afterUpdate = notesModel.getAllNotes();

        // Then
        boolean hasNoteBefore1 = afterUpdate.contains(noteBefore1);
        boolean hasNoteBefore2 = afterUpdate.contains(noteBefore2);
        boolean hasNoteBefore3 = afterUpdate.contains(noteBefore3);
        boolean hasNoteAfter1 = afterUpdate.contains(noteAfter1);
        boolean hasNoteAfter2 = afterUpdate.contains(noteAfter2);
        boolean hasNoteAfter3 = afterUpdate.contains(noteAfter3);

        Assertions.assertFalse(hasNoteBefore1);
        Assertions.assertTrue(hasNoteBefore2);
        Assertions.assertFalse(hasNoteBefore3);
        Assertions.assertTrue(hasNoteAfter1);
        Assertions.assertTrue(hasNoteAfter2);
        Assertions.assertTrue(hasNoteAfter3);
    }

    @Test
    void deleteNote_should_not_fail_when_deleting_non_existing_note() {
        // Given
        notesModel.setNotes(new ArrayList<>());

        // When
        notesModel.deleteNote(new Note("", "", ""));

        // Then
        Assertions.assertTrue(true);
    }

    @Test
    void deleteNote_should_delete_only_given_note() {
        // Given
        Note n1 = new Note("t1", "d1", "c1");
        Note n2 = new Note("t2", "d2", "c2");
        Note n3 = new Note("t3", "d3", "c3");
        ArrayList<Note> notesBefore = new ArrayList<>();
        notesBefore.add(n1);
        notesBefore.add(n2);
        notesModel.setNotes(notesBefore);

        // When
        notesModel.deleteNote(n1);
        notesModel.deleteNote(n3);

        // Then
        ArrayList<Note> notesAfter = notesModel.getAllNotes();
        Assertions.assertFalse(notesAfter.contains(n1));
        Assertions.assertTrue(notesAfter.contains(n2));
        Assertions.assertFalse(notesAfter.contains(n3));
    }

    @Test
    void addNote_should_add_a_copy() {
        // Given
        notesModel.setNotes(new ArrayList<>());
        Note note1 = new Note("t1", "d1", "c1");


        // When
        note1.setTitle("t3");

        // Then
        ArrayList<Note> notes = notesModel.getAllNotes();
        Assertions.assertFalse(notes.contains(note1));
    }

    @Test
    void setNotes_should_set_a_copy() {
        // Given
        ArrayList<Note> outsideNotes = new ArrayList<>();
        Note note1 = new Note("t1", "d1", "c1");
        Note note2 = new Note("t2", "d2", "c2");
        Note note3 = new Note("t3", "d3", "c3");
        outsideNotes.add(note1);
        outsideNotes.add(note2);
        outsideNotes.add(note3);
        notesModel.setNotes(outsideNotes);

        // When
        note1.setTitle("t4");
        note2.setText("d4");
        note3.setCategory("c4");

        // Then
        ArrayList<Note> insideNotes = notesModel.getAllNotes();
        Assertions.assertFalse(insideNotes.contains(note1));
        Assertions.assertFalse(insideNotes.contains(note2));
        Assertions.assertFalse(insideNotes.contains(note3));
    }

    @Test
    void getAllNotes_should_return_a_copy() {
        // Given
        ArrayList<Note> copy = notesModel.getAllNotes();

        // When
        copy.add(new Note("", "", ""));

        // Then
        ArrayList<Note> copy2 = notesModel.getAllNotes();
        Assertions.assertNotEquals(copy, copy2);
    }

    @Test
    void updateNoteById_should_update_a_copy() {
        // Given
        notesModel.setNotes(new ArrayList<>());
        Note note1 = new Note("t1", "d1", "c1");
        Note note2 = new Note("t2", "d2", "c2");
        Note note3 = new Note("t3", "d3", "c3");
        notesModel.addNote(note1);

        // When
        notesModel.updateNoteById(note1.getId(), note3);
        note1 = note2;

        // Then
        ArrayList<Note> notes = notesModel.getAllNotes();
        Assertions.assertFalse(notes.contains(note1));
        Assertions.assertFalse(notes.contains(note2));
        Assertions.assertTrue(notes.contains(note3));
    }
}
