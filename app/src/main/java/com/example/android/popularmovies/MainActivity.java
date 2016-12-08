package com.example.android.popularmovies;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieInfo>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    /*  fill out the API_KEY */
    private static final String API_KEY ="";


    private static final String REQUEST_URL_POPULAR1 = "http://api.themoviedb.org/3/movie/";
    private static final String REQUEST_URL_POPULAR2 = "?language=zh&api_key=";

    private String original_order = "top_rated";

    /* count the times loader load */
    private int loader_count = 0;

    private static final int Movie_LOADER_ID = 1;

    private MoiveAdapter mAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView movieGridView = (GridView) findViewById(R.id.gridView_movie);

        mAdapter = new MoiveAdapter(this, new ArrayList<MovieInfo>());

        movieGridView.setAdapter(mAdapter);

        movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                MovieInfo movie = mAdapter.getItem(position);
                intent.putExtra("key", movie);
                startActivity(intent);

            }
        });

        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(Movie_LOADER_ID, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<List<MovieInfo>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);


        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        if(original_order.equals(orderBy) && loader_count != 0 ) {
            return null;
        }

        loader_count++;
        original_order = orderBy;

        return new MoiveLoader(this, REQUEST_URL_POPULAR1 + orderBy  +REQUEST_URL_POPULAR2 + API_KEY);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieInfo>> loader, List<MovieInfo> data) {
        mAdapter.clear();

        if(data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<MovieInfo>> loader) {
        mAdapter.clear();
    }
}
