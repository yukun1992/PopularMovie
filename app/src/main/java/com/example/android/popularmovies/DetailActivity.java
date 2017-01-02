package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.id;
import static android.os.Build.ID;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_imageView)
    ImageView image;
    @BindView(R.id.detail_textView_release_date)
    TextView textView_release_date;
    @BindView(R.id.detail_textView_overview)
    TextView textView_overview;
    @BindView(R.id.detail_textView_title)
    TextView textView_title;
    @BindView(R.id.detail_vote_average)
    TextView textView_vote_average;
    @BindView(R.id.detail_textView_comment)
    TextView textView_review;
    @BindView(R.id.add_favorite)
    CheckBox checkBox_add_favorite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        Intent intent = getIntent();

        if (intent.resolveActivity(getPackageManager()) != null) {

            MovieInfo movie = intent.getParcelableExtra(Intent.EXTRA_TEXT);

            String id = movie.getId();
            SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
            if(id.equals(sharedPreferences.getString(id, ""))){
                checkBox_add_favorite.setChecked(true);
            }

            String imageUrl_String = movie.getPosterUrl();
            Picasso.with(getBaseContext()).load(imageUrl_String).into(image);

            String title = movie.getTitle();
            textView_title.setText(title);

            String vote_average = movie.getVote_average();
            textView_vote_average.setText(vote_average);

            String release_date = movie.getRelease_date();
            textView_release_date.setText(release_date);

            String overview = movie.getOverview();
            textView_overview.setText(overview);

            String review = movie.getReview();
            if (review.length() != 0) {
                textView_review.setText(review);
            }

        }
    }

    public void add_favorite (View view) {
        Intent intent = getIntent();
        if (intent.resolveActivity(getPackageManager()) != null) {
            MovieInfo movie = intent.getParcelableExtra(Intent.EXTRA_TEXT);
            SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
            if(checkBox_add_favorite.isChecked()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(movie.getId(),movie.getId() );
                editor.commit();
            } else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(movie.getId());
                editor.commit();
            }
        }


    }

   public void play_trailer(View view) {
       Intent intent = getIntent();
       if (intent.resolveActivity(getPackageManager()) != null) {
           MovieInfo movie = intent.getParcelableExtra(Intent.EXTRA_TEXT);
           if(movie.getTrailer() == null && movie.getTrailer().length() == 0) {
               return;
           }
           Uri webPage = Uri.parse(movie.getTrailer());
           Intent trailerIntent = new Intent(Intent.ACTION_VIEW, webPage);
           if (trailerIntent.resolveActivity(getPackageManager()) != null) {
               startActivity(trailerIntent);
           }
       }
   }
}
