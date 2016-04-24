package com.appguru.android.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    ImageAdapter imageAdapter;
    //public ArrayList<MovieDetailsObject> mMovieDetailsArrayList = null;
    public ArrayList<PopularMovie> popularMovieArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
         setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(getContext(), R.layout.fragment_main,popularMovieArrayList);
        gridview.setAdapter(imageAdapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getPopularMovies();
    }

    public void getPopularMovies() {
        FetchPopularMovie fetchPopularMovie = new FetchPopularMovie();
        fetchPopularMovie.execute();
    }

    public class FetchPopularMovie extends AsyncTask {

            protected ArrayList<PopularMovie> doInBackground(Object[] params) {
            // These two need to be declared outside the try/catch
// so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


// Will contain the raw JSON response as a string.
            String movieJsonStr = null;


            try {

                String apiKey = BuildConfig.POPULAR_MOVIES_API_KEY;
                //   URL url = new URL(baseUrl.concat(apiKey));
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("movie")
                        .appendPath("popular")
                        .appendQueryParameter("api_key", apiKey);
                URL url = new URL(builder.build().toString());

                Log.v("get url val", "::::" + url);

                // Create the request to moviedb, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    movieJsonStr = null;
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
                    movieJsonStr = null;
                }
                movieJsonStr = buffer.toString();
                // Log.v(LOG_TAG, "json dt" + test);*/


            } catch (IOException e) {
                Log.e("Fetch Movie", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                movieJsonStr = null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Fetch movie", "Error closing stream", e);
                    }
                }
            }
            try {
                return getPopularMoviesJson(movieJsonStr);
            } catch (JSONException e) {
                //Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);

            if (result != null) {
                imageAdapter.clear();
                for (int i = 0; i < popularMovieArrayList.size(); ++i) {
                    imageAdapter.add(popularMovieArrayList.get(i));
                    //    Log.e("MOVIE_APP_3", mMovieDetailsArrayList.get(i).m_strMovieTitle);
                }
                imageAdapter.notifyDataSetChanged();
            }
        }


        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         * <p/>
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private ArrayList<PopularMovie> getPopularMoviesJson(String movieJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String poster_path = "backdrop_path";
            final String movie_id = "id";
            final String movie_title = "original_title";
            final String posterBasePath = "http://image.tmdb.org/t/p/w185/";
            final String movieOverview = "overview";
            final String releaseDate = "release_date";
            //ArrayList<PopularMovie> popularMovieArrayList = new ArrayList<PopularMovie>();


            JSONObject movieJsonObject = new JSONObject(movieJsonStr);
            JSONArray movieJsonObjectJSONArray = movieJsonObject.getJSONArray("results");
            Log.v("::::", "movie list entry: " + movieJsonObjectJSONArray.toString());


            //String[] resultStrs = new String[];
            for (int i = 0; i < movieJsonObjectJSONArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                JSONObject movieObject = movieJsonObjectJSONArray.getJSONObject(i);


                PopularMovie popularMovie = new PopularMovie();
                popularMovie.setPosterUrl(posterBasePath + movieObject.getString(poster_path));
                popularMovie.setId(movieObject.getString(movie_id));
                popularMovie.setMovieName(movieObject.getString(movie_title));
                popularMovie.setOverView(movieObject.getString(movieOverview));
                popularMovieArrayList.add(popularMovie);
                //String test= popularMovie.getPosterUrl();
                //Log.v("test::","postar url"+test);


                // Get the JSON object representing the day

            }


            return popularMovieArrayList;

        }


    }
}


