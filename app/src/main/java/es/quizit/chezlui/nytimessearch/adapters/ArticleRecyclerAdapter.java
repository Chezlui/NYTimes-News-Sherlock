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
public class ArticleRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Article> mArticles;
    private Context mContext;

    private final int IMAGE = 0;
    private final int TEXT = 1;

    public ArticleRecyclerAdapter(Context context, List<Article> articles) {
        this.mContext = context;
        this.mArticles = articles;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);
        ViewHolderImage viewHolderImage;
        ViewHolderText viewHolderText;

        View articleView;
        switch (viewType) {
            case IMAGE:
                articleView = li.inflate(R.layout.item_article_with_image_result, parent, false);
                viewHolderImage = new ViewHolderImage(articleView);

                return viewHolderImage;
            case TEXT:
                articleView = li.inflate(R.layout.item_article_only_text_result, parent, false);
                viewHolderText = new ViewHolderText(articleView);

                return viewHolderText;
            default:
                articleView = li.inflate(R.layout.item_article_only_text_result, parent, false);
                viewHolderText = new ViewHolderText(articleView);

                return viewHolderText;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Article article;
        switch (holder.getItemViewType()) {
            case IMAGE:
                article = mArticles.get(position);
                ((ViewHolderImage) holder).ivImage.setImageResource(0);

                ((ViewHolderImage) holder).tvTitle.setText(article.getHeadLine());

                String thumbNail = article.getThumbnail();
                if (!TextUtils.isEmpty(thumbNail)) {
                    Glide.with(mContext).load(thumbNail).into(((ViewHolderImage) holder).ivImage);
                }
                break;
            case TEXT:
                article = mArticles.get(position);

                ((ViewHolderText) holder).tvTitle.setText(article.getHeadLine());
                break;
            default:
                article = mArticles.get(position);

                ((ViewHolderText) holder).tvTitle.setText(article.getHeadLine());

        }
    }


    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public int getItemViewType(int position) {
        Article article = mArticles.get(position);
        if (TextUtils.equals(article.getThumbnail(), "")) {
            return TEXT;
        } else {
            return IMAGE;
        }
    }

    public static class ViewHolderImage extends RecyclerView.ViewHolder {
        @Bind(R.id.ivImage)
        ImageView ivImage;
        @Bind(R.id.tvTitle)
        TextView tvTitle;

        public ViewHolderImage(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public static class ViewHolderText extends RecyclerView.ViewHolder {
        @Bind(R.id.tvTitle)
        TextView tvTitle;

        public ViewHolderText(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
