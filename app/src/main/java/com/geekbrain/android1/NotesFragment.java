package com.geekbrain.android1;

import static com.geekbrain.android1.R.*;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.geekbrain.android1.viewmodel.NotesViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class NotesFragment extends Fragment {
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*NotesViewModel model = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
        Context context = requireActivity().getApplicationContext();
        LinearLayout viewGroup = new LinearLayout(context);
        viewGroup.setOrientation(LinearLayout.VERTICAL);


        initToolbar(toolbar);
        setHasOptionsMenu(true);
        viewGroup.addView(toolbar);
        if (savedInstanceState == null) {
            model.getNotes().observe(requireActivity(),
                    notes -> layoutInit(context, viewGroup, notes));
        }
        return viewGroup;*/

        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }



    private void initToolbar(Toolbar toolbar) {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NotesViewModel model = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
        if (savedInstanceState == null) {
            model.getNotes().observe(requireActivity(),
                    notes -> fragmentInit((ViewGroup) view, notes));
        }
    }

    private void layoutInit(Context context, ViewGroup parent, List<Note> notes) {


        for (Note note : notes) {
            LinearLayout layout = new LinearLayout(context);

            TextView nName = new TextView(context);
            TextView nDate = new TextView(context);
            nName.setText(note.getName());
            nDate.setText(note.getNoteDate().toString());
            nName.setTextAppearance(style.BigText);
            nDate.setTextAppearance(style.MidText);
//            nName.setBackgroundColor(note.getBackColor());
//            nDate.setBackgroundColor(note.getBackColor());
            layout.addView(nName);
            layout.addView(nDate);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setOnClickListener(v -> showNote(v, note));
//            layout.setBackground(Drawable.createFromPath("@drawable/frame_border"));
 //           layout.setBackgroundColor(note.getBackColor());
            parent.addView(layout);
//            Log.d(TAG, "Created " + note.getName());
        }

    }

    private void fragmentInit(ViewGroup parent, List<Note> notes) {
        LayoutInflater layoutInflater = getLayoutInflater();
        parent.removeAllViews();
        for (Note note : notes) {
            View view = layoutInflater.inflate(layout.list_item_note, null, false);
            if (note.getBackColor() == getResources().getColor(R.color.teal_700, null)) {
                Log.i(TAG, "fragmentInit: note.getBackColor()");
                view.setBackground(ContextCompat.getDrawable(requireActivity(), drawable.frame_border_teal));
            } else if  (note.getBackColor() == getResources().getColor(color.purple_200, null)) {
                view.setBackground(ContextCompat.getDrawable(requireActivity(), drawable.frame_border_purple));
            } else {
                view.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.frame_border)); // Drawable.createFromPath("@drawable/frame_border"));
            }


//            view.setBackgroundResource(drawable.frame_border);
            TextView nName = view.findViewById(id.note_name);
            TextView nDate = view.findViewById(id.note_date);
            nName.setText(note.getName());
            nDate.setText(note.getNoteDate().toString());
//            nName.setBackgroundColor(note.getBackColor());
//            nDate.setBackgroundColor(note.getBackColor());


            view.setOnClickListener(v -> showNote(v, note));
//            view.setBackgroundColor(note.getBackColor());
            parent.addView(view);
//            Log.d(TAG, "Created " + note.getName());
        }

    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
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
                .add(id.note_body_container, noteBodyFragment)
                .addToBackStack("")
                .commit();
    }

    private void showPortNode(View view, Note note) {
        NoteBodyFragment noteBodyFragment = NoteBodyFragment.newInstance(note);
        View list_layout = requireActivity().findViewById(id.nested_scroll_view);
        list_layout.setVisibility(View.GONE);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(id.note_body_container, noteBodyFragment)
                .addToBackStack("")
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public void fragmentInit() {
        NotesViewModel model = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
        model.getNotes().observe(requireActivity(),
                    notes -> fragmentInit((ViewGroup) requireActivity().findViewById(id.fragment_container_view_tag), notes));
    }
}