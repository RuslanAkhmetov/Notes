package com.geekbrain.android1;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geekbrain.android1.viewmodel.NotesViewModel;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {
    private static final String NOTE_UUID = "uuid";
    private UUID uuidFragment;
    private final String TAG = "Notes_Fragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (uuidFragment!=null) {
            outState.putSerializable(NOTE_UUID, uuidFragment);
        }
        super.onSaveInstanceState(outState);
     }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null)
            uuidFragment = (UUID) savedInstanceState.getSerializable(NOTE_UUID);

        NotesViewModel model = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
        if (savedInstanceState == null) {
            model.getNotes().observe(requireActivity(),
                    notes -> fragmentInit((ViewGroup) view, notes));
        }
    }

    private void fragmentInit ( ViewGroup parent, List<Note> notes) {
        LayoutInflater layoutInflater = getLayoutInflater();
        for (Note note : notes) {
            View view = layoutInflater.inflate(R.layout.list_item_note, null,  false);
            TextView nName = view.findViewById(R.id.note_name);
            TextView nDate = view.findViewById(R.id.note_date);
            nName.setText(note.getName());
            nDate.setText(note.getNoteDate().toString());
            uuidFragment = note.getUuid();
            view.setOnClickListener(v-> showNote(v, note.getUuid()));
            parent.addView(view);
//            Log.d(TAG, "Created " + note.getName());
        }

    }

    private boolean isLandscape(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void showNote(View view, UUID uuid) {
        uuidFragment = uuid;
        if (isLandscape()) {

            showLandNote(view, uuid);
        } else {
            showPortNode(view, uuid);
        }
    }

    private void showLandNote(View view, UUID uuid) {
        NoteBodyFragment noteBodyFragment = NoteBodyFragment.newInstance(uuid);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.note_body_container, noteBodyFragment)
                .commit();
    }

    private void showPortNode(View view, UUID uuid) {
        NoteBodyFragment noteBodyFragment = NoteBodyFragment.newInstance(uuid);
        View list_layout  = requireActivity().findViewById (R.id.nested_scroll_view);
        list_layout.setVisibility(View.GONE);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.note_body_container, noteBodyFragment)
                .addToBackStack("")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

}