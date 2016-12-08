package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        MovieInfo movie = intent.getParcelableExtra("key");

        ImageView image = (ImageView) findViewById(R.id.detail_imageView);
        TextView textView_release_date = (TextView) findViewById(R.id.detail_textView_release_date);
        TextView textView_overview = (TextView) findViewById(R.id.detail_textView_overview);

        String imageUrl_String = movie.getPosterUrl();
        Picasso.with(getBaseContext()).load(imageUrl_String).into(image);

        String release_date = movie.getRelease_date();
        textView_release_date.setText(release_date );

        String overview = movie.getOverview();
        textView_overview.setText(overview);

    }
}
