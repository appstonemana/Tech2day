package com.andycraft.tech2day.helpers;


import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface Api {


    @GET
    Observable<String> getResponse(@Url String url, @QueryMap HashMap<String, Object> params);


}
