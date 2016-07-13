package com.geeky.adarsh.moviescollection.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.geeky.adarsh.moviescollection.DisplayMovie;
import com.geeky.adarsh.moviescollection.R;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;
import com.geeky.adarsh.moviescollection.pojo.MoviesDB;

import java.util.ArrayList;


public class BoxOfficeAdapter extends RecyclerView.Adapter<BoxOfficeAdapter.ViewHolderBoxOffice> {

    private LayoutInflater mLayoutInflater;
    private ArrayList<MoviesDB> myMoviesDb = new ArrayList<>();
    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();

    public BoxOfficeAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setMoviesList(ArrayList<MoviesDB> moviesList) {
        this.myMoviesDb = moviesList;
        notifyItemRangeChanged(0, moviesList.size());
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.card_view, parent, false);
        return new ViewHolderBoxOffice(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        MoviesDB currentMovie = myMoviesDb.get(position);
        final String movieTitile = currentMovie.getTitle();
        final String releaseDate = currentMovie.getReleaseDate();
        final Float rating = currentMovie.getRating();
        final String Url = currentMovie.getPosterPath();
        @SuppressLint("DefaultLocale") final String rating2 = String.format("%.02f", rating) + " (" + currentMovie.getRatingCount() + ")";
        holder.movieTitle.setText(movieTitile);
        holder.releaseDate.setText(releaseDate);
        holder.ratingBar.setRating(rating);
        holder.rating2.setText(rating2);
        holder.poster.setImageUrl(Url, mImageLoader);
        holder.llview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c = view.getContext();
                Bundle b = new Bundle();
                b.putString("movieTitle", movieTitile);
                b.putString("releaseDate", releaseDate);
                b.putFloat("rating", rating);
                b.putString("rating2", rating2);
                b.putString("posterPath", Url);
                holder.poster.buildDrawingCache();
                Bitmap bitmap = holder.poster.getDrawingCache();
                Intent intent = new Intent(c, DisplayMovie.class);
                intent.putExtra("BitmapImage", bitmap);
                intent.putExtras(b);
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myMoviesDb.size();
    }

    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder {

        TextView movieTitle, releaseDate, rating2;
        RatingBar ratingBar;
        NetworkImageView poster;
        LinearLayout llview;

        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            llview = (LinearLayout) itemView.findViewById(R.id.onclickLayout);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            releaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            rating2 = (TextView) itemView.findViewById(R.id.rating2);
            ratingBar = (RatingBar) itemView.findViewById(R.id.movieRatingBar);
            poster = (NetworkImageView) itemView.findViewById(R.id.movieImage);
        }
    }
}
