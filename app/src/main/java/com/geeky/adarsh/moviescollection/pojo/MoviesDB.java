package com.geeky.adarsh.moviescollection.pojo;

/**
 * Created by geekyAdarsh on 10-07-2016.
 */

public class MoviesDB {
    private String Title;
    private String PosterPath;
    private String Overview;
    private String ReleaseDate;
    private String Id;
    private Float Rating;
    private String RatingCount;

    public MoviesDB(String title, String posterPath, String overview, String releaseDate, String id, Float rating, String ratingCount) {
        this.Title = title;
        this.PosterPath = posterPath;
        this.Overview = overview;
        this.ReleaseDate = releaseDate;
        this.Id = id;
        this.Rating = rating;
        this.RatingCount = ratingCount;
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
}
