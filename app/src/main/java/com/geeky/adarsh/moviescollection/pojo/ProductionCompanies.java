package com.geeky.adarsh.moviescollection.pojo;


public class ProductionCompanies {
    private String Name = null;
    private Long Id = -1L;

    public ProductionCompanies(String name, Long id) {
        this.Name = name;
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public Long getId() {
        return Id;
    }
}
