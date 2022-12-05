package com.geekbrain.android1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DialogFragment extends androidx.fragment.app.DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/*    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";*/
    private static final String ARG_CALLBACK = "callback";

    // TODO: Rename and change types of parameters
/*    private String mParam1;
    private String mParam2;*/
    private boolean isDelete;
    public static String TAG ="ConfirmationDeleting";
    private Callbacks callbacks;

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    public DialogFragment() {
        // Required empty public constructor
    }


    public static DialogFragment newInstance(Callbacks callback) {
        DialogFragment fragment = new DialogFragment();
/*        Bundle args = new Bundle();
        args.putSerializable(ARG_CALLBACK, callback);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        if (getArguments() != null) {
           callbacks = getArguments().getSerializable(ARG_CALLBACK);
        }*/
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.atention)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (callbacks != null) {
                            callbacks.OnPositiveButtonClicked();
                        }
                        isDelete = true;
                        Log.i(TAG, "onClick: " + isDelete);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (callbacks != null) {
                            callbacks.OnNegativeButtonClicked();
                        }
                        isDelete = false;
                        Log.i(TAG, "onClick: " + isDelete);
                    }
                })
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog2, container, false);
    }
}