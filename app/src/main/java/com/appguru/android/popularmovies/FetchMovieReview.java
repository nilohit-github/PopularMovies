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
public class FetchMovieReview extends AsyncTask<Void, Void, ArrayList<MovieReview>> {

    private String mMovieID;
    public ArrayList<MovieReview> movieReviewArrayList ;
    public ReviewAdapter reviewAdapter;
    final String RESULTS = "results";



    private final String LOG_TAG = FetchMovieReview.class.getSimpleName();

    private final Context mContext;
    Toast toast;
    CharSequence text;

    public FetchMovieReview(Context context, String movieID,ArrayList movieReviewArrayList,ReviewAdapter reviewAdapter) {
        mContext = context;
        mMovieID =movieID;
        this.movieReviewArrayList = movieReviewArrayList;
        this.reviewAdapter= reviewAdapter;

    }

    private boolean DEBUG = true;

    @Override
    protected ArrayList<MovieReview> doInBackground(Void... ArrayList) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        String reviewJsonStr = null;
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
            final String REVIEWS = "reviews";
            String reviewJasonStr = null;


            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendPath(movie_id)
                    .appendPath(REVIEWS)
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
                reviewJasonStr = null;

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

                reviewJasonStr = null;
            }

            reviewJsonStr =buffer.toString();


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

        Log.d(LOG_TAG, "Movie Review Data fetched");
        try {
            return getReviewDataFromJson(reviewJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<MovieReview> getReviewDataFromJson(String moviesJsonStr)
            throws JSONException {

        final String MOVIE_ID = "id";

        final String REVIEW_ID = "id";
        final String AUTHOR = "author";
        final String CONTENT = "content";
        final String URL = "url";
        movieReviewArrayList.clear();

        try {
            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            String movie_id = moviesJson.getString(MOVIE_ID);

            JSONArray reviewArray = moviesJson.getJSONArray(RESULTS);

            for (int i = 0; i < reviewArray.length(); i++) {
                JSONObject movieJSONObject = reviewArray.getJSONObject(i);
                String author;
                String content;

                // Get the JSON object representing the review


                author = movieJSONObject.getString("author");
                content = movieJSONObject.getString("content");

                MovieReview movieReview = new MovieReview(movie_id,author, content);


                movieReviewArrayList.add(movieReview);
                }


        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();

        }
        return movieReviewArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieReview> movieReviewArrayList) {

        if(movieReviewArrayList.size()==0)
        {
            int duration = Toast.LENGTH_LONG;
            text ="Unable to fetch movie reviews,Sorry for the inconvinience";
            toast = Toast.makeText(mContext, text, duration);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
        }

        super.onPostExecute(movieReviewArrayList);
        reviewAdapter.notifyDataSetChanged();



    }
}// end of review Task





