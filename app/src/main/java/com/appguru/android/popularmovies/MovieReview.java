package com.appguru.android.popularmovies;

/**
 * Created by jhani on 6/3/2016.
 */
public class MovieReview {

    private String MOVIE_ID;
    private String AUTHOR;

    public String getMOVIE_ID() {
        return MOVIE_ID;
    }

    public void setMOVIE_ID(String MOVIE_ID) {
        this.MOVIE_ID = MOVIE_ID;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public void setAUTHOR(String AUTHOR) {
        this.AUTHOR = AUTHOR;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    private String CONTENT;

    public MovieReview(String movie_id, String author_id ,String content )
    {
        this.MOVIE_ID = movie_id;
        this.AUTHOR=author_id;
        this.CONTENT=content;

    }

}
