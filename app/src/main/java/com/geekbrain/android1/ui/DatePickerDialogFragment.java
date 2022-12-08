package com.geekbrain.android1.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.geekbrain.android1.NoteBodyEditFragment;
import com.geekbrain.android1.R;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatePickerDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePickerDialogFragment extends  DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DATE = "date";
    private static final String TAG = "DatePickerDialogFragment";


    // TODO: Rename and change types of parameters
    private LocalDate mdate;

    public interface CallbacksDate {
        void onDateSelected(LocalDate date);
    }

    public DatePickerDialogFragment(LocalDate noteDate) {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static DatePickerDialogFragment newInstance(LocalDate date) {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment(date);
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mdate = (LocalDate) getArguments().getSerializable(ARG_DATE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        if (savedInstanceState != null) {
            mdate = (LocalDate) savedInstanceState.getSerializable(ARG_DATE);
            calendar.setTime(Date.from(mdate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        int initialYear = calendar.get(Calendar.YEAR);
        int initialMonth = calendar.get(Calendar.MONTH);
        int initialDay = calendar.get(Calendar.DAY_OF_MONTH);
        android.app.DatePickerDialog.OnDateSetListener dateSetListener = new android.app.DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                LocalDate result = new GregorianCalendar(year, month, day).toZonedDateTime().toLocalDate();
                Log.i(TAG, "onDateSet: " + result.toString());
                ((NoteBodyEditFragment) getTargetFragment()).onDateSelected(result);
            }
        };
        return new DatePickerDialog(requireContext(),
                dateSetListener,
                initialYear,
                initialMonth,
                initialDay
        );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_picker_dialog, container, false);
    }
}