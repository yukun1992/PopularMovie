package com.example.android.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.N;

/**
 * Created by cheny on 2016/12/5.
 */

public class MovieLoader extends AsyncTaskLoader<List<MovieInfo>> {
    private String mUrl;
    private static final String API_KEY ="ae3e5545ff8e675002634536af6905d4";


    public MovieLoader(Context context , String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieInfo> loadInBackground() {
        String flag = "favorite";
        if(mUrl.equals(flag)) {
            Context context = getContext();
            SharedPreferences sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE);
            Map<String,?> keys = sharedPreferences.getAll();
            ArrayList<Integer> movieId = new ArrayList<>();
            for(Map.Entry<String,?> entry : keys.entrySet()){
                String result = entry.getValue().toString();
                movieId.add(Integer.parseInt(result));
            }
            Collections.sort(movieId);
            ArrayList<String> movieStringId = new ArrayList<>();
            for(Integer i : movieId) {
                movieStringId.add(i.toString());
            }
            movieId.clear();
            if(movieStringId.size() == 0) {
                return null;
            }
            List<MovieInfo> movieInfo = new ArrayList<>();
            for(int i = 0; i < movieStringId.size(); i++) {
                String urlId = movieStringId.get(i);
                String myFavoriteMovieUrl = "http://api.themoviedb.org/3/movie/"+  urlId +"?api_key="
                        + API_KEY;
                MovieInfo movie = QueryUtils.getFavoriteMovie(myFavoriteMovieUrl, urlId);
                String review = QueryUtils.getReviewFromJson(urlId);
                String trailer = QueryUtils.getTrailerFromJson(urlId);
                movie.setReview(review);
                movie.setTrailer(trailer);
                movieInfo.add(movie);
            }
            return movieInfo ;
        } else{
            List<MovieInfo> movieInfo = QueryUtils.fetchMovieData(mUrl);
            if (movieInfo.size() > 0) {
                for(int i = 0; i < movieInfo.size(); i++) {
                    MovieInfo movie = movieInfo.get(i);
                    String id = movie.getId();
                    String review = QueryUtils.getReviewFromJson(id);
                    String trailer = QueryUtils.getTrailerFromJson(id);
                    movie.setReview(review);
                    movie.setTrailer(trailer);
                }
                return movieInfo ;
            }
        }
        return null;
    }
}
