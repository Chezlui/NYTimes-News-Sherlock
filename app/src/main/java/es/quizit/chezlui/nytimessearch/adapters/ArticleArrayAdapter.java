package es.quizit.chezlui.nytimessearch.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import es.quizit.chezlui.nytimessearch.R;
import es.quizit.chezlui.nytimessearch.models.ArticleGSON;

/**
 * Created by chezlui on 11/02/16.
 */
public class ArticleArrayAdapter extends ArrayAdapter<ArticleGSON> {
    public ArticleArrayAdapter(Context context, List<ArticleGSON> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArticleGSON article = getItem(position);

        if(convertView == null)  {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_article_with_image_result, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivImage);
        imageView.setImageResource(0);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(article.getHeadLine());

        String thumbNail = article.getThumbnail();
        if (!TextUtils.isEmpty(thumbNail)) {
            Glide.with(getContext()).load(thumbNail).into(imageView);
        }

        return convertView;
    }
}
