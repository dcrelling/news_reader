package com.example.dcrelling.newsreader.service.response;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dcrelling on 8/11/16.
 */

public class ArticleSearchResponse
{
  @SerializedName("response")
  public Response _response;


  public Response getResponse()
  {
    return this._response;
  }


  public class Response
  {

    @SerializedName("docs")
    List<Article> _articleList;


    public Response()
    {
      _articleList = new ArrayList<>();
    }


    public List<Article> getArticleList()
    {
      return _articleList;
    }

  }


  public class Article
  {
    @SerializedName("headline")
    Headline _headline;


    public Headline getHeadline()
    {
      return _headline;
    }


    @Override
    public String toString()
    {
      return this._headline.getMain();
    }
  }


  public class Headline
  {
    @SerializedName("main")
    String _main;


    public String getMain()
    {
      return _main;
    }
  }
}
