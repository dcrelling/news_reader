package com.example.dcrelling.newsreader.ui;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.example.dcrelling.newsreader.R;
import com.example.dcrelling.newsreader.adaptor.ArticleAdapter;
import com.example.dcrelling.newsreader.service.NewYorkTimesService;
import com.example.dcrelling.newsreader.service.factory.ServiceFactory;
import com.example.dcrelling.newsreader.service.response.ArticleSearchResponse;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity
{

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide
   * fragments for each of the sections. We use a
   * {@link FragmentPagerAdapter} derivative, which will keep every
   * loaded fragment in memory. If this becomes too memory intensive, it
   * may be best to switch to a
   * {@link android.support.v4.app.FragmentStatePagerAdapter}.
   */
  private SectionsPagerAdapter mSectionsPagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  private ViewPager mViewPager;

  private static NewYorkTimesService mNewYorkTimesService;


  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.article_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.container);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(mViewPager);

    initService();

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {

      }
    });


  }


  private void initService()
  {
    mNewYorkTimesService = ServiceFactory.getInstance().createService(NewYorkTimesService.class, NewYorkTimesService.BASE_URL);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }


  /**
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
   * one of the sections/tabs/pages.
   */
  public class SectionsPagerAdapter extends FragmentPagerAdapter
  {

    public SectionsPagerAdapter(FragmentManager fm)
    {
      super(fm);
    }


    @Override
    public Fragment getItem(int position)
    {
      // getItem is called to instantiate the fragment for the given page.
      // Return a PlaceholderFragment (defined as a static inner class below).
      return ArrayListFragment.newInstance(position + 1);
    }


    @Override
    public int getCount()
    {
      // Show 3 total pages.
      return 3;
    }


    @Override
    public CharSequence getPageTitle(int position)
    {
      switch (position)
      {
        case 0:
          return "SECTION 1";
        case 1:
          return "SECTION 2";
        case 2:
          return "SECTION 3";
      }
      return null;
    }
  }


  public static class ArrayListFragment extends ListFragment
  {

    int mNum;

    private static final String ARG_SECTION_NUMBER = "section_number";


    static ArrayListFragment newInstance(int sectionNumber)
    {
      ArrayListFragment arrayListFragment = new ArrayListFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      arrayListFragment.setArguments(args);
      return arrayListFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
      super.onCreate(savedInstanceState);
      mNum = getArguments() != null
             ? getArguments().getInt("num")
             : 1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
      super.onCreateView(inflater, container, savedInstanceState);
      View rootView = inflater.inflate(R.layout.fragment_pager_list, container, false);
      TextView textView = (TextView) rootView.findViewById(R.id.section_label);
      textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
      return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
      super.onActivityCreated(savedInstanceState);
      Map<String, String> params = new HashMap<String, String>();
      params.put("api-key", NewYorkTimesService.API.SEARCH.getApiKey());
      params.put("begin_date", "20160810");

      mNewYorkTimesService.searchArticles(params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ArticleSearchResponse>()
      {
        @Override
        public void call(ArticleSearchResponse articleSearchResponse)
        {
          Log.d("got here", "got here");
          setListAdapter(new ArticleAdapter(getActivity(), R.layout.article_list_item, articleSearchResponse.getResponse().getArticleList()));
        }
      });

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
      super.onListItemClick(l, v, position, id);
      Log.i("FragmentList", "Item clicked: " + id);
    }
  }
}
