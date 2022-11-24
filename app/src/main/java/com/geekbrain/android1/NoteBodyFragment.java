package com.geekbrain.android1;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.geekbrain.android1.viewmodel.NotesViewModel;

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
//                Note note = model.getNote(uuidFragment);
                model.setCurrentNote(note);
                Log.i(TAG, "Fragment: " + note.getName());
                view.setBackgroundColor(note.getBackColor());
                TextView nameText = view.findViewById(R.id.note_name);
                TextView bodyText = view.findViewById(R.id.note_body);
                TextView dateText = view.findViewById(R.id.note_date);
                ImageButton paletteButton = view.findViewById(R.id.palette_button);
                ImageButton editButton = view.findViewById(R.id.edit_button);
                ImageButton backButton = view.findViewById(R.id.button_back);

                nameText.setText(note.getName());
                bodyText.setText(note.getBody());
                bodyText.setBackground(Drawable.createFromPath("@drawable/frame_border"));
                dateText.setText(note.getNoteDate().toString());
                paletteButton.setOnClickListener(view1 -> {
                    showPalette();
                    view1.invalidate();
                });
                editButton.setOnClickListener(v -> {
                    Toast.makeText(requireActivity(), getString(R.string.EditToastText), Toast.LENGTH_SHORT).show();
                });

                if(!isLandscape()) {

                    backButton.setOnClickListener(v -> {
                        requireActivity().onBackPressed();
                    });
                } else  {
                    backButton.setVisibility(View.GONE);
                }
            } else {
                Log.i(TAG, "Can't make  NoteBodyFragment");
            }
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }

    }

    private void showPalette() {
        PaletteFragment paletteFragment = PaletteFragment.newInstance();
        Log.i(TAG, "Button palette was clicked " + paletteFragment.toString());
        Log.i(TAG, "BackColor:" + note.getBackColor());

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.note_body_container, paletteFragment)
                .addToBackStack("")
                .commit();
    }

    private boolean isLandscape(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

}