package com.appguru.android.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by jhani on 6/1/2016.
 */
public class FetchMovieTrailer extends AsyncTask<Void, Void, ArrayList<MovieTrailer>> {

    private String mMovieID;
    public ArrayList<MovieTrailer> movieTrailerArrayList ;
    public TrailerAdapter trailerAdapter;



    private final String LOG_TAG = FetchMovieTrailer.class.getSimpleName();

    private final Context mContext;
    Toast toast;
    CharSequence text;

    public FetchMovieTrailer(Context context, String movieID,ArrayList movieTrailerArrayList,TrailerAdapter trailerAdapter) {
        mContext = context;
        mMovieID =movieID;
        this.movieTrailerArrayList = movieTrailerArrayList;
        this.trailerAdapter= trailerAdapter;

    }

    private boolean DEBUG = true;

    @Override
    protected ArrayList<MovieTrailer> doInBackground(Void... ArrayList) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        String trailerJsonStr = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        if (mMovieID == null) {
            return null;
        }
        String movie_id = mMovieID;

        try {
            final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie";
            final String API_KEY_PARAM = "api_key";
            String apiKey = BuildConfig.POPULAR_MOVIES_API_KEY;
            final String VIDEOS = "videos";
            String trailerJasonStr = null;


                Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                        .appendPath(movie_id)
                        .appendPath(VIDEOS)
                        .appendQueryParameter(API_KEY_PARAM, apiKey)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.i(LOG_TAG, "URL: " + url);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = null;
                try {
                    inputStream = urlConnection.getInputStream();
                } catch (Exception e) {
                }
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    trailerJasonStr = null;

                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.

                    trailerJasonStr = null;
                }

            trailerJsonStr =buffer.toString();

            //end of for

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error " + e.getMessage(), e);
            e.printStackTrace();

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        Log.d(LOG_TAG, "Movie Trailer Data fetched");
        try {
            return getTrailersDataFromJson(trailerJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<MovieTrailer> getTrailersDataFromJson(String moviesJsonStr)
            throws JSONException {

        final String MOVIE_ID = "id";
        final String RESULTS_ARRAY = "results";
        final String TRAILER_ID = "id";
        final String ISO_639_1 = "iso_639_1";
        final String KEY = "key";
        final String NAME = "name";
        final String SITE = "site";
        final String SIZE = "size";
        final String TYPE = "type";

        try {
            JSONObject moviesJson = new JSONObject(moviesJsonStr);
             String movie_id = moviesJson.getString(MOVIE_ID);

            JSONArray trailerArray = moviesJson.getJSONArray(RESULTS_ARRAY);
           // Vector<ContentValues> cVVector = new Vector<ContentValues>(moviesArray.length());

            for (int i = 0; i < trailerArray.length(); i++) {
                JSONObject movieJSONObject = trailerArray.getJSONObject(i);
                if (movieJSONObject.getString(SITE).equals("YouTube")) {

                    String trailerId = movieJSONObject.getString(TRAILER_ID);
                    String iso_639_1 = movieJSONObject.getString(ISO_639_1);
                    String key = movieJSONObject.getString(KEY);
                    String name = movieJSONObject.getString(NAME);

                    String site = movieJSONObject.getString(SITE);
                    String size = movieJSONObject.getString(SIZE);
                    String type = movieJSONObject.getString(TYPE);
                    MovieTrailer movieTrailer = new MovieTrailer(movie_id, trailerId, iso_639_1, key, name, site, size, type);
                    movieTrailerArrayList.add(movieTrailer);
                }
               /* ContentValues trailerValues = new ContentValues();

                trailerValues.put(TrailerEntry.COLUMN_MOVIE_ID, movieId);
                trailerValues.put(TrailerEntry.COLUMN_TRAILER_ID, trailerId);
                trailerValues.put(TrailerEntry.COLUMN_ISO_369_1, ISO_639_1);
                trailerValues.put(TrailerEntry.COLUMN_KEY, key);
                trailerValues.put(TrailerEntry.COLUMN_NAME, name);
                trailerValues.put(TrailerEntry.COLUMN_SITE, site);
                trailerValues.put(TrailerEntry.COLUMN_SIZE, size);
                trailerValues.put(TrailerEntry.COLUMN_TYPE, type);
                trailerValues.put(MovieEntry.COLUMN_DATE, mDayTime.setJulianDay(mJulianStartDay));

                cVVector.add(trailerValues);*/
            }


        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();

        }
        return movieTrailerArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieTrailer> movieTrailerArrayList) {

        if(movieTrailerArrayList.size()==0)
        {
            int duration = Toast.LENGTH_LONG;
            text ="Unable to fetch trailer,Sorry for the inconvinience";
            toast = Toast.makeText(mContext, text, duration);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
        }

        super.onPostExecute(movieTrailerArrayList);
        trailerAdapter.notifyDataSetChanged();



    }
}// end of Trailers Task





