package com.example.android.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.N;

/**
 * Created by cheny on 2016/12/5.
 */

public class MoiveLoader extends AsyncTaskLoader<List<MovieInfo>> {
    private String mUrl;
    public MoiveLoader(Context context , String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieInfo> loadInBackground() {
        List<MovieInfo> movieInfo = QueryUtils.fetchMoiveData(mUrl);
        return movieInfo ;
    }
}
