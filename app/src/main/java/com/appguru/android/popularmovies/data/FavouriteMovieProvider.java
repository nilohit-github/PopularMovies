package com.appguru.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by jhani on 5/30/2016.
 */
public class FavouriteMovieProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDBHelper mOpenHelper;

    static final int MOVIE = 100;
    static final int MOVIE_ID = 101;

    //location.location_setting = ?
    private static final String sMovieWithID =
            MovieContract.MovieList.TABLE_NAME+
                    "." + MovieContract.MovieList.COLUMN_MOVIE_ID + " = ? ";


    static UriMatcher buildUriMatcher() {

        // 1) The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case. Add the constructor below.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;



        // 2) Use the addURI function to match each of the types.  Use the constants from
        // WeatherContract to help define the types to the UriMatcher.


        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MovieContract.PATH_MOVIES, MOVIE);
        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/*", MOVIE_ID);

        return matcher;

    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDBHelper(getContext());
        return true;
    }


    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {

            case MOVIE:
                return MovieContract.MovieList.CONTENT_TYPE;
            case MOVIE_ID:
                return  MovieContract.MovieList.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            // "MOVIE"
            case MOVIE: {

                Log.v("inside movie","query"+uri.toString());
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieList.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case MOVIE_ID: {

                String movie_id = MovieContract.MovieList.getMovieIDFromUri(uri);
                Log.v("inside movie id","query"+movie_id);
                selection = sMovieWithID;
                selectionArgs = new String[]{movie_id};
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieList.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }




            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIE: {

                long _id = db.insert(MovieContract.MovieList.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.MovieList.buildMovieUriWithID(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case MOVIE:
                rowsDeleted = db.delete(
                        MovieContract.MovieList.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_ID: {

                String movie_id = MovieContract.MovieList.getMovieIDFromUri(uri);
                Log.v("inside movie id", "delete" + movie_id);
                selection = sMovieWithID;
                selectionArgs = new String[]{movie_id};
                rowsDeleted = db.delete(
                        MovieContract.MovieList.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MOVIE:

                rowsUpdated = db.update(MovieContract.MovieList.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

}
