package com.alan.rockonapp.parser;

import com.alan.rockonapp.adapter.model.Concert;
import com.alan.rockonapp.adapter.model.Ticket;
import com.alan.rockonapp.adapter.model.Venue;
import com.alan.rockonapp.constant.ParserConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alan
 */
public class ConcertParser implements ParserConst {
    private JSONArray mConcertsArray;

    public ConcertParser(final JSONArray concertsArray) {
        this.mConcertsArray = concertsArray;
    }

    public List jsonToConcertsList() {
        List concertsList = new ArrayList<>();
        //List artists = new ArrayList<>();

        try {
            // Parsing json array response
            // loop through each json object
            for (int i = 0; i < mConcertsArray.length(); i++) {

                JSONObject concert = (JSONObject) mConcertsArray.get(i);

                Concert concerts = new Concert();
                concerts.setName(concert.getString(CONCERT_NAME))
                        .setArtistName(concert.getJSONArray(ARTISTS).getJSONObject(0).getString(NAME));
                if(concert.has(IMAGES)) {
                    concerts.setSmallImageUrl(concert.getJSONObject(IMAGES).getString(SMALL_IMAGE_URL));
                    concerts.setMediumImageUrl(concert.getJSONObject(IMAGES).getString(MEDIUM_IMAGE_URL));
                    concerts.setLargeImageUrl(concert.getJSONObject(IMAGES).getString(LARGE_IMAGE_URL));

                }

                if(concert.has(VENUE)) {
                    Venue venue = new Venue();
                    concerts.setVenue(parseVenue(venue, concert.getJSONObject(VENUE)));
                }

                if(concert.has((CONCERT_DATE))){
                    concerts.setConcertDate(concert.getString(CONCERT_DATE));
                }

                if(concert.has(TICKET_PRICE)){
                    Ticket ticket = new Ticket();
                    ticket.setMax_value(concert.getJSONObject(TICKET_PRICE).getString(TICKET_MAX));
                    ticket.setMin_value(concert.getJSONObject(TICKET_PRICE).getString(TICKET_MIN));
                    concerts.setTicket(ticket);
                }


                concertsList.add(i,concerts);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return concertsList;
    }

    public List parseArtists(List artists, JSONArray artistsArray)
    {
        try {


            for (int i = 0; i < artistsArray.length(); i++) {

                JSONObject artist = (JSONObject) artistsArray.get(i);
                artists.add(artist.getString(ARTIST_NAME));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return artists;
    }

    public Venue parseVenue (Venue v, JSONObject venue)
    {
        try {
            v.setName(venue.getString(VENUE_NAME));
            v.setCity(venue.getString(CITY));
            v.setCountry(venue.getString(COUNTRY));
            v.setStreet(venue.getString(STREET));
            v.setLatitude(venue.getString(LATITUDE));
            v.setLongitude(venue.getString(LONGITUDE));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }
}
