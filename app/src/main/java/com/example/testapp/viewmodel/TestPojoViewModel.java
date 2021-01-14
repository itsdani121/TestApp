package com.example.testapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testapp.repository.callback.JsonArrayCallback;
import com.example.testapp.repository.model.TestPojo;
import com.example.testapp.repository.remote.Database;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TestPojoViewModel extends ViewModel {

    private Database mDatabase;
    private MutableLiveData<TestPojo> mTestPojoLiveData;
    private MutableLiveData<List<TestPojo>> mTestPojoListLiveData;

    public TestPojoViewModel() {
        this.mTestPojoLiveData = new MutableLiveData<>();
        this.mTestPojoListLiveData = new MutableLiveData<>();
        this.mDatabase = Database.getInstance();
    }

    public LiveData<TestPojo> getTestPojoLiveData() {

        if(mTestPojoLiveData == null)
            mTestPojoLiveData = new MutableLiveData<>();

        if(mTestPojoLiveData.getValue() == null)
            fetchPosts(0);

        return mTestPojoLiveData;
    }

    public LiveData<List<TestPojo>> getTestPojoListLiveData() {
        return mTestPojoListLiveData;
    }

    public void fetchPosts(final int postNo) {

        mDatabase.getJsonArray("https://jsonplaceholder.typicode.com/posts", new JsonArrayCallback() {
            @Override
            public void onJsonArrayCallbackSuccess(JSONArray jsonArray, String msg) {
                try {
                    JSONObject post = jsonArray.getJSONObject(postNo);
                    Gson gson = new Gson();
                    TestPojo testPojo = gson.fromJson(post.toString(), TestPojo.class);
                    mTestPojoLiveData.setValue(testPojo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onJsonArrayCallbackFailure(String error) {

            }
        });
    }

}
