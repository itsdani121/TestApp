package com.example.testapp.repository.callback;

import org.json.JSONArray;

public interface JsonArrayCallback {
    void onJsonArrayCallbackSuccess(JSONArray jsonArray, String msg);
    void onJsonArrayCallbackFailure(String error);
}
