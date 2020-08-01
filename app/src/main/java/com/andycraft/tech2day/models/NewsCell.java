package com.andycraft.tech2day.models;

/**
 * Created by Manabendu on 2019-07-06
 */
public class NewsCell {

    private int id;
    private String title;
    private String news_desc;
    private String news_original_url;
    private String publish_time;
    private String news_category;
    private String news_topic;
    private String news_vendor;
    private String news_vendor_logo;
    private String news_image;

    public String getNews_image() {
        return news_image;
    }

    public void setNews_image(String news_image) {
        this.news_image = news_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNews_desc() {
        return news_desc;
    }

    public void setNews_desc(String news_desc) {
        this.news_desc = news_desc;
    }

    public String getNews_original_url() {
        return news_original_url;
    }

    public void setNews_original_url(String news_original_url) {
        this.news_original_url = news_original_url;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getNews_category() {
        return news_category;
    }

    public void setNews_category(String news_category) {
        this.news_category = news_category;
    }

    public String getNews_topic() {
        return news_topic;
    }

    public void setNews_topic(String news_topic) {
        this.news_topic = news_topic;
    }

    public String getNews_vendor() {
        return news_vendor;
    }

    public void setNews_vendor(String news_vendor) {
        this.news_vendor = news_vendor;
    }

    public String getNews_vendor_logo() {
        return news_vendor_logo;
    }

    public void setNews_vendor_logo(String news_vendor_logo) {
        this.news_vendor_logo = news_vendor_logo;
    }
}
