package com.geeky.adarsh.moviescollection.pojo;


import android.os.Parcel;
import android.os.Parcelable;

public class Cast implements Parcelable {
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

    private Cast(Parcel in) {
        Character = in.readString();
        Id = in.readString();
        Name = in.readString();
        ProfilePath = in.readString();
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Character);
        parcel.writeString(Id);
        parcel.writeString(Name);
        parcel.writeString(ProfilePath);
    }
}
