package com.appguru.android.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jhani on 5/30/2016.
 */
public class MovieContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.appguru.android.popularmovies";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_MOVIES = "movies";

    /*
       Inner class that defines the table contents of the location table
       Students: This is where you will add the strings.  (Similar to what has been
       done for WeatherEntry)
    */
    public static final class MovieList implements BaseColumns {
        public static final String TABLE_NAME = "movies";

        // Movie id will be stored in the movies table to identify each movie uniquely.
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_NAME = "movie_name";
        public static final String COLUMN_MOVIE_POSTER = "movie_poster";
        public static final String COLUMN_MOVIE_SYNOPSIS = "movie_synopsis";
        public static final String COLUMN_MOVIE_RATING = "movie_rating";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";



        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static Uri buildMovieUriWithID(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMovieIDURI(String movie_id) {

            return CONTENT_URI.buildUpon().appendPath(movie_id).build();
            //return null;
        }

        public static String getMovieIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }


    }

}
