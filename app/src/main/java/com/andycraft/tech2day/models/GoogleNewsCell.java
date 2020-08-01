package com.andycraft.tech2day.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.palette.graphics.Palette;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GoogleNewsCell implements Parcelable {

    @SerializedName("author")
    private String mAuthor;
    @SerializedName("content")
    private String mContent;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("publishedAt")
    private String mPublishedAt;
    @SerializedName("source")
    private Source mSource;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("urlToImage")
    private String mUrlToImage;


//    public Palette posterPalette;
//    public Palette getPosterPalette() {
//        return posterPalette;
//    }
//    public void setPosterPalette(Palette posterPalette) {
//        this.posterPalette = posterPalette;
//    }


    public GoogleNewsCell() {
    }

    protected GoogleNewsCell(Parcel in) {
        mAuthor = in.readString();
        mContent = in.readString();
        mDescription = in.readString();
        mPublishedAt = in.readString();
        mTitle = in.readString();
        mUrl = in.readString();
        mUrlToImage = in.readString();
    }

    public static final Creator<GoogleNewsCell> CREATOR = new Creator<GoogleNewsCell>() {
        @Override
        public GoogleNewsCell createFromParcel(Parcel in) {
            return new GoogleNewsCell(in);
        }

        @Override
        public GoogleNewsCell[] newArray(int size) {
            return new GoogleNewsCell[size];
        }
    };

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }

    public Source getSource() {
        return mSource;
    }

    public void setSource(Source source) {
        mSource = source;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        mUrlToImage = urlToImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mContent);
        dest.writeString(mDescription);
        dest.writeString(mPublishedAt);
        dest.writeString(mTitle);
        dest.writeString(mUrl);
        dest.writeString(mUrlToImage);
    }
}
