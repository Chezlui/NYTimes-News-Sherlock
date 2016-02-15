package es.quizit.chezlui.nytimessearch.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chezlui on 11/02/16.
 */
@Parcel
public class ArticleGSON {

    @SerializedName("web_url")
    public String webUrl;
    @SerializedName("headline")
    public Headline headline;
    @SerializedName("multimedia")
    public List<Multimedia> multimedia = new ArrayList<Multimedia>();

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadLine() {
        return headline.main;
    }

    public String getThumbnail() {
        if (multimedia.size() > 0) {
            return ("http://www.nytimes.com/" +  multimedia.get(1).url);
        } else {
            return "";
        }
    }



    // This empty constructor is for the Parcel library
    public ArticleGSON() {
    }

//    public static ArrayList<ArticleGSON> fromJsonArray(JSONArray jsonArray){
//        ArrayList<ArticleGSON> articles = new ArrayList<>();
//        for (int i = 0; i < jsonArray.length(); i++) {
//            try {
//                articles.add(new ArticleGSON(jsonArray.getJSONObject(i)));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return articles;
//    }
}
