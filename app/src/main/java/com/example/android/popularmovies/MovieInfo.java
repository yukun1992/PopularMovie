package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheny on 2016/12/5.
 */

public class MovieInfo implements Parcelable {
    private String mId;
    private String mPoster_path;
    private String mOverview;
    private String mRelease_date;
    private String mPosterUrl;
    private String mTitle;
    private String mVote_average;
    private String mReview;
    private String mTrailer;


    public MovieInfo(String mId) {
        this.mId = mId;
    }

    public MovieInfo(String mId, String mPoster_path, String mOverview, String mRelease_date,
                     String mPosterUrl, String mTitle, String mVote_average) {
        this.mId = mId;
        this.mPoster_path = mPoster_path;
        this.mOverview = mOverview;
        this.mRelease_date = mRelease_date;
        this.mPosterUrl = mPosterUrl;
        this.mTitle = mTitle;
        this.mVote_average = mVote_average;
    }

    public MovieInfo(String mId, String mPoster_path, String mOverview, String mRelease_date,
                     String mPosterUrl, String mTitle, String mVote_average, String mReview, String mTrailer) {
        this.mId = mId;
        this.mPoster_path = mPoster_path;
        this.mOverview = mOverview;
        this.mRelease_date = mRelease_date;
        this.mPosterUrl = mPosterUrl;
        this.mTitle = mTitle;
        this.mVote_average = mVote_average;
        this.mReview = mReview;
        this.mTrailer = mTrailer;
    }

    protected MovieInfo(Parcel in) {
        mId = in.readString();
        mPoster_path = in.readString();
        mOverview = in.readString();
        mRelease_date = in.readString();
        mPosterUrl = in.readString();
        mTitle = in.readString();
        mVote_average = in.readString();
        mReview = in.readString();
        mTrailer = in.readString();

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

    public String getId() {
        return mId;
    }

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

    public String getTitle() {
        return mTitle;
    }

    public String getVote_average() {
        return mVote_average;
    }

    public String getReview() {
        return mReview;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public void setPoster_path(String mPoster_path) {
        this.mPoster_path = mPoster_path;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public void setRelease_date(String mRelease_date) {
        this.mRelease_date = mRelease_date;
    }

    public void setPosterUrl(String mPosterUrl) {
        this.mPosterUrl = mPosterUrl;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setVote_average(String mVote_average) {
        this.mVote_average = mVote_average;
    }

    public void setReview(String mReview) {
        this.mReview = mReview;
    }

    public String getTrailer() {
        return mTrailer;
    }

    public void setTrailer(String mTrailer) {
        this.mTrailer = mTrailer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mPoster_path);
        dest.writeString(mOverview);
        dest.writeString(mRelease_date);
        dest.writeString(mPosterUrl);
        dest.writeString(mTitle);
        dest.writeString(mVote_average);
        dest.writeString(mReview);
        dest.writeString(mTrailer);
    }
}
