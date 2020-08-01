package com.andycraft.tech2day;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.palette.graphics.Palette;

import com.andycraft.tech2day.models.GoogleNewsCell;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

//import com.andycraft.tech2day.adapters.FragmentPagerAdapter;

public class NewsDetailsActivity extends AppCompatActivity {


    ArrayList<GoogleNewsCell> googleNewsCells;
    private Palette posterPalette;
    private Palette.Swatch swatch;

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
        setContentView(R.layout.activity_news_details);


        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        ImageView mIvImage = findViewById(R.id.iv_image);
        TextView mTvTitle = findViewById(R.id.tv_title);
        TextView mTvDesc = findViewById(R.id.tv_desc);
        String img_url = getIntent().getStringExtra("EXTRA_IMG_URL");
        String title = getIntent().getStringExtra("EXTRA_TITLE");
        String desc = getIntent().getStringExtra("EXTRA_DESC");


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher_background);
        requestOptions.error(R.drawable.ic_launcher_background);


        Glide.with(this)
                .asBitmap()
                .load(img_url)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        if (getPosterPalette() != null) {
                            setUpInfoBackgroundColor(getPosterPalette());

                        } else {
                            //Bitmap bitmap = ((BitmapDrawable) holder.imageView.getDrawable()).getBitmap();

                            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    setPosterPalette(palette);
                                    setUpInfoBackgroundColor(palette);
                                }
                            });
                        }
                        return false;
                    }
                })
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(mIvImage);

        mTvTitle.setText(title);
        mTvDesc.setText(desc);

/**
 * Picasso ::: working fine
 * */
//        Picasso.get()
//                .load(img_url)
//                .into(new Target() {
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        mIvImage.setImageBitmap(bitmap);
//                        Palette.from(bitmap)
//                                .generate(new Palette.PaletteAsyncListener() {
//                                    @Override
//                                    public void onGenerated(Palette palette) {
//                                        Palette.Swatch textSwatch = palette.getVibrantSwatch();
//                                        if (textSwatch == null) {
////                                            Toast.makeText(NewsDetailsActivity.this, "Null swatch :(", Toast.LENGTH_SHORT).show();
//                                            return;
//                                        }
////                                        backgroundGroup.setBackgroundColor(textSwatch.getRgb());
////                                        titleColorText.setTextColor(textSwatch.getTitleTextColor());
////                                        bodyColorText.setTextColor(textSwatch.getBodyTextColor());
//
//                                        setTransparentStatusBar(textSwatch.getRgb());
//
//
//                                        mTvTitle.setText(title);
//                                        mTvDesc.setText(desc);
//                                    }
//
//                                    private void setTransparentStatusBar(int rgb) {
//                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////
////                    String hexColor = String.format("#%06X", (0xFFFFFF & color));
//
//                                            Window window = getWindow();
//                                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                                            window.setStatusBarColor(rgb);
//
//
//                                        } else {
//                                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//
//                                            // clear FLAG_TRANSLUCENT_STATUS flag:
//                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                                        }
//                                    }
//                                });
//
//
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                    }
//                });


//         googleNewsCells = getIntent().getParcelableArrayListExtra("EXTRA_DATA");
//
//        for (GoogleNewsCell cell: googleNewsCells ) {
//
//        }
//
//        ViewPager pager = (ViewPager) findViewById(R.id.pager);
//        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));


    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.navigation_share:
//                    selectedFragment = new HomeFragment();
//                    mToolbar.setTitle("Home");
                    break;
                case R.id.navigation_link:
                    Intent choserIntent = new Intent(Intent.ACTION_SEND);
                    Intent.createChooser(choserIntent,"Open link");
                    break;
                default:
                    break;
            }
            return true;
        }
    };






    private void setTransparentStatusBar(int rgb) {
//        color = rgb;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
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
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpInfoBackgroundColor(Palette palette) {
        swatch = palette.getDominantSwatch();
        if (swatch != null) {
            setTransparentStatusBar(swatch.getRgb());
        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            GoogleNewsCell cell = googleNewsCells.get(position);
            return FragmentViewPager.newInstance(cell.getTitle(),
                    cell.getDescription(), cell.getUrlToImage());
        }

        @Override
        public int getCount() {
            return googleNewsCells.size();
        }
    }
}