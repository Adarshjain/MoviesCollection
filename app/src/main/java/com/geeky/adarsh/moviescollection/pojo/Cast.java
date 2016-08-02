package com.geeky.adarsh.moviescollection.pojo;


public class Cast {
    private String Character = null;
    private String Id = null;
    private String Name = null;
    private String ProfilePath = null;

    public Cast(String character, String id, String name, String profilePath) {
        this.Character = character;
        this.Id = id;
        this.Name = name;
        this.ProfilePath = profilePath;
    }

    public String getCharacter() {
        return Character;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getProfilePath() {
        return ProfilePath;
    }
}
