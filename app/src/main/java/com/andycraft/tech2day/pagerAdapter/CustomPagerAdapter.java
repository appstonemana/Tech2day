package com.andycraft.tech2day.pagerAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.PagerAdapter;

import com.andycraft.tech2day.R;
import com.andycraft.tech2day.models.GoogleNewsCell;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

/**
 * Created by Manabendu on 2019-07-17
 */
public class CustomPagerAdapter extends PagerAdapter {

    public static final String TAG = CustomPagerAdapter.class.getSimpleName();

    OnPagerOptionListener onPagerOptionListener;
    private Context mContext;
    private ArrayList<GoogleNewsCell> newsList;
    private Palette posterPalette;
    private Palette.Swatch swatch;

    private boolean isDarkMode;


    //init views
    private TextView mTvTitle, mTvDesc;
    private ConstraintLayout mConstRoot;

    public CustomPagerAdapter(Context context, ArrayList<GoogleNewsCell> newsList) {
        mContext = context;
        this.newsList = newsList;

    }

    public void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
    }

    public Palette getPosterPalette() {
        return posterPalette;
    }

    public void setPosterPalette(Palette posterPalette) {
        this.posterPalette = posterPalette;

        swatch = posterPalette.getDominantSwatch();
        if (swatch != null) {
        }
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint("CheckResult")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.pager_view, container, false);


        ImageView mIvImage = layout.findViewById(R.id.iv_image);
        mTvTitle = layout.findViewById(R.id.tv_title);
        mTvDesc = layout.findViewById(R.id.tv_desc);
        mConstRoot = layout.findViewById(R.id.root_view_pager);

        ImageView mWebLink = layout.findViewById(R.id.opnWeb);
        ImageView mShare = layout.findViewById(R.id.share);
        ImageView mSpeek = layout.findViewById(R.id.speek);

        applyMode();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher_background);
        requestOptions.error(R.drawable.ic_launcher_background);

        String imgUrl = newsList.get(position).getUrlToImage();
        String title = newsList.get(position).getTitle();
        String desc = newsList.get(position).getDescription();
        mTvTitle.setText(title);
        mTvDesc.setText(desc);

        Glide.with(mContext)
                .asBitmap()
                .load(imgUrl)
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

                            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    onPagerOptionListener.onGetPalette(palette);
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
//        }

        mWebLink.setOnClickListener(v -> onPagerOptionListener.onOpen(position, newsList.get(position).getUrl()));

        mShare.setOnClickListener(v -> onPagerOptionListener.onShare(position, newsList.get(position).getUrl()));

        mSpeek.setOnClickListener(v -> onPagerOptionListener.onSpeek(position, v.getTag().toString()));

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void setUpInfoBackgroundColor(Palette palette) {
        swatch = palette.getDominantSwatch();
        if (swatch != null) {
            setTransparentStatusBar(swatch.getRgb());
        }
    }

    private void setTransparentStatusBar(int rgb) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = ((Activity) mContext).getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(rgb);


        } else {
            ((Activity) mContext).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            // clear FLAG_TRANSLUCENT_STATUS flag:
            ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity) mContext).getWindow().setNavigationBarColor(rgb);
        }
    }

    public void setOnPagerOptionListener(OnPagerOptionListener onPagerOptionListener) {
        this.onPagerOptionListener = onPagerOptionListener;
    }

    private void applyMode() {
        if (isDarkMode) {
            mTvTitle.setTextColor(mContext.getResources().getColor(R.color.white));
            mTvDesc.setTextColor(mContext.getResources().getColor(R.color.white));
            mConstRoot.setBackgroundColor(mContext.getResources().getColor(R.color.black));
        } else {
            mTvTitle.setTextColor(mContext.getResources().getColor(R.color.black));
            mTvDesc.setTextColor(mContext.getResources().getColor(R.color.black));
            mConstRoot.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
    }

    public interface OnPagerOptionListener {
        void onSpeek(int position, String tagValue);

        void onShare(int position, String url);

        void onOpen(int position, String webUrl);

        void onGetPalette(Palette palette);
    }
}
