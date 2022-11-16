package com.geekbrain.android1;

import java.util.Date;
import java.util.UUID;

public class Note {
    public String getName() {
        return name;
    }

    public Note(String name, String body, Date noteDate) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.body = body;
        this.noteDate = noteDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(Date noteDate) {
        this.noteDate = noteDate;
    }

    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    private String name = "";
    private String body = "";
    private Date noteDate = new Date();
}
