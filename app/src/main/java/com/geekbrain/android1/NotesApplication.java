package com.geekbrain.android1;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class NotesApplication extends Application {

    private  ResourceProvider resourceProvider;

    public  ResourceProvider getResourceProvider(){
        if (resourceProvider == null){
            return new ResourceProvider(this);
        }
        return resourceProvider;
    }

    private static Context mContext;

    public static Context getContext() {

        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        //resourceProvider = new ResourceProvider(this);
    }


}
