package com.appguru.android.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appguru.android.popularmovies.data.MovieContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    ImageButton mImageButton;
    String mMovieName;
    String mPopularity;
    String mReleaseDate;
    String mSynopsis;
    String mMovieID;
    String mMoviePoster;
    TrailerAdapter trailerAdapter;
    private ListView mMovieTrailersListView = null;
    public ArrayList<MovieTrailer> movieTrailerArrayList;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {

            PopularMovie popularMovie = (PopularMovie) intent.getParcelableExtra(Intent.EXTRA_TEXT);
            TextView movieName = (TextView) rootView.findViewById(R.id.movieName);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.movieThumbnail);
            TextView synopsis = (TextView) rootView.findViewById(R.id.overView);
            TextView rating = (TextView) rootView.findViewById(R.id.rating);
            TextView releaseDate = (TextView) rootView.findViewById(R.id.releaseDate);
            mMovieTrailersListView = (ListView) rootView.findViewById(R.id.listview_trailers);
            movieTrailerArrayList = new ArrayList<MovieTrailer>();
            trailerAdapter = new TrailerAdapter(getContext(), R.layout.fragment_detail,movieTrailerArrayList );

            mMovieName = popularMovie.getMovieName();
            mMovieID = popularMovie.getId();
            mSynopsis = popularMovie.getOverView();
            mPopularity = popularMovie.getRating();
            mReleaseDate = popularMovie.getReleaseDate();
            mMoviePoster = popularMovie.getPosterUrl();
            movieName.setText(mMovieName);
            synopsis.setText(mSynopsis);

            if (mPopularity.length() > 1) {
                mPopularity = (mPopularity.substring(0, 3));
            }

            rating.setText("Popularity  " + mPopularity + "/10");
            releaseDate.setText("Release Date  " + (mReleaseDate).substring(0, 4));

            Picasso.with(getContext()).load(popularMovie.getPosterUrl()).into(imageView);
            mImageButton = (ImageButton) rootView.findViewById(R.id.favorite);
        }

        if(mMovieID != null)
        {
            FetchMovieTrailer fetchMovieTrailer = new FetchMovieTrailer( getContext(),mMovieID ,movieTrailerArrayList,trailerAdapter);
            fetchMovieTrailer.execute();
            mMovieTrailersListView.setAdapter(trailerAdapter);
        }

        mMovieTrailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                trailerAdapter.getItem(position);
                MovieTrailer movieTrailer = trailerAdapter.getItem(position);
                String movie_id_youtube = "http://www.youtube.com/watch?v=" + movieTrailer.getKEY();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie_id_youtube));

                startActivity(intent);
            }
        });

        addFavourites();
        return rootView;
    }

    private void addFavourites()
    {
        mImageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri movieUri = MovieContract.MovieList.buildMovieIDURI(mMovieID);
                        Cursor favMovieCursor = getContext().getContentResolver().query(
                                movieUri,
                                new String[]{MovieContract.MovieList._ID},
                                MovieContract.MovieList.COLUMN_MOVIE_ID + " = ?",
                                new String[]{mMovieID},
                                null);

                        if (favMovieCursor.moveToFirst()) {
                            int movieIDIndex = favMovieCursor.getColumnIndex(MovieContract.MovieList._ID);
                            int deletedCount = getContext().getContentResolver().delete(movieUri,
                                    MovieContract.MovieList.COLUMN_MOVIE_ID + " = ?",
                                    new String[]{mMovieID});

                            Log.v("rows deleted ::::", "::::" + deletedCount);
                            Toast.makeText(getActivity(), "Removed from Favourite", Toast.LENGTH_SHORT)
                                    .show();
                            favMovieCursor.close();
                        } else {
                            ContentValues values = new ContentValues();
                            values.put(MovieContract.MovieList.COLUMN_MOVIE_ID, mMovieID);
                            values.put(MovieContract.MovieList.COLUMN_MOVIE_NAME, mMovieName);
                            values.put(MovieContract.MovieList.COLUMN_MOVIE_POSTER, mMoviePoster);
                            values.put(MovieContract.MovieList.COLUMN_MOVIE_SYNOPSIS, mSynopsis);
                            values.put(MovieContract.MovieList.COLUMN_MOVIE_RATING, mPopularity);
                            values.put(MovieContract.MovieList.COLUMN_MOVIE_RELEASE_DATE, mReleaseDate);

                            Log.v("inserted movied id", "value::" + mMovieID);
                            Log.v("inserted movied name", "value::" + mMovieName);


                            Uri inserteduri = getContext().getContentResolver().insert(MovieContract.MovieList.CONTENT_URI, values);
                            Log.v("inserted uri", "value::" + inserteduri.toString());
                            Toast.makeText(getActivity(), "Added to Favorite", Toast.LENGTH_SHORT)
                                    .show();

                        }


                    }
                }

        );
                }


    }
