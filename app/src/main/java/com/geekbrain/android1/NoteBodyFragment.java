package com.geekbrain.android1;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.geekbrain.android1.viewmodel.NotesViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteBodyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteBodyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String NOTE_UUID = "uuid";
    private static final String NOTE = "note";

    private static final String TAG = "NoteBody_Fragment";

    private UUID uuidFragment;
    private Note note;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public NoteBodyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NoteBodyFragment newInstance(UUID uuid) {
        NoteBodyFragment fragment = new NoteBodyFragment();
        if (uuid != null) {
            Bundle args = new Bundle();
            args.putSerializable(NOTE_UUID, uuid);
            fragment.setArguments(args);
        }
        return fragment;
    }

    public static NoteBodyFragment newInstance(Note note) {
        NoteBodyFragment fragment = new NoteBodyFragment();
        if (note != null) {
            Bundle args = new Bundle();
            args.putParcelable(NOTE, note);
            fragment.setArguments(args);
        }
        return fragment;
    }

    public static NoteBodyFragment newInstance() {
        return new NoteBodyFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            uuidFragment = (UUID) getArguments().getSerializable(NOTE_UUID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_note_body, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initBodyFragment(view);
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu);
        bottomNavigationView.setOnItemSelectedListener(item -> onItemAction(item));

    }

    public void initBodyFragment() {
        View view = requireActivity().findViewById(R.id.note_body_container_1);
//       ((ViewGroup) view).removeAllViews();
        initBodyFragment(view);
    }

    private void initBodyFragment(@NonNull View view) {
//        ((ViewGroup) view).removeAllViews();
        Bundle arguments = getArguments();
        if (arguments != null) {
            note = arguments.getParcelable(NOTE);
        }

        NotesViewModel model = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);

        if (note == null) {
            note = model.getCurrentNote() == null ? model.getFirst()
                    : model.getCurrentNote();
        }

        try {
            if (note != null) {
                model.setCurrentNote(note);
                Log.i(TAG, "Fragment: " + note.getName());

                view.setBackgroundColor(note.getBackColor());
                TextView nameText = view.findViewById(R.id.note_name);
                TextView bodyText = view.findViewById(R.id.note_body);
                TextView dateText = view.findViewById(R.id.note_date);

                nameText.setText(note.getName());
                bodyText.setText(note.getBody());
                dateText.setText(note.getNoteDate().toString());
            } else {
                Log.i(TAG, "Can't make NoteBodyFragment");
            }
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }

    public boolean onItemAction(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.palette_action:
                Log.i(TAG, "onOptionsItemSelected: palette.");
                showPalette();
                break;
            case R.id.edit_action:
                Log.i(TAG, "onOptionsItemSelected: edit.");
                Toast.makeText(requireActivity(), getString(R.string.edit_note), Toast.LENGTH_SHORT)
                        .show();
                return true;
            case R.id.delete_action:
                //Toast.makeText(requireActivity(), getString(R.string.delete_note), Toast.LENGTH_SHORT).show();
                NotesViewModel model = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
                if (model.deleteNote(model.getCurrentNote())>=0) {
                    NotesFragment notesFragment = new NotesFragment();
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, notesFragment)
                            .replace(R.id.note_body_container, NoteBodyFragment.newInstance(model.getCurrentNote()))
                            .commit();
                    return true;
                }
                return false;
            case R.id.back_action:
                //updateNotesListData();
                requireActivity().onBackPressed();
                return true;
            default:
                return false;
        }
        return false;
    }

    private void showPalette() {
        PaletteFragment paletteFragment = PaletteFragment.newInstance();
        Log.i(TAG, "Button palette was clicked " + paletteFragment);
        Log.i(TAG, "BackColor:" + note.getBackColor());

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.note_body_container, paletteFragment)
                .addToBackStack("")
                .commit();
    }

    private void updateNotesListData() {
        for (Fragment fragment : requireActivity().getSupportFragmentManager().getFragments()) {
            if (fragment instanceof NotesFragment) {
                ((NotesFragment) fragment).fragmentInit();
            }
        }
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}