package com.geekbrain.android1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.geekbrain.android1.ui.DatePickerDialogFragment;
import com.geekbrain.android1.viewmodel.NotesViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteBodyEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteBodyEditFragment extends Fragment implements DatePickerDialogFragment.CallbacksDate {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ADD_NEW = "add_new";
    private static final String TAG = "Note_Body_Edit";
    private static final String NOTE = "Note_Body_Edit_Note";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;


    // TODO: Rename and change types of parameters
    private boolean addNew;
    private Note note;


    public NoteBodyEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteBodyEditFragment newInstance(boolean addNote, Note note) {
        NoteBodyEditFragment fragment = new NoteBodyEditFragment();
        Bundle args = new Bundle();
        args.putBoolean(ADD_NEW, addNote);
        args.putParcelable(NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            addNew = getArguments().getBoolean(ADD_NEW);
            note = getArguments().getParcelable(NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_note_body_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBodyEditFragment(view);
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.inflateMenu(R.menu.bottom_edit_navigation_menu);
        bottomNavigationView.setOnItemSelectedListener(this::onItemAction);
    }

    private void initBodyEditFragment(View view) {

        NotesViewModel model = new ViewModelProvider(getActivity(),
                ViewModelProvider.Factory.from(NotesViewModel.initializer)).get(NotesViewModel.class);
        Bundle arguments = getArguments();
        if (arguments != null) {
            note = arguments.getParcelable(NOTE);
            /*if (note.isArchived() != MainActivity.isArchived()) {
                if (model.getFirstArchived() != null)
                    note = model.getFirstArchived();
            } else if (note.isInBasket() != MainActivity.isInBasket()){
                if (model.getFirstInBasket()!= null)
                    note = model.getFirstInBasket();
            }*/ /*else {
                note = model.getFirst();
            }*/

        }

        if (note == null && !addNew) {
            note = model.getCurrentNote().copy();
        }

        try {
            if (note != null) {

                EditText nameEditText = view.findViewById(R.id.edit_note_name);
                EditText bodyEditText = view.findViewById(R.id.edit_note_body);
                TextView nameDateText = view.findViewById(R.id.edit_note_date);
                nameDateText.setOnClickListener(v -> {
                    DatePickerDialogFragment datePickerDialog = new DatePickerDialogFragment(note.getNoteDate());
                    datePickerDialog.setTargetFragment(this, REQUEST_DATE);
                    datePickerDialog.show(requireActivity().getSupportFragmentManager(), DIALOG_DATE);
                });

                if (note.getBackColor() == 0) {
                    bodyEditText.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.frame_border));
                } else if (note.getBackColor() == getResources().getColor(R.color.teal_700, null)) {
                    bodyEditText.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.frame_border_teal_700));
                } else if (note.getBackColor() == getResources().getColor(R.color.purple_200, null)) {
                    bodyEditText.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.frame_border_purple_200));
                }
                ImageButton buttonPurple = view.findViewById(R.id.button_palette_purple);
                ImageButton buttonTeal = view.findViewById(R.id.button_palette_teal);

                buttonTeal.setOnClickListener(v -> {
                    int color = getResources().getColor(R.color.teal_700, null);
                    note.setBackColor(color);
                    requireActivity().findViewById(R.id.note_body_edit_container)
                            .setBackgroundColor(note.getBackColor());
                    bodyEditText.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.frame_border_teal_700));
//                    initBodyEditFragment(requireActivity().findViewById(R.id.note_body_edit_container));
                });

                buttonPurple.setOnClickListener(v -> {
                    int color = getResources().getColor(R.color.purple_200, null);
                    note.setBackColor(color);
                    requireActivity().findViewById(R.id.note_body_edit_container)
                            .setBackgroundColor(note.getBackColor());
                    bodyEditText.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.frame_border_purple_200));
//                    initBodyEditFragment(requireActivity().findViewById(R.id.note_body_edit_container));
                });

                nameEditText.setText(note.getName());
                bodyEditText.setText(note.getBody());
                nameDateText.setText(note.getNoteDate().toString());

                nameEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        note.setName(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                bodyEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        note.setBody(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

            } else {
                Log.i(TAG, "Can't make NoteBodyFragment");
            }
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }

    public boolean onItemAction(@NonNull MenuItem item) {
        int id = item.getItemId();
        NotesViewModel model = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.Factory.from(NotesViewModel.initializer)).get(NotesViewModel.class);
        switch (id) {
            case R.id.save_action:
                Log.i(TAG, "onItemAction: ");
                if (addNew) {
                    note.setArchived(false);
                    note.setInBasket(false);
                    Log.i(TAG, "onItemAction: note name" + note.getName());
                    if (model.addNote(note) > 0) {
                        Toast.makeText(requireActivity(), getString(R.string.success_add), Toast.LENGTH_SHORT).show();
                    }
                } else if (model.editCurrentNote(note) >= 0) {
                    Toast.makeText(requireActivity(), getString(R.string.success_save), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireActivity(), getString(R.string.unsuccess_save), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.back_action:
                //updateNotesListData();

                model.updateDB();
                NotesFragment notesFragment = new NotesFragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, notesFragment)
                        .replace(R.id.note_body_container, NoteBodyFragment.newInstance(model.getCurrentNote()))
                        .commit();
                return true;
            default:
                return false;
        }
        return false;
    }

    @Override
    public void onDateSelected(LocalDate date) {
        Log.i(TAG, "onDateSelected: "+ date.toString());
        note.setNoteDate(date);
        TextView dateText = requireActivity().findViewById(R.id.edit_note_date);
        dateText.setText(date.toString());

    }
}