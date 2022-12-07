package com.geekbrain.android1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
    private static final String CONFIRMATION = "Confirm_Dialog";
    private static final int REQUEST_CODE = 0;


    private UUID uuidFragment;
    private Note note;
    private Callbacks callbacks = new Callbacks() {
        @Override
        public void OnPositiveButtonClicked() {
            NotesViewModel model = new ViewModelProvider(getActivity(),
                    ViewModelProvider.Factory.from(NotesViewModel.initializer)).get(NotesViewModel.class);
            if (model.deleteNote(model.getCurrentNote()) >= 0) {
                NotesFragment notesFragment = NotesFragment.newInstance(
                        MainActivity.getColumn(), MainActivity.isInBasket(), MainActivity.isArchived());
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, notesFragment)
                        .replace(R.id.note_body_container, NoteBodyFragment.newInstance(model.getCurrentNote()))
                        .commit();
            }
        }

        @Override
        public void OnNegativeButtonClicked() {

        }
    };


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
            note = getArguments().<Note>getParcelable(NOTE);
            /*if (getActivity().getSupportFragmentManager().findFragmentByTag(CONFIRMATION) != null){
                DialogFragment fragment = (DialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag(CONFIRMATION);
                fragment.setCallbacks(callbacks);*/

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
        if (MainActivity.isArchived() || MainActivity.isInBasket()) {
            bottomNavigationView.findViewById(R.id.edit_action).setVisibility(View.GONE);
            bottomNavigationView.findViewById(R.id.restore_action).setVisibility(View.VISIBLE);
        } else {
            bottomNavigationView.findViewById(R.id.edit_action).setVisibility(View.VISIBLE);
            bottomNavigationView.findViewById(R.id.restore_action).setVisibility(View.GONE);
        }
        if (MainActivity.isArchived()) {
            bottomNavigationView.findViewById(R.id.archive_action).setVisibility(View.GONE);
        }
        bottomNavigationView.setOnItemSelectedListener(item -> onItemAction(item));

    }

    public void initBodyFragment() {
        View view = requireActivity().findViewById(R.id.note_body_edit_container);
        initBodyFragment(view);
    }

    private void initBodyFragment(@NonNull View view) {
        Bundle arguments = getArguments();

        NotesViewModel model = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(NotesViewModel.initializer)).get(NotesViewModel.class);
        if (arguments != null) {
            note = arguments.getParcelable(NOTE);
        }
        if (note == null) {
            note = model.getCurrentNote() == null ? model.getFirst()
                    : model.getCurrentNote();
        }
        if (MainActivity.isInBasket() && !note.isInBasket() ) {
            if (model.getFirstInBasket() != null) {
                note = model.getFirstInBasket();
            }
        } else if (MainActivity.isArchived() && !note.isArchived()) {
            if (model.getFirstArchived()!= null) {
                note = model.getFirstArchived();
            }
        } else if (!MainActivity.isArchived() && !MainActivity.isInBasket() && (note.isInBasket() || note.isArchived()))
            note = model.getFirst();

        try {
            if (note != null) {
                model.setCurrentNote(note);
                Log.i(TAG, "Fragment: " + note.getName());
                if (note.getBackColor() != 0) {
                    view.setBackgroundColor(note.getBackColor());
                }
                TextView nameText = view.findViewById(R.id.edit_note_name);
                TextView bodyText = view.findViewById(R.id.edit_note_body);
                TextView dateText = view.findViewById(R.id.note_date);

                nameText.setText(note.getName());
                bodyText.setText(note.getBody());
                dateText.setText(note.getNoteDate().toString());
                if (note.getBackColor() == 0) {
                    bodyText.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.frame_border));
                } else if (note.getBackColor() == getResources().getColor(R.color.teal_700, null)) {
                    bodyText.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.frame_border_teal_700));
                } else if (note.getBackColor() == getResources().getColor(R.color.purple_200, null)) {
                    bodyText.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.frame_border_purple_200));
                }

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
            case R.id.add_action:
                Log.i(TAG, "onOptionsItemSelected: palette.");
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.note_body_container, NoteBodyEditFragment.newInstance(true, new Note()))
                        .commit();
                return true;
            case R.id.edit_action:
                Log.i(TAG, "onOptionsItemSelected: edit.");
                Toast.makeText(requireActivity(), getString(R.string.edit_note), Toast.LENGTH_SHORT)
                        .show();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.note_body_container, NoteBodyEditFragment.newInstance(false, note.copy()))
                        .commit();
            case R.id.restore_action:
                if (note.isArchived()){
                    note.setArchived(false);
                } else if (note.isInBasket()){
                    note.setInBasket(false);
                }
                return true;

            case R.id.archive_action:
                note.setArchived(true);
                note.setInBasket(false);
                return true;

            case R.id.delete_action:

                DialogFragment dialogFragment = new DialogFragment();
                dialogFragment.setCallbacks(callbacks);
                dialogFragment.setTargetFragment(this, REQUEST_CODE);
                dialogFragment.show(requireActivity().getSupportFragmentManager(), CONFIRMATION);

                return true;

            case R.id.back_action:

                View list_layout = requireActivity().findViewById(R.id.fragment_container);
                list_layout.setVisibility(View.VISIBLE);
                NotesFragment notesFragment = NotesFragment.newInstance(MainActivity.getColumn()
                        , MainActivity.isInBasket()
                        , MainActivity.isArchived());
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, notesFragment)
                        .replace(R.id.note_body_container, NoteBodyFragment.newInstance())
                        .commit();

                return true;
            default:
                return false;
        }
//        return false;
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