package com.example.dcrelling.newsreader.adaptor;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.dcrelling.newsreader.R;
import com.example.dcrelling.newsreader.service.response.ArticleSearchResponse;

/**
 * Created by dcrelling on 8/12/16.
 */

public class ArticleAdapter extends ArrayAdapter<ArticleSearchResponse.Article>
{
  public ArticleAdapter(Context context, int resource, List<ArticleSearchResponse.Article> objects)
  {
    super(context, resource, objects);
  }


  @NonNull
  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
    ArticleSearchResponse.Article article = getItem(position);
    if (convertView == null)
    {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_list_item, parent, false);
    }
    TextView headline = (TextView) convertView.findViewById(R.id.headline);
    headline.setText(article.getHeadline().getMain());
    return convertView;

  }
}
