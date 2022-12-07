package com.geekbrain.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";
    private static final String NOTES_FRAGMENT = "NotesFragment";

    private static int column = 1;
    private static boolean inBasket = false;
    private static boolean archived    = false;

    public static boolean isInBasket() {
        return inBasket;
    }
    public static void setInBasket(boolean inBasket) {
        MainActivity.inBasket = inBasket;
    }
    public static boolean isArchived() {
        return archived;
    }
    public static void setArchived(boolean archived) {
        MainActivity.archived = archived;
    }
    public static void setColumn(int column) {
        if (column != 1)
            MainActivity.column = 2;
        else
            MainActivity.column = 1;
    }
    public static int getColumn() {
        return column;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_primary));
        NotesFragment notesFragment = NotesFragment.newInstance(column, inBasket, archived);
        setSupportActionBar(findViewById(R.id.toolbar));
        initToolBarDrawer();

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
                setColumn(getColumn() == 1 ? 2 : 1);
                if (column == 1) {
                    Toast.makeText(this, R.string.linear_view, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.two_columns, Toast.LENGTH_SHORT).show();
                }
                NotesFragment notesFragment = NotesFragment.newInstance(column, inBasket, archived);
                setSupportActionBar(findViewById(R.id.toolbar));
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction
                        .replace(R.id.fragment_container, notesFragment, NOTES_FRAGMENT)
                        .replace(R.id.note_body_container, NoteBodyFragment.newInstance());
                fragmentTransaction.commit();
                return true;
            default:
                return false;
        }
        //return super.onOptionsItemSelected(item);
    }

    private void initToolBarDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {
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
            switch (id) {
                case R.id.drawer_notes:
                    setArchived(false);
                    setInBasket(false);
                    drawerLayout.close();
                    //TODO UI update
                    return true;
                case R.id.drawer_notification:
                    drawerLayout.close();
                    return true;
                case R.id.drawer_SETTINGS:
                    openSettingFragment();
                    drawerLayout.close();
                    return true;
                case R.id.drawer_basket:
                    setInBasket(true);

                    drawerLayout.close();

                    //TODO UI update
                    return true;
                case R.id.drawer_archive:
                    setArchived(true);
                    drawerLayout.close();
                    //TODO UI update
                    return true;
                default:
                    return false;
            }

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