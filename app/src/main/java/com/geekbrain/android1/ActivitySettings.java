package com.geekbrain.android1;


public class ActivitySettings {
    public ActivitySettings(){
        column = 1;
        inBasket = false;
        archived = false;
    }

    private static ActivitySettings INSTANCE;

    public static ActivitySettings init(){

        if (INSTANCE == null) {
            INSTANCE = new ActivitySettings();
        }
        return INSTANCE;
    }

    public static ActivitySettings initFromGSON(ActivitySettings activitySettings){
        ActivitySettings.INSTANCE = activitySettings;
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