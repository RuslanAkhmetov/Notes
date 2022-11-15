package com.geekbrain.android1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.geekbrain.android1.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesViewModel extends ViewModel {
    private MutableLiveData<List<Note>> notes;

    public LiveData<List<Note>> getNotes() {
        if (notes == null){
            notes = new MutableLiveData<List<Note>>();
            init (100);
        }
        return notes;
    }

    public int currentNote = 0;

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
