package com.alan.rockonapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alan.rockonapp.R;
import com.alan.rockonapp.adapter.SetlistAdapter;
import com.alan.rockonapp.adapter.model.Setlist;
import com.alan.rockonapp.constant.IntentConst;
import com.alan.rockonapp.controller.AppController;
import com.alan.rockonapp.parser.SetListParser;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;


public class SetlistActivity extends AppCompatActivity implements
        Response.Listener<JSONArray>,
        Response.ErrorListener,
        AdapterView.OnItemClickListener,
        IntentConst{

    private static final String LOG_TAG = SetlistActivity.class.getSimpleName();

    private static final String BASE_URL = "http://10.0.2.2:8081/api/artists/";

    private SetlistAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlist);

        final ListView listView = (ListView) findViewById(R.id.setlists_lv);

        mAdapter = new SetlistAdapter(this, new ArrayList());

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

        final Intent extras = getIntent();
        String artistName = "";
        if(null != extras)
            artistName = extras.getStringExtra("artist_name");


        final String jsonUrl = BASE_URL + artistName;

        final JsonArrayRequest request = new JsonArrayRequest (jsonUrl, this, this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMSg = error.getMessage();
        if(errorMSg==null)
            errorMSg="There was an a problem obtaining the setlists :(";
        VolleyLog.d(LOG_TAG, "Error: " + errorMSg);
        Toast.makeText(this.getApplicationContext(),
                errorMSg, Toast.LENGTH_SHORT).show();

        Intent concertsActivity = new Intent(this, ConcertsActivity.class);
        startActivity(concertsActivity);
    }

    @Override
    public void onResponse(JSONArray response) {
        SetListParser parser = new SetListParser(response);
        mAdapter.setItems(parser.jsonToSetList());
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Setlist setlist= (Setlist) parent.getAdapter().getItem(position);

        Intent songListActivity = new Intent(this, SongListActivity.class);

        songListActivity.putExtra(INTENT_SONG_LIST, setlist.getSongList().toString()
                                                    .replace(',', '\n'));
        songListActivity.putExtra(INTENT_EVENT_DATE, setlist.getEventDate());

        startActivity(songListActivity);
    }
}
