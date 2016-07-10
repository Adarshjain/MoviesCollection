package com.geeky.adarsh.moviescollection.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.geeky.adarsh.moviescollection.AnimationUtils.AnimUtil;
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
        holder.movieTitle.setText(currentMovie.getTitle());
        holder.releaseDate.setText(currentMovie.getReleaseDate());
        holder.ratingBar.setRating(currentMovie.getRating());
        holder.rating2.setText(String.format("%.02f", currentMovie.getRating()) + " (" + currentMovie.getRatingCount() + ")");
        String Url = currentMovie.getPosterPath();
        mImageLoader.get(Url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.poster.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

//        if (position>0){
//            AnimUtil.animate(holder,true);
//        }else{
//            AnimUtil.animate(holder,false);
//        }
    }

    @Override
    public int getItemCount() {
        return myMoviesDb.size();
    }

    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder {

        TextView movieTitle, releaseDate, rating2;
        RatingBar ratingBar;
        ImageView poster;

        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            releaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            rating2 = (TextView) itemView.findViewById(R.id.rating2);
            ratingBar = (RatingBar) itemView.findViewById(R.id.movieRatingBar);
            poster = (ImageView) itemView.findViewById(R.id.movieImage);
        }
    }
}
