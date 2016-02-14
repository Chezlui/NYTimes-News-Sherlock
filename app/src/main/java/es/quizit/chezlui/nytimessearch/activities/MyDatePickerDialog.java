package es.quizit.chezlui.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.DatePicker;

/**
 * Created by chezlui on 13/02/16.
 */
public class MyDatePickerDialog extends DatePickerDialog {
    public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        String date = new String((month + 1) + "/" + day + "/" + year);
        sp.edit().putString("begin_date", date).apply();

    }
}
