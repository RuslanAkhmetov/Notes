package com.geekbrain.android1;

import android.graphics.Color;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.util.UUID;

public class Note implements Parcelable {
    public Note() {
        this.uuid = UUID.randomUUID();
        this.name = "";
        this.body = "";
        this.noteDate = LocalDate.now();
        this.backColor = Color.parseColor("#FFFFFF");
    }

    public String getName() {
        return name;
    }

    public Note(String name, String body, LocalDate noteDate, int backColor) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.body = body;
        this.noteDate = noteDate;
        this.backColor = backColor;

    }

    private String name;
    private String body;
    private LocalDate noteDate;
    private int backColor;

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(LocalDate noteDate) {
        this.noteDate = noteDate;
    }

    private final UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public int getBackColor() {
        return backColor;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getUuid());
        parcel.writeString(getName());
        parcel.writeString(getBody());
        parcel.writeSerializable(getNoteDate());
        parcel.writeInt(getBackColor());
    }

    protected Note(Parcel parcel){
        uuid = (UUID) parcel.readSerializable();
        name = parcel.readString();
        body = parcel.readString();
        noteDate = (LocalDate) parcel.readSerializable();
        backColor = parcel.readInt();
    }

    public Note copy(){
        return new Note(name, body, noteDate, backColor);
    }

    public static final Creator <Note> CREATOR  = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel parcel) {
            return new Note(parcel);
        }

        @Override
        public Note[] newArray(int i) {
            return new Note[i];
        }
    };
}
