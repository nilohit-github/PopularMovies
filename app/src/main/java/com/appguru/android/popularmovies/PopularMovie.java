package com.appguru.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by jhani on 4/23/2016.
 */
public class PopularMovie implements Parcelable {

    public PopularMovie()
    {

    }

    private String movieName ;
    private String id;
    private String posterUrl;
    private String releaseDate;
    private String  rating;
    private String overView;




    protected PopularMovie(Parcel in) {
        movieName = in.readString();
        id = in.readString();
        posterUrl = in.readString();
        overView = in.readString();
        rating = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<PopularMovie> CREATOR = new Creator<PopularMovie>() {
        @Override
        public PopularMovie createFromParcel(Parcel in) {
            return new PopularMovie(in);
        }

        @Override
        public PopularMovie[] newArray(int size) {
            return new PopularMovie[size];
        }
    };

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieName);
        dest.writeString(id);
        dest.writeString(posterUrl);
        dest.writeString(overView);
        dest.writeString(rating);
        dest.writeString(releaseDate);
    }
}
