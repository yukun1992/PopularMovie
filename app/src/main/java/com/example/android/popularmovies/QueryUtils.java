package com.example.android.popularmovies;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.key;

/**
 * Created by cheny on 2016/12/5.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final String API_KEY ="ae3e5545ff8e675002634536af6905d4";

    public QueryUtils() {
    }

    public static List<MovieInfo> fetchMovieData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<MovieInfo> movieInfo = extractFeatureFromJson(jsonResponse);

        return movieInfo;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the  JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static String getReviewFromJson(String id) {

        String ReviewJson = "http://api.themoviedb.org/3/movie/"+ id+"/reviews?api_key=" + API_KEY;
        if(TextUtils.isEmpty(ReviewJson)){
            return null;
        }

        URL url = createUrl(ReviewJson );
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        String result = "";

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray resultsInfo = baseJsonResponse.getJSONArray("results");
            for (int i = 0; i < resultsInfo.length() && i < 2; i++ ) {
                JSONObject currentInfo = resultsInfo.getJSONObject(i);
                String review = currentInfo.getString("content");
                if(review != null && review.length()!= 0) {
                    result = review +"\n";
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  result;
    }

    public static MovieInfo getFavoriteMovie(String MovieUrl, String id) {
        if(TextUtils.isEmpty(MovieUrl)){
            return null;
        }
        URL url = createUrl(MovieUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        MovieInfo movieInfo = new MovieInfo(id);
        try {
            JSONObject currentInfo = new JSONObject(jsonResponse);

            String Id = currentInfo.getString("id");
            movieInfo.setId(Id);

            String Poster_path = currentInfo.getString("poster_path").substring(1);
            movieInfo.setPoster_path(Poster_path);

            String Overview = currentInfo.getString("overview");
            movieInfo.setOverview(Overview);

            String Release_date = currentInfo.getString("release_date");
            movieInfo.setRelease_date(Release_date);

            String Title = currentInfo.getString("title");
            movieInfo.setTitle(Title );

            String Vote_average = currentInfo.getString("vote_average");
            movieInfo.setVote_average(Vote_average);

            String Poster_path_part1 = "https://image.tmdb.org/t/p/w185/";
            String PosterUrl = Poster_path_part1 + Poster_path;
            movieInfo.setPosterUrl(PosterUrl);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieInfo;
    }

    public  static String getTrailerFromJson(String id) {
        String API_part = "&append_to_response=videos";
        String TrailerJson = "http://api.themoviedb.org/3/movie/"+ id+"?api_key=" + API_KEY +
                API_part;
        if(TextUtils.isEmpty(TrailerJson)){
            return null;
        }
        URL url = createUrl(TrailerJson);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        String result = "";
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONObject resultsInfo = baseJsonResponse.getJSONObject("videos");
            JSONArray videosInfo = resultsInfo.getJSONArray("results");
            for(int i = 0; i <videosInfo.length() && i < 1; i++) {
                JSONObject currentInfo = videosInfo .getJSONObject(i);
                String key = currentInfo.getString("key");
                result = "https://www.youtube.com/watch?v=" + key;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  result;
    }

    private static List<MovieInfo> extractFeatureFromJson(String movieJSON) {
        if(TextUtils.isEmpty(movieJSON)){
            return null;
        }

        List<MovieInfo> movieInfo = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(movieJSON);

            JSONArray resultsInfo = baseJsonResponse.getJSONArray("results");

            for (int i = 0; i < resultsInfo.length(); i++) {

                JSONObject currentInfo = resultsInfo.getJSONObject(i);

                String Id = currentInfo.getString("id");

                String Poster_path = currentInfo.getString("poster_path").substring(1);

                String Overview = currentInfo.getString("overview");

                String Release_date = currentInfo.getString("release_date");

                String Title = currentInfo.getString("title");

                String Vote_average = currentInfo.getString("vote_average");

                String Poster_path_part1 = "https://image.tmdb.org/t/p/w185/";

                String PosterUrl = Poster_path_part1 + Poster_path;


                MovieInfo movie = new MovieInfo(Id, Poster_path, Overview, Release_date, PosterUrl,
                        Title, Vote_average );

                movieInfo.add(movie);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the movie JSON results", e);
        }
        return movieInfo;
    }

}
