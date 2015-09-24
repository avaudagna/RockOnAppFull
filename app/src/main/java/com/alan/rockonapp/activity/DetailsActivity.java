package com.alan.rockonapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alan.rockonapp.R;

/**
 * @author alan
 */
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle(R.string.details);
        //styleActionBar(getSupportActionBar());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
