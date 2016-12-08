package com.example.android.popularmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by cheny on 2016/12/5.
 */

public class MoiveAdapter extends ArrayAdapter<MovieInfo> {


    public MoiveAdapter(Context context, List<MovieInfo> movieInfo) {
        super(context, 0, movieInfo);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        MovieInfo currentMovie = getItem(position);

        /*   get section name   */
        ImageView image = (ImageView) listItemView.findViewById(R.id.list_item_movie_imageView);
        String imageUrl_String = currentMovie.getPosterUrl();


        Picasso.with(getContext()).load(imageUrl_String).into(image);

        return listItemView;
    }

}
