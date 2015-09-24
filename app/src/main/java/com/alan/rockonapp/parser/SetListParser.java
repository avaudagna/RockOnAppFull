package com.alan.rockonapp.parser;

import com.alan.rockonapp.adapter.model.Setlist;
import com.alan.rockonapp.constant.ParserConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alan on 24/09/15.
 */
public class SetListParser implements ParserConst {
    private JSONArray mSetlistsArray;

    public SetListParser(final JSONArray setlistArray) {
        this.mSetlistsArray = setlistArray;
    }

    public List jsonToSetList() {
        List setlistsList = new ArrayList<>();

        try {
            //for each event
            for (int i = 0; i < mSetlistsArray.length(); i++) {

                JSONObject event = (JSONObject) mSetlistsArray.get(i);
                if(event.has(SETS)) {
                    JSONObject sets = (JSONObject) event.getJSONObject(SETS);
                    if (sets.has(SET)) {
                        JSONArray subSet = sets.getJSONArray(SET);

                        Setlist setlist = new Setlist();

                        //for each songList in each subset
                        for (int j = 0; j < subSet.length(); j++) {
                            //songList
                            JSONArray songList = (JSONArray) subSet.getJSONObject(j)
                                    .getJSONArray(SONG_ARRAY_NAME);

                            //for each song in each songList
                            for (int k = 0; k < songList.length(); k++) {
                                setlist.addSong(songList.getJSONObject(k).getString(SONG_NAME));
                            }
                        }

                        setlist.setEventDate(event.getString(SETLIST_DATE));
                        setlistsList.add(i,setlist);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return setlistsList;
    }

}
