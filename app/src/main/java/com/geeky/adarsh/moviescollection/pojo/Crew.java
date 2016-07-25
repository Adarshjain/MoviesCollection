package com.geeky.adarsh.moviescollection.pojo;


public class Crew {
    private String Name = null;
    private Long Id = -1L;
    private String Job = null;
    private String ProfilePath = null;

    public Crew(String dept, Long id, String job, String profilePath) {
        this.Name = dept;
        this.Id = id;
        this.Job = job;
        this.ProfilePath = profilePath;
    }

    public String getDept() {
        return Name;
    }

    public Long getId() {
        return Id;
    }

    public String getJob() {
        return Job;
    }

    public String getProfilePath() {
        return ProfilePath;
    }
}
