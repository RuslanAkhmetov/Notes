package com.geekbrain.android1;

import java.util.Date;

public class Note {
    public String getName() {
        return name;
    }

    public Note(String name, String body, Date noteDate) {
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

    private String name = "";
    private String body = "";
    private Date noteDate = new Date();
}
