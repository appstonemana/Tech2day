package com.andycraft.tech2day;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.andycraft.tech2day.adapters.NewsListAdapter;
import com.andycraft.tech2day.helpers.ApiCaller;
import com.andycraft.tech2day.helpers.ApiTaskDelegate;
import com.andycraft.tech2day.models.GoogleNewsCell;
import com.andycraft.tech2day.pagerAdapter.NewsDetails2Activity;
import com.andycraft.tech2day.utils.SpacesItemDecoration;
import com.andycraft.tech2day.webservices.ApiGetNews;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends BaseActivity implements NewsListAdapter.OnNewsClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    boolean isDarkMode;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private RecyclerView mRvNewsList;
    private ArrayList<GoogleNewsCell> newsList;
    private SpacesItemDecoration itemDecoration;
    private TextToSpeech.OnInitListener speachListener;
    private TextToSpeech textToSpeech;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar mProgressBar;
    private NewsListAdapter adapter;
    private Toolbar toolbar;
    private TextView mTvTitle;
    private ConstraintLayout mConstRoot;

    private MenuItem mItem;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("Theme", MODE_PRIVATE);

        editor = preferences.edit();

        //Initially dark mode false
        isDarkMode = preferences.getBoolean("isBlack", false);
        if (isDarkMode) {
            setDarkTheme();
        } else {
            setLightTheme();
        }

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        mTvTitle = findViewById(R.id.tv_title);
        mConstRoot = findViewById(R.id.root);

        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        darkModeSetup();


        mRvNewsList = findViewById(R.id.rv_news_list);
        mRvNewsList.setHasFixedSize(true);
        mRvNewsList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        setItemDecoration(this);

        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        newsList = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNews(false);
            }
        });

        fetchNews(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        textToSpeech = new TextToSpeech(this, speachListener);

        speachListener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        };

    }

    private void fetchNews(boolean showLoader) {
        if (showLoader) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        new ApiGetNews(this)
                .setApiKey("6cd4cc56897c46e7be3b6f1bfe8c9953")
                .setSources("google-news-in")
                .makeCall(new ApiTaskDelegate() {
                    @Override
                    public void onTaskCompleted(ApiCaller apiCaller, String message) {
                        mProgressBar.setVisibility(View.GONE);
                        ApiGetNews news = (ApiGetNews) apiCaller;
                        setupRecyclerView(news.getAllNews());
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onErrorOccured(ApiCaller apiCaller, String error) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void setupRecyclerView(ArrayList<GoogleNewsCell> allNews) {
        newsList = allNews;
        adapter = new NewsListAdapter(this, allNews, speachListener);
        adapter.setOnNewsClickListener(this);
        adapter.setDarkMode(isDarkMode);
        mRvNewsList.setAdapter(adapter);
    }

    @Override
    public void onNewsClick(int position, View view, boolean isBtn, String tag) {
        if (isBtn) {
            textToSpeech.speak(newsList.get(position).getTitle(), TextToSpeech.QUEUE_FLUSH, null);
        } else {
            Intent detailIntent = new Intent(MainActivity.this, NewsDetails2Activity.class);
            detailIntent.putExtra("EXTRA_IMG_URL", newsList.get(position).getUrlToImage());
            detailIntent.putExtra("EXTRA_TITLE", newsList.get(position).getTitle());
            detailIntent.putExtra("EXTRA_DESC", newsList.get(position).getDescription());
            detailIntent.putExtra("EXTRA_POS", position);
            detailIntent.putExtra("EXTRA_MODE", isDarkMode);
            detailIntent.putParcelableArrayListExtra("EXTRA_DATA", newsList);
//            ActivityOptionsCompat options = ActivityOptionsCompat.
//                    makeSceneTransitionAnimation(this, view, "profile");
            startActivity(detailIntent/*, options.toBundle()*/);
        }

    }

    private void setItemDecoration(Context context) {
        int spacing = getStyleGridSpacing(context);
        mRvNewsList.removeItemDecoration(itemDecoration);
        itemDecoration = new SpacesItemDecoration(spacing);
        mRvNewsList.addItemDecoration(itemDecoration);
    }

    public int getStyleGridSpacing(Context context) {
        Resources res = null;
        if (context != null)
            res = context.getResources();
        assert res != null;
        int spacing = (int) res.getDimension(R.dimen.spacing_16);

        mRvNewsList.setPadding(0, 0, 0, 0);
        mRvNewsList.setPadding(spacing / 2,
                spacing / 2,
                spacing / 2,
                spacing / 2);
        return spacing;

    }

    @Override
    protected void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isDarkMode) {
            getMenuInflater().inflate(R.menu.menu_dark, menu);
        } else {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mItem = item;

        switch (item.getItemId()) {

            case R.id.navigation_dark:

                if (preferences.getBoolean("isBlack", false)) {
                    editor.putBoolean("isBlack", false);
                    editor.apply();
                    isDarkMode = false;
                    setLightTheme();
                    adapter.setDarkMode(false);
                    adapter.notifyDataSetChanged();
                    mItem.setIcon(R.drawable.ic_lightbulb_outline_black_24dp);
                    refreshActivity();
                } else {
                    editor.putBoolean("isBlack", true);
                    editor.apply();
                    isDarkMode = true;
                    setDarkTheme();
                    adapter.setDarkMode(true);
                    adapter.notifyDataSetChanged();
                    mItem.setIcon(R.drawable.ic_lightbulb_outline_white_24dp);
                    refreshActivity();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setLightTheme() {
        setTheme(R.style.AppTheme);
    }

    private void setDarkTheme() {
        setTheme(R.style.AppThemeDark);
    }

    private void refreshActivity() {

        adapter.setDarkMode(isDarkMode);
        adapter.notifyDataSetChanged();
        darkModeSetup();
    }

    private void darkModeSetup() {
        if (isDarkMode) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.black));
            mTvTitle.setTextColor(getResources().getColor(R.color.white));
            mConstRoot.setBackgroundColor(getResources().getColor(R.color.black));

        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.white));
            mTvTitle.setTextColor(getResources().getColor(R.color.black));
            mConstRoot.setBackgroundColor(getResources().getColor(R.color.white));

        }
    }

}
