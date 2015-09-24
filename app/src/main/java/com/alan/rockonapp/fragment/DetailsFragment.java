package com.alan.rockonapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alan.rockonapp.R;
import com.alan.rockonapp.activity.ConcertsActivity;
import com.alan.rockonapp.activity.SetlistActivity;
import com.alan.rockonapp.constant.IntentConst;
import com.alan.rockonapp.constant.ParserConst;
import com.alan.rockonapp.controller.AppController;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author alan
 */
public class DetailsFragment extends Fragment implements
        Response.Listener<JSONObject>,
        Response.ErrorListener,
        ParserConst,
        IntentConst {

    private static final String LOG_TAG = DetailsFragment.class.getSimpleName();
    private static final String DATE_PATTERN = "yyyy.MM.dd HH:mm:ss";
    private NetworkImageView mConcertPicture;
    private TextView mConcertName;
    private RatingBar mConcertFavorite;
    private TextView mArtistName;
    private TextView mAddressNumber;
    private TextView mTicketRange;
    private TextView mEventDate;


    public DetailsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_details, container, false);

        findViewsByParent(view);
        bindViews(getActivity().getIntent());

        return view;
    }

    /**
     *
     * @param view
     */
    private void findViewsByParent(@NonNull final View view) {
        mConcertPicture = (NetworkImageView) view.findViewById(R.id.concert_medium_picture);
        mConcertName = (TextView) view.findViewById(R.id.concert_details_name);
        mConcertFavorite = (RatingBar) view.findViewById(R.id.contact_favorite);
        mArtistName = (TextView) view.findViewById(R.id.artist_name);
        mEventDate = (TextView) view.findViewById(R.id.event_date);
        mAddressNumber = (TextView) view.findViewById(R.id.address_number);
        mTicketRange = (TextView) view.findViewById(R.id.ticket_range);
    }

    /**
     *
     * @param intent
     */
    private void bindViews(@NonNull final Intent intent) {
        mConcertName.setText(intent.getStringExtra(INTENT_NAME));


        mEventDate.setText(intent.getStringExtra(intent.getStringExtra(INTENT_EVENT_DATE)));
        mAddressNumber.setText(intent.getStringExtra(INTENT_VENUE_STREET+","+INTENT_VENUE_CITY+","+
                                                     INTENT_VENUE_COUNTRY));
        mTicketRange.setText(intent.getStringExtra(INTENT_TICKET_MAX) +
                                                   "~" +
                             intent.getStringExtra(INTENT_TICKET_MIN));

        mArtistName.setText(intent.getStringExtra(INTENT_ARTIST_NAME));
        mArtistName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String artistName = mArtistName.getText().toString().trim();
                artistName = artistName.replace(' ','+');
                Intent setListIntent = new Intent(getActivity(), SetlistActivity.class);
                setListIntent.putExtra(INTENT_ARTIST_NAME, artistName);

                startActivity(setListIntent);
            }
        });

    }

    /**
     *
     * @param millis
     */
    private void setFormattedDate(@NonNull final Long millis) {
        Date date = new Date(millis);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());

        //mBirthDate.setText(formatter.format(date));
    }

    /**
     *
     * @param url
     */
    private void getDetailsFromNetwork(@NonNull final String url) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(url, this, this);
        AppController.getInstance(getContext()).addToRequestQueue(jsonRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        ImageLoader loader = AppController.getInstance(getContext()).getImageLoader();
        mConcertFavorite.setIsIndicator(true);
        mConcertFavorite.setNumStars(1);

        try {
            if(response.getBoolean(FAVORITE))
                mConcertFavorite.setRating(1);

            mConcertPicture.setImageUrl(response.getString(MEDIUM_IMAGE_URL), loader);

            //mAddressNumber.setText(getAddressFromJson(response.getJSONObject(ADDRESS)));

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMSg = error.getMessage();
        if(errorMSg==null)
            errorMSg="There was an a problem obtaining the details :(";
        VolleyLog.d(LOG_TAG, "Error: " + errorMSg);
        Toast.makeText(getActivity().getApplicationContext(),
                errorMSg, Toast.LENGTH_SHORT).show();

        Intent concertsActivity = new Intent(getActivity(), ConcertsActivity.class);
        startActivity(concertsActivity);
    }

}
