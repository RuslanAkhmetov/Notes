package com.geekbrain.android1;

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
import android.widget.ImageButton;

import com.geekbrain.android1.viewmodel.NotesViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaletteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaletteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "PALETTE_FRAGMENT";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PaletteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//    * @param param1 Parameter 1.
//     * @param param2 Parameter 2.*/
//      @return A new instance of fragment PaletteFragment.

    // TODO: Rename and change types and number of parameters
    public static PaletteFragment newInstance() {
        PaletteFragment fragment = new PaletteFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        Log.i(TAG, "New Instance");

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
        return inflater.inflate(R.layout.fragment_palette, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NotesViewModel model = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);

        Note note = model.getCurrentNote() == null ? model.getFirst()
                : model.getCurrentNote();

        Log.i(TAG, "onViewCreated BackColor" + note.getBackColor());

        ImageButton buttonPurple = view.findViewById(R.id.button_palette_purple);
        ImageButton buttonTeal = view.findViewById(R.id.button_palette_teal);

        buttonTeal.setOnClickListener(v ->{
                    int color = getResources().getColor(R.color.teal_700, null);
                    setNoteBackColor(v, color, note);
                });

        buttonPurple.setOnClickListener(v ->{
                int color = getResources().getColor(R.color.purple_200, null);
                setNoteBackColor(v, color, note);
        });
    }

    private void setNoteBackColor(View v, int color, Note note) {
        Log.i(TAG, "BackColor  required:" + color );
        note.setBackColor(color);
        Log.i(TAG, "BackColor:" + note.getBackColor());
    }
}