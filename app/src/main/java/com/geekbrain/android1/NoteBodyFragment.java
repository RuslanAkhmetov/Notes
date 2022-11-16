package com.geekbrain.android1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private static final String TAG = " NoteBody_Fragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NoteBodyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteBodyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteBodyFragment newInstance(String param1, String param2) {
        NoteBodyFragment fragment = new NoteBodyFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_note_body, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();

        NotesViewModel model =  new ViewModelProvider(requireActivity()).get(NotesViewModel.class);

        if(arguments != null){
            try {
                Note note = model.getNote((UUID) arguments.getSerializable(NOTE_UUID));
                TextView nameText = view.findViewById(R.id.note_name);
                TextView bodyText = view.findViewById(R.id.note_body);
                TextView dateText = view.findViewById(R.id.note_date);
                nameText.setText(note.getName());
                bodyText.setText(note.getBody());
                dateText.setText(note.getNoteDate().toString());
            } catch (Exception e) {
                Log.i(TAG, "Can't make  NoteBodyFragment");
            }
        }


    }

    public static NoteBodyFragment newInstance(UUID uuid){
        NoteBodyFragment fragment = new NoteBodyFragment();
        Bundle args = new Bundle();
        args.putSerializable(NOTE_UUID, uuid);
        fragment.setArguments(args);
        return fragment;
    }



}