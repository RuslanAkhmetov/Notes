package com.geekbrain.android1;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String NOTE_UUID = "uuid";
    private UUID uuidActivity;

    private static final String  TAG ="Main_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotesFragment notesFragment = new NotesFragment();

        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();

        if (savedInstanceState == null) {
                    fragmentTransaction.add(R.id.fragment_container, notesFragment)
                            .add(R.id.note_body_container, NoteBodyFragment.newInstance(null));

        } else {
            uuidActivity = (UUID) savedInstanceState.getSerializable(NOTE_UUID);
            Log.i(TAG, "On Create uuidActivity: " + uuidActivity);
                    fragmentTransaction.replace(R.id.fragment_container, notesFragment)
                            .replace(R.id.note_body_container, NoteBodyFragment.newInstance(null));
//                            .replace(R.id.note_body_container, NoteBodyFragment.newInstance(uuidActivity));
        }
        fragmentTransaction.commit();
//
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        View list_layout = findViewById(R.id.nested_scroll_view);
        list_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putSerializable(NOTE_UUID, uuidActivity);
        super.onSaveInstanceState(outState, outPersistentState);
    }
}