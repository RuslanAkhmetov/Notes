package com.geekbrain.android1;


public class Preferences  {
    public Preferences(){
        column = 1;
        inBasket = false;
        archived = false;
    }

    private static Preferences INSTANCE;

    public static Preferences init(){

        if (INSTANCE == null) {
            INSTANCE = new Preferences();
        }
        return INSTANCE;
    }

    public static Preferences initFromGSON(Preferences preferences){
        Preferences.INSTANCE = preferences;
        return INSTANCE;
    }

    private int column ;
    private boolean inBasket;
    private boolean archived;

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

    public void setColumn(int column) {
        if (column != 1)
            this.column = 2;
        else
            this.column = 1;
    }

    public int getColumn() {
        return column;
    }


/*    public String mytoString() {
        String serial = "{\"column\"" + column + ",\"inBasket\":" + "\"archived\":" + archived+"}";

        return serial;
    }*/
}