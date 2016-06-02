package com.appguru.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jhani on 5/30/2016.
 */
public class MovieDBHelper extends SQLiteOpenHelper {


    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movie.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MovieList.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                MovieContract.MovieList._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                MovieContract.MovieList.COLUMN_MOVIE_ID + " INTEGER UNIQUE NOT NULL, "+
                MovieContract.MovieList.COLUMN_MOVIE_NAME + " TEXT, "+
                MovieContract.MovieList.COLUMN_MOVIE_POSTER + " TEXT, "+
                MovieContract.MovieList.COLUMN_MOVIE_RATING + " TEXT, "+
                MovieContract.MovieList.COLUMN_MOVIE_RELEASE_DATE + " TEXT, "+
                MovieContract.MovieList.COLUMN_MOVIE_SYNOPSIS + " TEXT "+

                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieList.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
