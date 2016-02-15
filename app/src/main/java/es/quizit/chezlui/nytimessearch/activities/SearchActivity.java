package es.quizit.chezlui.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import es.quizit.chezlui.nytimessearch.R;
import es.quizit.chezlui.nytimessearch.Utility;
import es.quizit.chezlui.nytimessearch.adapters.ArticleRecyclerAdapter;
import es.quizit.chezlui.nytimessearch.adapters.DividerItemDecoration;
import es.quizit.chezlui.nytimessearch.adapters.EndlessRecyclerViewScrollListener;
import es.quizit.chezlui.nytimessearch.adapters.ItemClickSupport;
import es.quizit.chezlui.nytimessearch.models.ArticleGSON;
import es.quizit.chezlui.nytimessearch.models.Filter;

// TODO add animation to RecyclerView
// TODO Add place holder
// TODO Hide toolbar on scroll?
// TODO si me aburro onSwipeRefreshListener
public class SearchActivity extends AppCompatActivity {
    @Bind(R.id.rvResults) RecyclerView rvResults;

    List<ArticleGSON> articles = new ArrayList<>();
    ArticleRecyclerAdapter articleAdapter;

    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        articleAdapter = new ArticleRecyclerAdapter(this, articles);


        rvResults.setAdapter(articleAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvResults.addItemDecoration(itemDecoration);

        ItemClickSupport.addTo(rvResults).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                        ArticleGSON article = articles.get(position);
                        intent.putExtra("article", Parcels.wrap(article));
                        startActivity(intent);
                    }
                }
        );

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        rvResults.setLayoutManager(staggeredGridLayoutManager);
        rvResults.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadData(page);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchActivity.this.query = query;

                // perform query here
                onArticleSearch(searchView);
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

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

        articles.clear();
        articleAdapter.notifyDataSetChanged();
        int page = 0;
        loadData(page);
    }

    private void loadData(int page) {
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = getUrlParams(page);
        Log.d("URL", url + params.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    Log.d("DEBUG", articleJsonResults.toString());

                    int currentAdapterSize = articleAdapter.getItemCount();

                    Type collectionType = new TypeToken<List<ArticleGSON>>(){}.getType();
                    Gson gson = new GsonBuilder().create();

                    articles.addAll(
                            (ArrayList<ArticleGSON>)gson.fromJson(articleJsonResults.toString(), collectionType));
//                  articles.addAll(ArticleGSON.fromJsonArray(articleJsonResults));



                    articleAdapter.notifyDataSetChanged();
                    //articleAdapter.notifyItemRangeChanged(currentAdapterSize, articles.articlesList.size() - currentAdapterSize);
                    if(articles.size() == 0) {
                        Toast.makeText(SearchActivity.this, "try another search", Toast.LENGTH_SHORT).show();
                    }
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
        FragmentManager fm = getSupportFragmentManager();
        FilterDialog filterDialog = FilterDialog.newInstance();
        filterDialog.show(fm, "Filter your search");
    }

    private RequestParams getUrlParams(int page) {
        RequestParams params = new RequestParams();

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

        if (page > 0) {
            params.put("page", page);
        }

        return params;
    }
}
