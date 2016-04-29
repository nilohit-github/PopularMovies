package com.appguru.android.popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

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

            movieName.setText(popularMovie.getMovieName());
            synopsis.setText(popularMovie.getOverView());
            String popularity = popularMovie.getRating();
            if (popularity.length() > 1) {
                popularity = (popularity.substring(0, 3));
            }

            rating.setText("Popularity  " + popularity + "/10");
            releaseDate.setText("Release Date  " + (popularMovie.getReleaseDate()).substring(0, 4));

            Picasso.with(getContext()).load(popularMovie.getPosterUrl()).into(imageView);

        }

        return rootView;
    }
}
