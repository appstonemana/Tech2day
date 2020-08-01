package com.andycraft.tech2day.helpers;

import android.util.Log;

import com.andycraft.tech2day.models.GoogleNewsCell;
import com.andycraft.tech2day.utils.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


abstract public class ApiCaller {


    public static final String TAG = ApiCaller.class.getSimpleName();
    private Api api;

    abstract protected String getURL();

    abstract protected HashMap<String, Object> getParams();


    protected boolean parseJSONResponse(String response) {
        return true;
    }

    protected boolean showResponseMessage(String message) {
        return true;
    }


    public void makeCall(ApiTaskDelegate apiTaskDelegate) {

        api = new ApiHandler().getApi();

        CompositeDisposable disposables = new CompositeDisposable();

        String url = getURL().concat(convertWithGuava(getParams()));
        Log.i(TAG, "makeCall:::::::::::: "+url);

        disposables.add(api.getResponse(getURL(), getParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String o) {

                        ObjectMapper mapper = new ObjectMapper();
                        String jsonResponse = "";
                        JSONObject jsonObject = null;
                        try {

                            jsonObject = new JSONObject(o);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        GenericResponse responseObj = new GenericResponse();
                        if (responseObj != null) {
                            responseObj.status = jsonObject.optString("status");
                            responseObj.totalResults = jsonObject.optInt("totalResults");
                            responseObj.articles = jsonObject.optJSONArray("articles");

                            if (responseObj.status.equals("ok")) {
//                                try{
//                                    jsonResponse = mapper.writeValueAsString(jsonObject.opt("result"));
                                parseJSONResponse(responseObj.articles.toString());
                                apiTaskDelegate.onTaskCompleted(ApiCaller.this, "Success");
//                                }catch (Exception e){
//                                    apiTaskDelegate.onErrorOccured(ApiCaller.this, responseObj.message);
//                                }
                            } else {
                                apiTaskDelegate.onErrorOccured(ApiCaller.this, responseObj.message);
                            }
                        } else {
                            apiTaskDelegate.onErrorOccured(ApiCaller.this, "Server Error");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        apiTaskDelegate.onErrorOccured(ApiCaller.this, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }


    private String convertWithGuava(Map<String, ?> map) {
        return Joiner.on("&").withKeyValueSeparator("=").join(map);
    }
}