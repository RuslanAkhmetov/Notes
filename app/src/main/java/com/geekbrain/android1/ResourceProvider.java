package com.geekbrain.android1;

import android.content.Context;

import androidx.annotation.ArrayRes;
import androidx.annotation.StringRes;

public class ResourceProvider {
    private Context mContext;

    public ResourceProvider(Context mContext) {
        this.mContext = mContext;
    }

    public String[] getStringArray(@ArrayRes int resId){
        return mContext.getResources().getStringArray(resId);
    }

    public String getString(@StringRes int resId){
        return mContext.getResources().getString(resId);
    }
}
