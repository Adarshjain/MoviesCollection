package com.geeky.adarsh.moviescollection.pojo;


import android.os.Parcel;
import android.os.Parcelable;

public class Crew implements Parcelable {
    private String Job = null;
    private String Id = null;
    private String Name = null;
    private String ProfilePath = null;

    public Crew(String job, String id, String name, String profilePath) {
        this.Job = job;
        this.Id = id;
        this.Name = name;
        this.ProfilePath = profilePath;
    }

    private Crew(Parcel in) {
        Job = in.readString();
        Id = in.readString();
        Name = in.readString();
        ProfilePath = in.readString();
    }

    public static final Creator<Crew> CREATOR = new Creator<Crew>() {
        @Override
        public Crew createFromParcel(Parcel in) {
            return new Crew(in);
        }

        @Override
        public Crew[] newArray(int size) {
            return new Crew[size];
        }
    };

    public String getJob() {
        return Job;
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
        parcel.writeString(Job);
        parcel.writeString(Id);
        parcel.writeString(Name);
        parcel.writeString(ProfilePath);
    }
}
