package com.geekbrain.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


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

        initToolBarDrawer();

        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();

       if (savedInstanceState == null) {
                    fragmentTransaction
                            .add(R.id.fragment_container, notesFragment)
                            .add(R.id.note_body_container, NoteBodyFragment.newInstance());
        } else {
            Log.i(TAG, "On Create");
                    fragmentTransaction
                            .replace(R.id.fragment_container, notesFragment)
                            .replace(R.id.note_body_container, NoteBodyFragment.newInstance());
        }
        fragmentTransaction.commit();
//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
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

    private void initToolBarDrawer(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar){
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_one,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id){
                case R.id.drawer_notes:

                case R.id.drawer_notification:
                    drawerLayout.close();
                    return true;
                case R.id.drawer_SETTINGS:
                    openSettingFragment();
                    drawerLayout.close();
                    return true;
                default:
                    return false;
            }

        });

    }



    private void openSettingFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .add(R.id.fragment_container, new SettingFragment(), "Setting")
                .commit();
    }


}