package com.geeky.adarsh.moviescollection.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
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
    private ImageLoader mImageLoaderx = mVolleySingleton.getImageLoader();

    public BoxOfficeAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setMoviesList(ArrayList<MoviesDB> moviesList) {
        this.myMoviesDb = moviesList;
        notifyItemRangeChanged(0, moviesList.size());
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.grid_view, parent, false);
        return new ViewHolderBoxOffice(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        MoviesDB currentMovie = myMoviesDb.get(position);
        final String movieTitile = currentMovie.getTitle();
        final String releaseDate = currentMovie.getReleaseDate();
        final Float rating = currentMovie.getRating();
        final String Url = currentMovie.getPosterPath();
        final String rating2 = " (" + currentMovie.getRatingCount() + ")";
        final int[] color = {0x000000};
        holder.movieTitle.setText(movieTitile);
        holder.releaseDate.setText(releaseDate);
//        holder.rating.setText(rating.toString());
//        holder.rating2.setText(rating2);
        holder.poster.setImageUrl(Url, mImageLoader);
        mImageLoaderx.get(Url, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    Palette.from(response.getBitmap()).clearFilters().generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            color[0] = palette.getMutedColor(0x88000000);
                            holder.paletteFrame.setBackgroundColor(color[0]);
                        }
                    });
                }
            }
        });
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
                b.putInt("titleBarColor", color[0]);

                Intent intent = new Intent(c, DisplayMovie.class);
//                intent.putExtra("BitmapImage", bitmap);
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

        TextView movieTitle, releaseDate;
        //                , rating2, rating, genre;
        NetworkImageView poster;
        RelativeLayout llview;
        LinearLayout paletteFrame;

        ViewHolderBoxOffice(View itemView) {
            super(itemView);
            llview = (RelativeLayout) itemView.findViewById(R.id.onclickLayout);
            paletteFrame = (LinearLayout) itemView.findViewById(R.id.palette_frame);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            releaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
//            rating2 = (TextView) itemView.findViewById(R.id.rating2);
//            rating = (TextView) itemView.findViewById(R.id.rating);
            poster = (NetworkImageView) itemView.findViewById(R.id.movieImage);
//            genre = (TextView) itemView.findViewById(R.id.genre);
        }
    }
}