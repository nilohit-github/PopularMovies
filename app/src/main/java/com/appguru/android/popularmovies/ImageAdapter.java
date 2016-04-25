package com.appguru.android.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jhani on 4/22/2016.
 */
public class ImageAdapter extends ArrayAdapter<PopularMovie> {
    private Context context;
    int layoutResourceId;
    ArrayList<PopularMovie> popularMovieArrayList ;
    int listOfMovies=0 ;



    public ImageAdapter(Context context, int resource, ArrayList<PopularMovie> popularMovieArrayList) {
        super(context, resource, popularMovieArrayList);
        this.context=context;
        this.layoutResourceId=layoutResourceId;
        this.popularMovieArrayList=popularMovieArrayList;
    }


    public int getCount() {


    if(popularMovieArrayList!=null)
        {
            listOfMovies = popularMovieArrayList.size();
        }


        return listOfMovies;
    }



    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        PopularMovie popularMovie =getItem(position);
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);

           // imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setLayoutParams(new GridView.LayoutParams(500, 500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);
        Picasso.with(getContext()).load(popularMovie.getPosterUrl()).into(imageView);
        return imageView;
    }


}