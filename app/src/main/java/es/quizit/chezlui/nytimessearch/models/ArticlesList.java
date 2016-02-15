package es.quizit.chezlui.nytimessearch.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chezlui on 14/02/16.
 */
@Parcel
public class ArticlesList {

    public List<ArticleGSON> articlesList;

    // public constructor is necessary for collections
    public ArticlesList() {
        articlesList = new ArrayList<ArticleGSON>();
    }

    public int size() {
        return articlesList.size();
    }
}
