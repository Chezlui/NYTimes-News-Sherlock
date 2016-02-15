package es.quizit.chezlui.nytimessearch.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.quizit.chezlui.nytimessearch.R;

/**
 * Created by chezlui on 12/02/16.
 */
public class FilterDialog extends DialogFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Bind(R.id.etDatePicker) EditText etDatePicker;
    @Bind(R.id.spnSortOrder) Spinner spnSortOrder;
    @Bind(R.id.cbArts) CheckBox cbArts;
    @Bind(R.id.cbFashionStyle) CheckBox cbFashionStyle;
    @Bind(R.id.cbSports) CheckBox cbSports;

    public FilterDialog() {

    }

    public static FilterDialog newInstance() {
        FilterDialog filterDialog = new FilterDialog();
        return filterDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_filter, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle(R.string.title_activity_filter);
        ButterKnife.bind(this, view);
        drawFilters();
        setListeners();
    }

    private void showDatePicker() {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        MyDatePickerDialog myDatePickerDialog =  new MyDatePickerDialog(getActivity(), null, year, month, day);
        myDatePickerDialog.show();
    }

    /** Detect if a sharedPreferences has been changed and if it is date, go ahead an update the editText */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        if (TextUtils.equals(key, "begin_date")) {
            etDatePicker.setText(sp.getString("begin_date", ""));
        }
    }

    private void drawFilters() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        etDatePicker.setText(sp.getString("begin_date", ""));
        spnSortOrder.setSelection(sp.getInt("sort_order", 1));
        cbArts.setChecked(sp.getBoolean("arts", false));
        cbFashionStyle.setChecked(sp.getBoolean("fashion_style", false));
        cbSports.setChecked(sp.getBoolean("sports", false));
    }

    private void setListeners() {

        etDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        spnSortOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(parent.getContext());
                sp.edit().putInt("sort_order", position).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cbArts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                sp.edit().putBoolean("arts", checkBox.isChecked()).apply();
            }
        });

        cbFashionStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                sp.edit().putBoolean("fashion_style", checkBox.isChecked()).apply();
            }
        });

        cbSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                sp.edit().putBoolean("sports", checkBox.isChecked()).apply();
            }
        });

        // Set a listener for sharedPreferences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        sp.registerOnSharedPreferenceChangeListener(this);
    }
}
