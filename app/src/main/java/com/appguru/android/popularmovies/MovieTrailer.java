package com.appguru.android.popularmovies;

/**
 * Created by jhani on 6/2/2016.
 */
public class MovieTrailer{

        private String MOVIE_ID;
        private String TRAILER_ID;
        private String ISO_639_1;
        private String KEY;
        private String NAME;
        private String SITE;
        private String SIZE;
        private String TYPE;

    public MovieTrailer(String movie_id, String trailer_id ,String iso ,String key, String name, String site
            , String size, String type)
    {
        this.MOVIE_ID = movie_id;
        this.TRAILER_ID=trailer_id;
        this.ISO_639_1=iso;
        this.KEY=key;
        this.NAME=name;
        this.SITE=site;
        this.SIZE=size;
        this.TYPE=type;
    }

    public String getMOVIE_ID() {
        return MOVIE_ID;
    }

    public void setMOVIE_ID(String MOVIE_ID) {
        this.MOVIE_ID = MOVIE_ID;
    }

    public String getTRAILER_ID() {
        return TRAILER_ID;
    }

    public void setTRAILER_ID(String TRAILER_ID) {
        this.TRAILER_ID = TRAILER_ID;
    }

    public String getISO_639_1() {
        return ISO_639_1;
    }

    public void setISO_639_1(String ISO_639_1) {
        this.ISO_639_1 = ISO_639_1;
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSITE() {
        return SITE;
    }

    public void setSITE(String SITE) {
        this.SITE = SITE;
    }

    public String getSIZE() {
        return SIZE;
    }

    public void setSIZE(String SIZE) {
        this.SIZE = SIZE;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }




        }
