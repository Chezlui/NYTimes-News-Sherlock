package es.quizit.chezlui.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.quizit.chezlui.nytimessearch.R;

/**
 * Created by chezlui on 12/02/16.
 */
public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    @Bind(R.id.etDatePicker) EditText etDatePicker;
    @Bind(R.id.spnSortOrder) Spinner spnSortOrder;
    @Bind(R.id.cbArts) CheckBox cbArts;
    @Bind(R.id.cbFashionStyle) CheckBox cbFashionStyle;
    @Bind(R.id.cbSports) CheckBox cbSports;

    // TODO Delete all filters with an action button

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawFilters();
        setListeners();
    }

    private void showDatePicker() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        etDatePicker.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putString("begin_date", etDatePicker.getText().toString()).apply();
    }


    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Activity needs to implement this interface
            DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener) getActivity();

            // Create a new instance of TimePickerDialog and return it
            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }
    }

    private void drawFilters() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
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
    }
}
