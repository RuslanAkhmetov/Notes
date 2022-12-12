package com.geekbrain.android1.viewmodel;

import com.geekbrain.android1.Note;

import java.time.ZoneId;

public class NoteForGSON {
    private String uuid;
    private String name;
    private String body;
    private long noteDate;
    private int backColor;
    private boolean inBasket;
    private boolean archived;


    public NoteForGSON(Note note) {
        this.uuid = note.getUuid().toString();
        name = note.getName();
        body = note.getBody();
        noteDate = note.getNoteDate().atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond();
        backColor = note.getBackColor();
        inBasket = note.isInBasket();
        archived = note.isArchived();
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public long getNoteDate() {
        return noteDate;
    }

    public int getBackColor() {
        return backColor;
    }

    public boolean isInBasket() {
        return inBasket;
    }

    public boolean isArchived() {
        return archived;
    }
}
