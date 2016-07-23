package com.geeky.adarsh.moviescollection.pojo;


import android.os.Parcel;
import android.os.Parcelable;

public class MoviesDB implements Parcelable {
    public static final Creator<MoviesDB> CREATOR = new Creator<MoviesDB>() {
        @Override
        public MoviesDB createFromParcel(Parcel in) {
            return new MoviesDB(in);
        }

        @Override
        public MoviesDB[] newArray(int size) {
            return new MoviesDB[size];
        }
    };
    private String Title;
    private String PosterPath;
    private String Overview;
    private String ReleaseDate;
    private String Id;
    private Float Rating;
    private String RatingCount;
    private String BackdropPath;

    public MoviesDB(String title, String posterPath, String overview, String releaseDate, String id, Float rating, String ratingCount, String backdropPath) {
        this.Title = title;
        this.PosterPath = posterPath;
        this.Overview = overview;
        this.ReleaseDate = releaseDate;
        this.Id = id;
        this.Rating = rating;
        this.RatingCount = ratingCount;
        this.BackdropPath = backdropPath;
    }

    protected MoviesDB(Parcel in) {
        Title = in.readString();
        PosterPath = in.readString();
        Overview = in.readString();
        ReleaseDate = in.readString();
        Id = in.readString();
        RatingCount = in.readString();
        Rating = Float.parseFloat(in.readString());
        BackdropPath = in.readString();
    }

    public String getTitle() {
        return Title;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public String getOverview() {
        return Overview;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public String getId() {
        return Id;
    }

    public Float getRating() {
        return Rating;
    }

    public String getRatingCount() {
        return RatingCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Title);
        parcel.writeString(PosterPath);
        parcel.writeString(Overview);
        parcel.writeString(ReleaseDate);
        parcel.writeString(Id);
        parcel.writeString(RatingCount);
        parcel.writeString(Rating.toString());
        parcel.writeString(BackdropPath);
    }

    public String getBackdropPath() {
        return BackdropPath;
    }
}
