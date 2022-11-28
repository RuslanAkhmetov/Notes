package com.geekbrain.android1.viewmodel;

import com.geekbrain.android1.Note;

public interface ListNoteViewModel {
    public int deleteNote(Note note);

    public int editCurrentNote(Note note); //return index of current Node or -1 if editing unsuccessful
}
