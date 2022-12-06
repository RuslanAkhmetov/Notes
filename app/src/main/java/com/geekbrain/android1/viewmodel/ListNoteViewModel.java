package com.geekbrain.android1.viewmodel;

import com.geekbrain.android1.Note;

public interface ListNoteViewModel {
    int deleteNote(Note note);

    int archivenode(Note note);

    int finalDelete(Note note);

    int editCurrentNote(Note note); //return index of current Node or -1 if editing unsuccessful

    int addNote(Note note);         //return index of current Node or -1 if adding unsuccessful

    Note getFirst();

    Note getFirstInBasket();

    Note getFirstArchived();
}
