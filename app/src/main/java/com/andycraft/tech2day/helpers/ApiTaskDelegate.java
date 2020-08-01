package com.andycraft.tech2day.helpers;

public interface ApiTaskDelegate {

     void onTaskCompleted(ApiCaller apiCaller, String message);

     void onErrorOccured(ApiCaller apiCaller, String error);
}
