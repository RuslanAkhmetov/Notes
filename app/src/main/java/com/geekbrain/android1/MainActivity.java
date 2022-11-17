package com.geekbrain.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String NOTE_UUID = "uuid";
    private UUID uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotesFragment notesFragment = new NotesFragment();

        if(savedInstanceState != null)
            uuid = (UUID) savedInstanceState.getSerializable(NOTE_UUID);

        NoteBodyFragment noteBodyFragment = NoteBodyFragment.newInstance(uuid);

        /*if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, notesFragment)
                    .replace(R.id.note_body_container, NoteBodyFragment.newInstance(uuid))
                    .commit();
        } else {*/
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, notesFragment)
                    .replace(R.id.note_body_container, noteBodyFragment )
                    .commit();
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        View list_layout  = findViewById (R.id.nested_scroll_view);
        list_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putSerializable(NOTE_UUID, uuid);
        super.onSaveInstanceState(outState, outPersistentState);
    }
}