package com.geeky.adarsh.moviescollection.pojo;


public class SimilarMovies {
    private Long ID = -1L;
    private String Title = null;
    private String PosterPath = null;

    public SimilarMovies(Long id, String title, String posterPath) {
        this.ID = id;
        this.Title = title;
        this.PosterPath = posterPath;
    }

    public Long getID() {
        return ID;
    }

    public String getTitle() {
        return Title;
    }

    public String getPosterPath() {
        return PosterPath;
    }
}
