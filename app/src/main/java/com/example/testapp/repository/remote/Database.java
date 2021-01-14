package com.example.testapp.repository.remote;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.testapp.repository.callback.JsonArrayCallback;
import com.example.testapp.MyApp;

import org.json.JSONArray;

import static android.content.ContentValues.TAG;

public class Database {

    private static Database instance;

    public synchronized static Database getInstance() {
        if(instance == null)
            instance = new Database();
        return instance;
    }

    private Database(){
        AndroidNetworking.initialize(MyApp.getInstance().getAppContext());
    }

    public void getJsonArray(String url, final JsonArrayCallback listener){
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse: "+response);
                        listener.onJsonArrayCallbackSuccess(response, "Success");
                    }
                    @Override
                    public void onError(ANError error) {
                        listener.onJsonArrayCallbackFailure(error.getMessage());
                    }
                });
    }


}
