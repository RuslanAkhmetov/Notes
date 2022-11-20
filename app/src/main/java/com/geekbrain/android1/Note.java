package com.geekbrain.android1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

public class Note implements Parcelable {
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

    private final UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    private String name;
    private String body;
    private Date noteDate;

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
    }

    protected Note(Parcel parcel){
        uuid = (UUID) parcel.readSerializable();
        name = parcel.readString();
        body = parcel.readString();
        noteDate = (Date) parcel.readSerializable();
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
