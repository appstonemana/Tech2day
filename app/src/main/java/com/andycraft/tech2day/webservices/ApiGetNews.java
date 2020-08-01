package com.andycraft.tech2day.webservices;

import android.content.Context;

import com.andycraft.tech2day.helpers.ApiCaller;
import com.andycraft.tech2day.models.GoogleNewsCell;
import com.andycraft.tech2day.models.NewsCell;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Manabendu on 2019-07-10
 */
public class ApiGetNews extends ApiCaller {

    private Context context;
    private String sources;
    private String apiKey;

    private ArrayList<GoogleNewsCell> newsCells;

    public ApiGetNews(Context context) {
        this.context = context;
    }

    public ApiGetNews setSources(String sources) {
        this.sources = sources;
        return this;
    }

    public ApiGetNews setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    @Override
    protected String getURL() {
        return "v2/top-headlines";
    }

    @Override
    protected HashMap<String, Object> getParams() {
        HashMap<String ,Object> params = new HashMap<>();
        params.put("sources",sources);
        params.put("apiKey",apiKey);
        return params;
    }

    public ArrayList<GoogleNewsCell> getAllNews(){
       return newsCells;
    }

    @Override
    protected boolean parseJSONResponse(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);
            newsCells = new ArrayList<>();
            newsCells.clear();
            newsCells = getAllNews(jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    private ArrayList<GoogleNewsCell> getAllNews(JSONArray jsonArray) {
        ArrayList<GoogleNewsCell> news = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            GoogleNewsCell cell = new GoogleNewsCell();
            JSONObject jObj = jsonArray.optJSONObject(i);
            cell.setTitle(jObj.optString("title"));
            cell.setContent(jObj.optString("content"));
            cell.setUrl(jObj.optString("url"));
            cell.setUrlToImage(jObj.optString("urlToImage"));
            cell.setDescription(jObj.optString("description"));

            news.add(cell);
        }

        return news;
    }
}
