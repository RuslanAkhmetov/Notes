package com.geekbrain.android1;

import android.graphics.Color;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.geekbrain.android1.viewmodel.NoteForGSON;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

public class Note implements Parcelable {

    private final UUID uuid;
    private String name;
    private String body;
    private LocalDate noteDate;
    private int backColor;
    private boolean inBasket;
    private boolean archived;


    public Note() {
        this.uuid = UUID.randomUUID();
        this.name = "";
        this.body = "";
        this.noteDate = LocalDate.now();
        this.backColor = Color.parseColor("#FFFFFF");
        this.inBasket = true;
        this.archived = true;
    }

    public Note(String name, String body, LocalDate noteDate, int backColor) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.body = body;
        this.noteDate = noteDate;
        this.backColor = backColor;
    }

    public Note(String name, String body, LocalDate noteDate, int backColor, boolean inBasket, boolean archived) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.body = body;
        this.noteDate = noteDate;
        this.backColor = backColor;
        this.inBasket = inBasket;
        this.archived = archived;
    }

    public Note(NoteForGSON noteForGSON) {
        this.uuid = UUID.fromString(noteForGSON.getUuid());
        this.name = noteForGSON.getName();
        this.body = noteForGSON.getBody();
        this.noteDate = Instant.ofEpochSecond(noteForGSON.getNoteDate()).atZone(ZoneId.systemDefault()).toLocalDate();
        this.backColor = noteForGSON.getBackColor();
        this.inBasket = noteForGSON.isInBasket();
        this.archived = noteForGSON.isArchived();
    }

    public String getName() {
        return name;
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

    public LocalDate getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(LocalDate noteDate) {
        this.noteDate = noteDate;
    }



    public UUID getUuid() {
        return uuid;
    }

    public int getBackColor() {
        return backColor;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }

    public boolean isInBasket() {
        return inBasket;
    }

    public void setInBasket(boolean inBasket) {
        this.inBasket = inBasket;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            parcel.writeBoolean(isInBasket());
            parcel.writeBoolean(isArchived());
        } else {
            parcel.writeInt(BooleanToInt(isInBasket()));
            parcel.writeInt(BooleanToInt(isArchived()));
        }
    }

    protected Note(Parcel parcel) {
        uuid = (UUID) parcel.readSerializable();
        name = parcel.readString();
        body = parcel.readString();
        noteDate = (LocalDate) parcel.readSerializable();
        backColor = parcel.readInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            inBasket = parcel.readBoolean();
            archived = parcel.readBoolean();
        } else {
            inBasket = IntToBoolean(parcel.readInt());
            archived = IntToBoolean(parcel.readInt());
        }

    }

    public Note copy() {
        return new Note(name, body, noteDate, backColor, inBasket, archived);
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel parcel) {
            return new Note(parcel);
        }

        @Override
        public Note[] newArray(int i) {
            return new Note[i];
        }
    };

    private int BooleanToInt(boolean b) {
        if (b)
            return 1;
        else
            return 0;
    }

    private boolean IntToBoolean (int i){
        if(i==1)
            return true;
        else if (i==0)
            return false;
        else
            throw new IllegalArgumentException(String.format("Can't cast %d to boolean", i));
    }

}
