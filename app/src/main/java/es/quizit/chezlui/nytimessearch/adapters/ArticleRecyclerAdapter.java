package es.quizit.chezlui.nytimessearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.quizit.chezlui.nytimessearch.R;
import es.quizit.chezlui.nytimessearch.models.Article;

/**
 * Created by chezlui on 14/02/16.
 */
public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.ViewHolder> {
    private List<Article> mArticles;
    private Context mContext;

    public ArticleRecyclerAdapter(Context context, List<Article> articles) {
        this.mContext = context;
        this.mArticles = articles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);

        View articleView = li.inflate(R.layout.item_article_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(articleView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = mArticles.get(position);

        holder.ivImage.setImageResource(0);

        holder.tvTitle.setText(article.getHeadLine());

        String thumbNail = article.getThumbnail();
        if (!TextUtils.isEmpty(thumbNail)) {
            Glide.with(mContext).load(thumbNail).into(holder.ivImage);
        }
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivImage) ImageView ivImage;
        @Bind(R.id.tvTitle) TextView tvTitle;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }





    }
}
