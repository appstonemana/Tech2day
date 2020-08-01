package com.andycraft.tech2day.pagerAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import com.andycraft.tech2day.R;
import com.andycraft.tech2day.WebActivity;
import com.andycraft.tech2day.models.GoogleNewsCell;
import com.andycraft.tech2day.utils.CustomTabs;

import java.util.ArrayList;
import java.util.Locale;

public class NewsDetails2Activity extends AppCompatActivity implements CustomPagerAdapter.OnPagerOptionListener {


    int position;
    private Palette posterPalette;
    private Palette.Swatch swatch;
    private Activity activity;

    private TextToSpeech.OnInitListener speachListener;
    private TextToSpeech textToSpeech;
    private ArrayList<GoogleNewsCell> newsCellArrayList;

    public Palette getPosterPalette() {
        return posterPalette;
    }

    public void setPosterPalette(Palette posterPalette) {
        this.posterPalette = posterPalette;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details2);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        newsCellArrayList = getIntent().getParcelableArrayListExtra("EXTRA_DATA");
        int pos = getIntent().getIntExtra("EXTRA_POS", -1);
        boolean isDarkMode = getIntent().getBooleanExtra("EXTRA_MODE",false);

        ViewPager pager = findViewById(R.id.viewpager);

        CustomPagerAdapter adapter = new CustomPagerAdapter(this, newsCellArrayList);
        adapter.setOnPagerOptionListener(this);
        adapter.setDarkMode(isDarkMode);
        pager.setAdapter(adapter/*new CustomPagerAdapter(this, newsCellArrayList)*/);
        pager.setCurrentItem(pos, true);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setUpInfoBackgroundColor(palette);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (textToSpeech != null) {
                    textToSpeech.stop();
                    textToSpeech.shutdown();
                }
            }
        });
    }

    private void setTransparentStatusBar(int rgb) {
//        color = rgb;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

//                    String hexColor = String.format("#%06X", (0xFFFFFF & color));

            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(rgb);


        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            // clear FLAG_TRANSLUCENT_STATUS flag:
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(rgb);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private Palette palette;
    private void setUpInfoBackgroundColor(Palette palette) {
        swatch = palette.getDominantSwatch();
        if (swatch != null) {
            setTransparentStatusBar(swatch.getRgb());
        }
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

    @Override
    protected void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onSpeek(int position, String tagValue) {
        speachListener.onInit(TextToSpeech.SUCCESS);
        textToSpeech.speak(newsCellArrayList.get(position).getTitle(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onShare(int position, String url) {
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, newsCellArrayList.get(position).getUrl());
        startActivity(Intent.createChooser(intent2, "Share via"));
    }

    @Override
    public void onOpen(int position, String webUrl) {
//        Intent webIntent = new Intent(this, WebActivity.class);
//        webIntent.putExtra("link", newsCellArrayList.get(position).getUrl());
//        startActivity(webIntent);
        CustomTabs.openTab(this,newsCellArrayList.get(position).getUrl());
    }

    @Override
    public void onGetPalette(Palette palette) {
        this.palette = palette;
    }
}
