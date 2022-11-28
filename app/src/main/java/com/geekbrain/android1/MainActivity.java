package com.geekbrain.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String  TAG ="Main_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_primary));
        NotesFragment notesFragment = new NotesFragment();
        setSupportActionBar(findViewById(R.id.toolbar));
        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();

/*        if (savedInstanceState == null) {
                    fragmentTransaction
                            .add(R.id.fragment_container, notesFragment)
                            .add(R.id.note_body_container, NoteBodyFragment.newInstance());
        } else {
            Log.i(TAG, "On Create");*/
                    fragmentTransaction
                            .add(R.id.fragment_container, notesFragment)
                            .add(R.id.note_body_container, NoteBodyFragment.newInstance());
//        }
        fragmentTransaction.commit();
//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        View list_layout = findViewById(R.id.nested_scroll_view);
        list_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.app_bar_search:
                Toast.makeText(this, R.string.search_action, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.cozy_view_action:
                Toast.makeText(this, R.string.cozy_view, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
        //return super.onOptionsItemSelected(item);
    }

}