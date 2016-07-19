package com.geeky.adarsh.moviescollection.pojo;


public class Genre {
    private String Name = null;
    private Long Id = -1L;

    public Genre(String name, Long id) {
        this.Name = name;
        this.Id = id;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }
}
