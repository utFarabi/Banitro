package com.sm.banitro.ui.main;

import org.json.JSONException;
import org.json.JSONObject;

import co.ronash.pushe.PusheListenerService;

public class PushListener extends PusheListenerService {
    @Override
    public void onMessageReceived(JSONObject json, JSONObject messageContent) {
        super.onMessageReceived(json, messageContent);
        if (json == null || json.length() == 0)
            return; //json is empty
        android.util.Log.i("Pushe", "Custom json Message: " + json.toString()); //print json to logCat
        //Do something with json
        try {
            String s1 = json.getString("title");
            String s2 = json.getString("content");
            android.util.Log.i("Pushe", "Json Message\n title: " + s1 + "\n content: " + s2);
        } catch (JSONException e) {
            android.util.Log.e("TAG", "Exception in parsing json", e);
        }
    }
}
