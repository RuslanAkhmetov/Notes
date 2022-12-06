package com.geekbrain.android1.ui;

import android.view.View;

import com.geekbrain.android1.Note;

public interface OnItemClickListener {
    default void onItemClick(View view, int position) {

    }
    void onItemClick(View view, Note note);
}
