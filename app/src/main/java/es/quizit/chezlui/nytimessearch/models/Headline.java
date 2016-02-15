package es.quizit.chezlui.nytimessearch.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by chezlui on 14/02/16.
 */
@Parcel
public class Headline {

    @SerializedName("main")
    public String main;

    /**
     * No args constructor for use in serialization
     */
    public Headline() {
    }
}
