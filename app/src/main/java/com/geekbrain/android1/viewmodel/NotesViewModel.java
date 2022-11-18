package com.geekbrain.android1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.geekbrain.android1.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotesViewModel extends ViewModel {
    private MutableLiveData<List<Note>> notes;

    public Note getCurrentNote() {
        return currentNote;
    }

    public void setCurrentNote(Note currentNote) {
        this.currentNote = currentNote;
    }

    private Note currentNote;

    public LiveData<List<Note>> getNotes() {
        if (notes == null){
            notes = new MutableLiveData<>();
            init (20);
        }
        return notes;
    }

    public Note getNote(UUID uuid){
        if(notes == null) throw new NullPointerException("Note is not initialized");
        try {
            List<Note> list = notes.getValue();
            for (Note note: list) {
                if (note.getUuid() == uuid){
                    currentNote = note;
                    return note;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("There is no Note with uuid: " + uuid);
        }
        return null;
    }

    public Note getFirst(){
        if(notes == null || notes.getValue().size() == 0) throw new NullPointerException("Note is not initialized or empty");
        currentNote = notes.getValue().get(0);
        return currentNote;
    }




    private void init(int num){
         List<Note> list = new ArrayList<>() ;
        for (int i = 0; i < num; i++) {
            int m = 1 + i/30;
            int d = i % 30==0 ? 30 : i % 30;
            list.add(new Note("Notes " + i, "This is note " + i, new Date()));
        }
        if (list != null)  notes.setValue(list);
    }

}
