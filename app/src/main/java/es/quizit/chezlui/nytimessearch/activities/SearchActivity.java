package es.quizit.chezlui.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import es.quizit.chezlui.nytimessearch.ArticleArrayAdapter;
import es.quizit.chezlui.nytimessearch.R;
import es.quizit.chezlui.nytimessearch.Utility;
import es.quizit.chezlui.nytimessearch.models.Article;
import es.quizit.chezlui.nytimessearch.models.Filter;

public class SearchActivity extends AppCompatActivity {
    @Bind(R.id.etQuery)
    EditText etQuery;
    @Bind(R.id.btnSearch)
    Button btnSearch;
    @Bind(R.id.gvResults)
    GridView gvResults;

    ArrayList<Article> articles;
    ArticleArrayAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        articles = new ArrayList<>();
        articleAdapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(articleAdapter);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);

                Article article = articles.get(position);

                intent.putExtra("article", article);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter_search) {
            showFilgerDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onArticleSearch(View view) {
        if (!Utility.isOnline()) {
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show();
            return;
        }

        articleAdapter.clear();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = getUrlParams();
        Log.d("URL", url + params.toString());
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    Log.d("DEBUG", articleJsonResults.toString());
                    articleAdapter.addAll(Article.fromJsonArray(articleJsonResults));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("JSONError", errorResponse.toString());
                Toast.makeText(getBaseContext(), "Try another search", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showFilgerDialog() {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivity(intent);
    }

    private RequestParams getUrlParams() {
        RequestParams params = new RequestParams();

        String query = etQuery.getText().toString();

        params.put("api-key", "08e74226222237c1d9dd21876df2f25b:2:74376421");
        params.put("page", 0);
        params.put("q", query);

        Filter filter = Filter.getFilters(this);
        Iterator<String> it = filter.getDeskValues().iterator();

        String news_desk;
        if (it.hasNext()) {
            news_desk = "news_desk:(";
            while (it.hasNext()) {
                news_desk += "\"" + it.next() + "\" ";
            }
            news_desk += ")";
            params.put("fq", news_desk);
        }

        if (filter.getBeginDate() != "") {
            params.put("begin_date", filter.getBeginDate());
        }

        if (filter.getSortOrder() != "") {
            params.put("sort", filter.getSortOrder());
        }

        return params;
    }
}
