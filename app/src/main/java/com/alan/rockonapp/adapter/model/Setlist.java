package com.alan.rockonapp.adapter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alan on 24/09/15.
 */
public class Setlist {
    private List<String> songList = new ArrayList<String>();
    private String eventDate;

    public Setlist setSongList(List<String> songList) {
        this.songList = songList;
        return this;
    }

    public List<String> getSongList() {
        return songList;
    }

    public Setlist addSong(String song)
    {
        songList.add(song);
        return this;
    }


    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDate() {
        return eventDate;
    }
}
