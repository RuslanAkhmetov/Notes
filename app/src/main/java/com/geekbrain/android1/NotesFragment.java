package com.geekbrain.android1;

import static com.geekbrain.android1.R.*;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geekbrain.android1.ui.NotesListAdapter;
import com.geekbrain.android1.ui.OnItemClickListener;
import com.geekbrain.android1.viewmodel.NotesViewModel;

import java.util.List;
import java.util.stream.Collectors;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class NotesFragment extends Fragment {
    private final String TAG = "Notes_Fragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String Q_COLUMNS = "qColumns";
    private static final String IN_BASKET = "ib_basket";
    private static final String ARCHIVED = "archived";

    // TODO: Rename and change types of parameters
    private int columns = 1;
    private boolean inBasket;
    private boolean archived;

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param columns Parameter 1.
     * @return A new instance of fragment NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(int columns, boolean inBasket, boolean archived) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putInt(Q_COLUMNS, columns);
        args.putBoolean(IN_BASKET, inBasket);
        args.putBoolean(ARCHIVED, archived);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            columns = getArguments().getInt(Q_COLUMNS);
            inBasket = getArguments().getBoolean(IN_BASKET);
            archived = getArguments().getBoolean(ARCHIVED);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(layout.recycler_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(id.notes_recycler_view);
        NotesViewModel model = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.Factory.from(NotesViewModel.initializer)).get(NotesViewModel.class);

        if (savedInstanceState == null) {
            model.initNotes().observe(requireActivity(),
                    notes -> initRecyclerView(recyclerView,
                            notes.stream().filter(note -> {
                                return note.isInBasket() == inBasket && note.isArchived() == archived;
                            }).collect(Collectors.toList())
                            ,columns));
        }

    }

    private void initRecyclerView(RecyclerView recyclerView, List<Note>notes, int columns){
        if (columns == 1){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);

        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);
        }

        NotesListAdapter notesListAdapter = new NotesListAdapter(notes);
        recyclerView.setAdapter(notesListAdapter);

        notesListAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                showNote(view, position);
            }

            @Override
            public void onItemClick(View view, Note note) {
                showNote(view, note);
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }


    private void fragmentInit(ViewGroup parent, List<Note> notes) {
        LayoutInflater layoutInflater = getLayoutInflater();
        parent.removeAllViews();
        for (Note note : notes) {
            View view = layoutInflater.inflate(layout.list_item_note, null, false);
            if (note.getBackColor() == getResources().getColor(R.color.teal_700, null)) {
                Log.i(TAG, "fragmentInit: note.getBackColor()");
                view.setBackground(ContextCompat.getDrawable(requireActivity(), drawable.frame_border_teal_700));
            } else if  (note.getBackColor() == getResources().getColor(color.purple_200, null)) {
                view.setBackground(ContextCompat.getDrawable(requireActivity(), drawable.frame_border_purple_200));
            } else {
                view.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.frame_border)); // Drawable.createFromPath("@drawable/frame_border"));
            }

            TextView nName = view.findViewById(id.edit_note_name);
            TextView nDate = view.findViewById(id.note_date);
            nName.setText(note.getName());
            nDate.setText(note.getNoteDate().toString());

            view.setOnClickListener(v -> showNote(v, note));
            parent.addView(view);
        }

    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void showNote(View view, int index) {
        NotesViewModel model = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(NotesViewModel.initializer)).get(NotesViewModel.class);
        Note note = model.getNote(index);
        showNote(view, note);
    }

    private void showNote(View view, Note note) {
        if (isLandscape()) {
            showLandNote(view, note);
        } else {
            showPortNode(view, note);
        }
    }

    private void showLandNote(View view, Note note) {
        NoteBodyFragment noteBodyFragment = NoteBodyFragment.newInstance(note);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(id.note_body_container, noteBodyFragment)
//                .addToBackStack("")
                .commit();
    }

    private void showPortNode(View view, Note note) {
        NoteBodyFragment noteBodyFragment = NoteBodyFragment.newInstance(note);
        View list_layout = requireActivity().findViewById(id.fragment_container);
        list_layout.setVisibility(View.GONE);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(id.note_body_container, noteBodyFragment)
//                .addToBackStack("")
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }



    public void fragmentInit() {
        NotesViewModel model = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.Factory.from(NotesViewModel.initializer)).get(NotesViewModel.class);
        model.initNotes().observe(requireActivity(),
                    notes -> fragmentInit((ViewGroup) requireActivity().findViewById(id.fragment_container_view_tag), notes));
    }
}