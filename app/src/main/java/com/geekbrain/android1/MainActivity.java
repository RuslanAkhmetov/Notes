package com.geekbrain.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.prefs.Preferences;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";
    private static final String KEY = "key";

    private static final String NOTES_FRAGMENT = "NotesFragment";
    private static final String MAINACTIVITY = "com.geekbrain.android1.shared_prefs.Main_Activity";

    public SharedPreferences sharedPreferences = null;
    ActivitySettings activitySettings = new ActivitySettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MAINACTIVITY, MODE_PRIVATE);

        String savedPreferences = sharedPreferences.getString(KEY, null);


        if (savedPreferences == null){
             activitySettings = ActivitySettings.init();
        } else{
            try {
                activitySettings = ActivitySettings.initFromGSON(new GsonBuilder().create().fromJson(savedPreferences,
                        (Type) ActivitySettings.class));
                Log.i(TAG, "onCreate: " + savedPreferences);
            } catch (Exception e) {
                activitySettings = ActivitySettings.init();
                Log.i(TAG, "onCreate: " + e.getMessage());
            }
        }

        if (activitySettings.isArchived()){
            setTitle(R.string.archive_drawer_action);
        } else if (activitySettings.isInBasket()){
            setTitle(R.string.basket_drawer_action);
        } else {
            setTitle(R.string.notes_drawer_action);
        }

        NotesFragment notesFragment = NotesFragment.newInstance(activitySettings.getColumn(), activitySettings.isInBasket(), activitySettings.isArchived());

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_primary));
        setSupportActionBar(toolbar);
        initDrawer(toolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (savedInstanceState == null) {
            fragmentTransaction
                    .add(R.id.fragment_container, notesFragment, NOTES_FRAGMENT)
                    .add(R.id.note_body_container, NoteBodyFragment.newInstance());
        } else {
            Log.i(TAG, "On Create");

            fragmentTransaction
                    .replace(R.id.fragment_container, notesFragment, NOTES_FRAGMENT)
                    .replace(R.id.note_body_container, NoteBodyFragment.newInstance());
        }
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                Toast.makeText(this, R.string.search_action, Toast.LENGTH_SHORT).show();
                return true;

            case R.id.cozy_view_action:
                activitySettings.setColumn(activitySettings.getColumn() == 1 ? 2 : 1);
                if (activitySettings.getColumn() == 1) {
                    Toast.makeText(this, R.string.linear_view, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.two_columns, Toast.LENGTH_SHORT).show();
                }
                String preferencesJson = new GsonBuilder().create().toJson(activitySettings);
                sharedPreferences.edit().putString(KEY, preferencesJson).apply();

                NotesFragment notesFragment = NotesFragment.newInstance(activitySettings.getColumn(), activitySettings.isInBasket(), activitySettings.isArchived());
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction
                        .replace(R.id.fragment_container, notesFragment, NOTES_FRAGMENT)
                        .replace(R.id.note_body_container, NoteBodyFragment.newInstance());
                fragmentTransaction.commit();
                return true;

            default:
                return false;
        }
    }


    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        ActivitySettings activitySettings = ActivitySettings.init();
        Log.i(TAG, "initDrawer: " + activitySettings.toString());

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_one,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.drawer_notes:
                    activitySettings.setArchived(false);
                    activitySettings.setInBasket(false);
                    break;

                case R.id.drawer_notification:
                    activitySettings.setArchived(false);
                    activitySettings.setInBasket(false);
                    break;

                case R.id.drawer_basket:
                    activitySettings.setInBasket(true);
                    activitySettings.setArchived(false);
                    break;

                case R.id.drawer_archive:
                    activitySettings.setArchived(true);
                    activitySettings.setInBasket(false);
                    break;

                case R.id.drawer_SETTINGS:
                    openSettingFragment();
                    drawerLayout.close();
                    return true;

                default:
                    return false;
            }

            String preferencesJson = new GsonBuilder().create().toJson(activitySettings);

            Log.i(TAG, "initDrawer: " +  preferencesJson);

            sharedPreferences.edit()
                    .putString(KEY, preferencesJson)
                    .apply();
            NotesFragment notesFragment = NotesFragment.newInstance(activitySettings.getColumn(), activitySettings.isInBasket(), activitySettings.isArchived());
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction
                    .replace(R.id.fragment_container, notesFragment, NOTES_FRAGMENT)
                    .replace(R.id.note_body_container, NoteBodyFragment.newInstance());
            fragmentTransaction.commit();
            item.setChecked(true);
            setTitle(item.getTitle());
            drawerLayout.close();

            return true;

        });


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    private void openSettingFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .add(R.id.fragment_container, new SettingFragment(), "Setting")
                .commit();
    }


}