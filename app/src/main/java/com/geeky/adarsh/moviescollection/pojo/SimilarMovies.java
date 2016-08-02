package com.geeky.adarsh.moviescollection.pojo;


import android.os.Parcel;
import android.os.Parcelable;

public class SimilarMovies implements Parcelable {
    private String ID = null;
    private String Title = null;
    private String PosterPath = null;
    private String BackDropPath = null;

    public SimilarMovies(String id, String title, String posterPath, String BackDropPath) {
        this.ID = id;
        this.Title = title;
        this.PosterPath = posterPath;
        this.BackDropPath = BackDropPath;
    }

    private SimilarMovies(Parcel in) {
        ID = in.readString();
        Title = in.readString();
        PosterPath = in.readString();
        BackDropPath = in.readString();
    }

    public static final Creator<SimilarMovies> CREATOR = new Creator<SimilarMovies>() {
        @Override
        public SimilarMovies createFromParcel(Parcel in) {
            return new SimilarMovies(in);
        }

        @Override
        public SimilarMovies[] newArray(int size) {
            return new SimilarMovies[size];
        }
    };

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return Title;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public String getBackDropPath() {
        return BackDropPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(Title);
        parcel.writeString(PosterPath);
        parcel.writeString(BackDropPath);
    }
}
