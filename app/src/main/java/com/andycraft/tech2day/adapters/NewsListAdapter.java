package com.andycraft.tech2day.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.andycraft.tech2day.R;
import com.andycraft.tech2day.models.GoogleNewsCell;
import com.andycraft.tech2day.utils.ThemeColorizer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by Manabendu on 2019-07-06
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_1 = 1;
    private static final String TAG = NewsListAdapter.class.getSimpleName();

    OnNewsClickListener onNewsClickListener;
    Palette.Swatch swatch;
    private Context mContext;
    private ArrayList<GoogleNewsCell> mData;
    private LayoutInflater inflater;
    private RequestOptions requestOptions;
    private TextToSpeech.OnInitListener listener;
    private boolean isDarkMode;

    public NewsListAdapter(Context context, ArrayList<GoogleNewsCell> data, TextToSpeech.OnInitListener listener) {
        this.mContext = context;
        this.mData = data;
        inflater = LayoutInflater.from(context);
        requestOptions = new RequestOptions();

        this.listener = listener;

    }

    public void setDarkMode(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
    }

    public void setOnNewsClickListener(OnNewsClickListener onNewsClickListener) {
        this.onNewsClickListener = onNewsClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 5 == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;


        if (viewType == TYPE_1) {
            viewHolder = new NewsCellViewHolder(inflater.inflate(R.layout.cell_news_list, parent, false));
        } else {
            viewHolder = new NewsHolder2(inflater.inflate(R.layout.cell_top_news, parent, false));
        }

        return viewHolder;

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        GoogleNewsCell cell;

        if (holder.getItemViewType() == TYPE_1) {
            NewsCellViewHolder vh = (NewsCellViewHolder) holder;
            cell = mData.get(position);
            vh.mTvTitle.setText(cell.getTitle());
            requestOptions.placeholder(R.drawable.ic_launcher_background);
            requestOptions.error(R.drawable.ic_launcher_background);
            Glide.with(mContext)
                    .asBitmap()
                    .load(cell.getUrlToImage())
                    .apply(requestOptions)
                    .into(vh.mIvNewsImg);

            vh.mCvRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNewsClickListener.onNewsClick(position, vh.mIvNewsImg, false, "");
                }
            });

            ThemeColorizer.seViewTextColor(mContext, ((NewsCellViewHolder) holder).mTvTitle, isDarkMode);

            ThemeColorizer.setViewBackgroundColor(mContext, ((NewsCellViewHolder) holder).mCvRoot, isDarkMode);

//            ThemeColorizer.setSpeekerBackgroundColor(mContext, ((NewsCellViewHolder) holder).mIvNewsImg, isDarkMode);

        } else {
            NewsHolder2 vh = (NewsHolder2) holder;
            cell = mData.get(position);
            vh.mTvTitle.setText(cell.getTitle());
            String tagValue = (String) vh.mBtnSpeak.getTag();
            requestOptions.placeholder(R.drawable.ic_launcher_background);
            requestOptions.error(R.drawable.ic_launcher_background);
            Glide.with(mContext)
                    .asBitmap()
                    .load(cell.getUrlToImage())
                    .apply(requestOptions)
                    .into(vh.mIvNewsImg);

            vh.mCvRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNewsClickListener.onNewsClick(position, vh.mIvNewsImg, false, tagValue);
                }
            });

            vh.mBtnSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onInit(TextToSpeech.SUCCESS);
                    onNewsClickListener.onNewsClick(position, vh.mBtnSpeak, true, tagValue);
                }
            });

            ThemeColorizer.seViewTextColor(mContext, ((NewsHolder2) holder).mTvTitle, isDarkMode);

            ThemeColorizer.setViewBackgroundColor(mContext, ((NewsHolder2) holder).mCvRoot, isDarkMode);

            ThemeColorizer.setSpeekerBackgroundColor(mContext, ((NewsHolder2) holder).mBtnSpeak, isDarkMode);

        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnNewsClickListener {
        void onNewsClick(int position, View view, boolean btn, String tagValue);
    }

    public class NewsCellViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        ImageView mIvNewsImg;
        ConstraintLayout mCvRoot;

        public NewsCellViewHolder(View itemView) {
            super(itemView);

            mCvRoot = itemView.findViewById(R.id.root_view);
            mTvTitle = itemView.findViewById(R.id.tv_news_title);
            mIvNewsImg = itemView.findViewById(R.id.iv_news_img);
        }
    }

    public class NewsHolder2 extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        ImageView mIvNewsImg;
        ConstraintLayout mCvRoot;
        ImageView mBtnSpeak;

        public NewsHolder2(@NonNull View itemView) {
            super(itemView);
            itemView.setTag("speek");
            mCvRoot = itemView.findViewById(R.id.root_view);
            mTvTitle = itemView.findViewById(R.id.tv_news_title);
            mIvNewsImg = itemView.findViewById(R.id.iv_news_img);
            mBtnSpeak = itemView.findViewById(R.id.btn_speek);
        }
    }

}