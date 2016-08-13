package com.example.dcrelling.newsreader.adaptor;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    return super.getView(position, convertView, parent);
  }
}
