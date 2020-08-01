package com.andycraft.tech2day;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Manabendu on 2019-07-17
 */
public class FragmentViewPager extends Fragment {


    private Palette posterPalette;
    private Palette.Swatch swatch;

    public Palette getPosterPalette() {
        return posterPalette;
    }

    public void setPosterPalette(Palette posterPalette) {
        this.posterPalette = posterPalette;
    }

    public static FragmentViewPager newInstance(String title, String desc, String img) {

        FragmentViewPager f = new FragmentViewPager();
        Bundle b = new Bundle();
        b.putString("title", title);
        b.putString("desc", desc);
        b.putString("img", img);

        f.setArguments(b);

        return f;
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        TextView tv = (TextView) v.findViewById(R.id.tv_title);
        tv.setText(getArguments().getString("title"));

        TextView tvDesc = v.findViewById(R.id.tv_desc);
        tvDesc.setText(getArguments().getString("desc"));

        ImageView imageView = (ImageView) v.findViewById(R.id.iv_image);
        String imgUrl =getArguments().getString("img");


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher_background);
        requestOptions.error(R.drawable.ic_launcher_background);


        Glide.with(this)
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
                .into(imageView);

        return v;
    }

    private void setTransparentStatusBar(int rgb) {
//        color = rgb;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//                    String hexColor = String.format("#%06X", (0xFFFFFF & color));

            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(rgb);


        } else {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            // clear FLAG_TRANSLUCENT_STATUS flag:
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setNavigationBarColor(rgb);
        }
    }


    private void setUpInfoBackgroundColor(Palette palette) {
        swatch = palette.getDominantSwatch();
        if (swatch != null) {
//            int startColor = ContextCompat.getColor(ll.getContext(), R.color.grey_800);
//            int endColor = swatch.getRgb();

//            AnimationUtility.animateBackgroundColorChange(ll, startColor, endColor);
            setTransparentStatusBar(swatch.getRgb());
        }
    }
}
