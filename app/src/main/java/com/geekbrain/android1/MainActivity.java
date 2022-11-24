package com.geekbrain.android1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private static final String  TAG ="Main_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotesFragment notesFragment = new NotesFragment();

        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();

        if (savedInstanceState == null) {
                    fragmentTransaction.add(R.id.fragment_container, notesFragment)
                            .add(R.id.note_body_container, NoteBodyFragment.newInstance());
        } else {
            Log.i(TAG, "On Create");
                    fragmentTransaction.replace(R.id.fragment_container, notesFragment)
                            .replace(R.id.note_body_container, NoteBodyFragment.newInstance());
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


}