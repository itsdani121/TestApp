package com.example.testapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testapp.R;
import com.example.testapp.databinding.ActivityMainBinding;
import com.example.testapp.repository.callback.JsonArrayCallback;
import com.example.testapp.repository.model.TestPojo;
import com.example.testapp.repository.remote.Database;
import com.example.testapp.viewmodel.Factory;
import com.example.testapp.viewmodel.TestPojoViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TestPojoViewModel mViewModel;

    private ActivityMainBinding mBinding;

    private int postNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initRef();
        observe();

        mBinding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postNo++;
                mViewModel.fetchPosts(postNo);
            }
        });
    }

    private void observe() {
        mViewModel.getTestPojoLiveData().observe(this, new Observer<TestPojo>() {
            @Override
            public void onChanged(TestPojo testPojo) {
                Log.d(TAG, "onChanged: "+testPojo);
                if(testPojo != null)
                    populate(testPojo);
            }
        });


    }

    private void populate(TestPojo testPojo) {
        int id = testPojo.getId();
        String title = testPojo.getTitle();
        String body = testPojo.getBody();

        mBinding.postNoTv.setText("Post No. "+id);
        mBinding.titleTv.setText(title);
        mBinding.bodyTv.setText(body);
    }

    private void initRef() {
        mViewModel = new ViewModelProvider(this, new Factory()).get(TestPojoViewModel.class);
    }

}