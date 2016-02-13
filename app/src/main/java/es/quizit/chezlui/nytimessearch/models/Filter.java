package es.quizit.chezlui.nytimessearch.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.Bind;
import es.quizit.chezlui.nytimessearch.R;

/**
 * Created by chezlui on 12/02/16.
 */
public class Filter {
    @Bind(R.id.etDatePicker) EditText etDatePicker;
    @Bind(R.id.spnSortOrder) Spinner spnSortOrder;
    @Bind(R.id.cbArts) CheckBox cbArts;
    @Bind(R.id.cbFashionStyle) CheckBox cbFashionStyle;
    @Bind(R.id.cbSports) CheckBox cbSports;


    String beginDate;

    public String getBeginDate() {
        if (beginDate == "") {
            return "";
        }
        String[] dateItems = beginDate.split("/");
        return dateItems[2]
                + ((dateItems[0].length() == 1) ? "0" + dateItems[0] : dateItems[0])
                + ((dateItems[1].length() == 1) ? "0" + dateItems[1] : dateItems[1]);
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public ArrayList<String> getDeskValues() {
        return deskValues;
    }

    String sortOrder;
    ArrayList<String> deskValues;

    public Filter() {

    }

    public static Filter getFilters(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Filter filter = new Filter();
        filter.beginDate = sp.getString("begin_date", "");
        filter.sortOrder = (sp.getInt("sort_order", 1) == 0) ? "oldest" : "newest";
        filter.deskValues = new ArrayList<String>();

        boolean deskValue = sp.getBoolean("arts", false);
        if(deskValue) {
            filter.deskValues.add(DeskValues.ARTS);
        } else {
            filter.deskValues.remove(DeskValues.ARTS);
        }

        deskValue = sp.getBoolean("fashion_style", false);
        if(deskValue) {
            filter.deskValues.add(DeskValues.FASHION_AND_STYLE);
        } else {
            filter.deskValues.remove(DeskValues.FASHION_AND_STYLE);
        }

        deskValue = sp.getBoolean("sports", false);
        if(deskValue) {
            filter.deskValues.add(DeskValues.SPORTS);
        } else {
            filter.deskValues.remove(DeskValues.SPORTS);
        }

        return filter;
    }


    public final class DeskValues {

        public static final String ARTS = "Arts";
        public static final String FASHION_AND_STYLE = "Fashion & Style";
        public static final String SPORTS = "Sports";

        private DeskValues() { }
    }
}
