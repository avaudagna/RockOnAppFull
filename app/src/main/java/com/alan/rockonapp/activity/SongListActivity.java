package com.alan.rockonapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alan.rockonapp.R;
import com.alan.rockonapp.constant.IntentConst;

/**
 * Created by alan on 24/09/15.
 */
public class SongListActivity extends AppCompatActivity implements IntentConst {

    private TextView mSongList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        setTitle(R.string.title);
        setTitle(getIntent().getStringExtra(INTENT_EVENT_DATE));

        mSongList = (TextView) findViewById(R.id.song_list_tv);
        mSongList.setText(getIntent().getStringExtra(INTENT_SONG_LIST));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}