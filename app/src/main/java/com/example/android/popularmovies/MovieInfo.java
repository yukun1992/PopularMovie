package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cheny on 2016/12/5.
 */

public class MovieInfo implements Parcelable {
    private String mPoster_path;
    private String mOverview;
    private String mRelease_date;
    private String mPosterUrl;

    public MovieInfo(String mPoster_path, String mOverview, String mRelease_date, String mPosterUrl) {
        this.mPoster_path = mPoster_path;
        this.mOverview = mOverview;
        this.mRelease_date = mRelease_date;
        this.mPosterUrl = mPosterUrl;
    }

    protected MovieInfo(Parcel in) {
        mPoster_path = in.readString();
        mOverview = in.readString();
        mRelease_date = in.readString();
        mPosterUrl = in.readString();
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    public String getPoster_path() {
        return mPoster_path;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getRelease_date() {
        return mRelease_date;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPoster_path);
        dest.writeString(mOverview);
        dest.writeString(mRelease_date);
        dest.writeString(mPosterUrl);
    }
}
