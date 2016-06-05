package com.appguru.android.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jhani on 4/22/2016.
 */
public class ReviewAdapter extends ArrayAdapter<MovieReview> {
    private Context context;
    int layoutResourceId;
    ArrayList<MovieReview> movieReviewArrayList;
    int listOfReview = 0;
    private LayoutInflater inflater;


    public ReviewAdapter(Context context, int resource, ArrayList<MovieReview> movieReviewArrayList) {
        super(context, resource, movieReviewArrayList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.movieReviewArrayList = movieReviewArrayList;
        inflater = LayoutInflater.from(context);
    }

    public static class ViewHolder {
        public static TextView trailerTextView;

        public ViewHolder(View view) {
            trailerTextView = (TextView) view.findViewById(R.id.title_textview);
        }
    }



    public int getCount() {


        if (movieReviewArrayList != null) {
            listOfReview = movieReviewArrayList.size();
        }
        return listOfReview;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.review_list, parent, false);

            ((TextView) convertView.findViewById(R.id.reviewer_textview)).setText(movieReviewArrayList.get(position).getAUTHOR());
            ((TextView) convertView.findViewById(R.id.content_textview)).setText(movieReviewArrayList.get(position).getCONTENT());

        }

        return convertView;
    }


}