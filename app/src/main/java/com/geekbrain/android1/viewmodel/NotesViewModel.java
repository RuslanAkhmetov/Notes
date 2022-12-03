package com.geekbrain.android1.viewmodel;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import static com.geekbrain.android1.R.array.descriptions;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.geekbrain.android1.Note;
import com.geekbrain.android1.NotesApplication;
import com.geekbrain.android1.R;
import com.geekbrain.android1.ResourceProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotesViewModel extends ViewModel implements ListNoteViewModel {
    private static final String TAG = "ViewModel";

    ResourceProvider resourceProvider;

    private boolean addNewToTheEnd = true;

    public NotesViewModel(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    public static final ViewModelInitializer<NotesViewModel> initializer = new ViewModelInitializer<>(
            NotesViewModel.class,
            creationExtras -> {
                NotesApplication app = (NotesApplication) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                return new NotesViewModel(app.getResourceProvider());
            }
    );

    public boolean isAddNewToTheEnd() {
        return addNewToTheEnd;
    }

    public void setAddNewToTheEnd(boolean addNewToTheEnd) {
        this.addNewToTheEnd = addNewToTheEnd;
    }

    private MutableLiveData<List<Note>> notes;

    private List<Note> noteList;

    public Note getCurrentNote() {
        return currentNote;
    }

    public void setCurrentNote(Note currentNote) {
        this.currentNote = currentNote;
    }

    private Note currentNote;

    public LiveData<List<Note>> initNotes() {
        if (notes == null) {
            notes = new MutableLiveData<>();
            init();
//            init(20);
        }
        return notes;
    }

    public Note getNote(UUID uuid) {
        if (notes == null) throw new NullPointerException("Note is not initialized");
        try {
            List<Note> list = notes.getValue();
            for (Note note : list) {
                if (note.getUuid() == uuid) {
                    currentNote = note;
                    return note;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("There is no Note with uuid: " + uuid);
        }
        return null;
    }

    public Note getFirst() {
        if (notes == null || notes.getValue().size() == 0)
            throw new NullPointerException("Note is not initialized or empty");
        currentNote = notes.getValue().get(0);
        return currentNote;
    }

    private void init(){
        List<Note> list = new ArrayList<>();
        Context context = NotesApplication.getContext();
        for(int i = 0; i < resourceProvider.getStringArray(R.array.titles).length; i++) {
            list.add(new Note( resourceProvider.getStringArray(R.array.titles)[i], resourceProvider.getStringArray(descriptions)[i],generateDate(i) , 0));
            Log.i(TAG, "init: " + list.get(i).getName());
        }

        notes.setValue(list);
    }

    private void init(int num) {
        List<Note> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {

            list.add(new Note("Notes " + i, "This is note " + i, generateDate(i) , 0));
        }
        notes.setValue(list);
    }

    @Override
    public int deleteNote(Note note) {                             //return index of current note after delete or -1 if delete is impossible
        try{
        List<Note> list = notes.getValue();
        if (list.size()==1)
            return -1;
        if (note != null) {
            int removeIndex = list.indexOf(note);
            if (removeIndex == list.size()-1) {
                notes.getValue().remove(note);
                setCurrentNote(list.get(list.size()-1));
                return list.size()-1;
            } else {
                notes.getValue().remove(note);
                setCurrentNote(list.get(removeIndex));
                return removeIndex;
            }
        }
        } catch (Exception e){
            Log.i(TAG, "deleteNote: impossible");
        }
        return -1;
    }

    @Override
    public int editCurrentNote(Note note) {
        try {
            currentNote.setName(note.getName());
            currentNote.setBody(note.getBody());
            currentNote.setBackColor(note.getBackColor());
            currentNote.setNoteDate(LocalDate.now());
            Log.i(TAG, "editCurrentNote: " + LocalDate.now());
            return notes.getValue().indexOf(currentNote);
        } catch (Exception e){
            Log.i(TAG, "editCurrentNote: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public int addNote(Note note) {
        try {
            if (addNewToTheEnd) {
                notes.getValue().add(note);
            } else {
                notes.getValue().add(0, note);
            }
            currentNote = note;
            return notes.getValue().indexOf(currentNote);
        } catch (Exception e){
            Log.i(TAG, "addNote: " + e.getMessage());
            return -1;
        }
    }

    private LocalDate generateDate (int i) {
        int d = i % 30 == 0 ? 30 : i % 30;
        String day = String.valueOf(d).length() == 1 ? "0" + d : String.valueOf(d);
        int m = 1 + i / 30;
        String month = String.valueOf(m).length() == 1 ? "0" + m : String.valueOf(m);
        String date = day + "-" + month + "-" + "2022";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        return LocalDate.parse(date, dateTimeFormatter);
    }
}
