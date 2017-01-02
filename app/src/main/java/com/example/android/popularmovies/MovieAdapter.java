package com.example.android.popularmovies;

import android.app.Activity;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by cheny on 2016/12/5.
 */

public class MovieAdapter extends ArrayAdapter<MovieInfo> {

    public MovieAdapter(Context context, List<MovieInfo> movieInfo) {
        super(context, 0, movieInfo);
    }

    static class ViewHolder {
        ImageView ImageItem;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ImageItem = (ImageView) convertView.findViewById(R.id.list_item_movie_imageView);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MovieInfo currentMovie = getItem(position);
        if(currentMovie !=null ) {
            String imageUrl_String = currentMovie.getPosterUrl();
            Picasso.with(getContext()).load(imageUrl_String).into(viewHolder.ImageItem );
        }
        return convertView;
    }
}
