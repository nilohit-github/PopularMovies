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
public class TrailerAdapter extends ArrayAdapter<MovieTrailer> {
    private Context context;
    int layoutResourceId;
    ArrayList<MovieTrailer> movieTrailerArrayList;
    int listOfTrailer = 0;
    private LayoutInflater inflater;


    public TrailerAdapter(Context context, int resource, ArrayList<MovieTrailer> movieTrailerArrayList) {
        super(context, resource, movieTrailerArrayList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.movieTrailerArrayList = movieTrailerArrayList;
        inflater = LayoutInflater.from(context);
    }

    public static class ViewHolder {
        public static TextView trailerTextView;

        public ViewHolder(View view) {
            trailerTextView = (TextView) view.findViewById(R.id.title_textview);
        }
    }



    public int getCount() {


        if (movieTrailerArrayList != null) {
            listOfTrailer = movieTrailerArrayList.size();
        }
        return listOfTrailer;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.trailer_list, parent, false);

            ((TextView) convertView.findViewById(R.id.title_textview)).setText(movieTrailerArrayList.get(position).getTYPE());

        }

        return convertView;
    }


}