package es.quizit.chezlui.nytimessearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by chezlui on 11/02/16.
 */
@Parcel
public class Article {
    public String webUrl;
    public String headLine;
    public String thumbnail;

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    // This empty constructor is for the Parcel library
    public Article() {}

    public Article(JSONObject jsonObject) {
        try {
            this.webUrl = jsonObject.has("web_url") ? jsonObject.getString("web_url") : "";
            this.headLine = jsonObject.has("headline") ?
                    ( jsonObject.getJSONObject("headline").has("main") ?
                            jsonObject.getJSONObject("headline").getString("main")
                            : "" )
                    : "";

            JSONArray multimedia = jsonObject.has("multimedia") ? jsonObject.getJSONArray("multimedia") : null;
            if(multimedia != null && multimedia.length() > 0) {
                this.thumbnail = multimedia.getJSONObject(0).has("url") ?
                        "http://www.nytimes.com/" + multimedia.getJSONObject(0).getString("url")
                        : "";
            } else {
                this.thumbnail = "";
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Article> fromJsonArray(JSONArray jsonArray){
        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                articles.add(new Article(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return articles;
    }

}
