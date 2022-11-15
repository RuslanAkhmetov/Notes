package com.geekbrain.android1.viewmodel;

import androidx.lifecycle.ViewModel;

import com.geekbrain.android1.Note;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class NotesViewModel extends ViewModel {
    private List<Note> notes;

    public List<Note> getNotes() {
        return notes;
    }

    public int currentNote = 0;

    public Note getNote(int index){
        return notes[index];
    }

    private List<Note> init(int num){
        List<Note> list = new List<Note>;
        for (int i = 0; i < num; i++) {
            int m = 1 + i/30;
            int d = i % 30==0 ? 30 : i % 30;
            list.add(Note("Notes " + i, "This is note " + i, new LocalDate.of(2022, m, d)));
        }
    }

}
