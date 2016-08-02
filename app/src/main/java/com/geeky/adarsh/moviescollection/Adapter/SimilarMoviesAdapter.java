package com.geeky.adarsh.moviescollection.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.geeky.adarsh.moviescollection.DisplayMovie;
import com.geeky.adarsh.moviescollection.R;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;
import com.geeky.adarsh.moviescollection.pojo.SimilarMovies;

import java.util.ArrayList;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.SimilarViewHolder> {

    private LayoutInflater mLayoutInflater;
    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();
    private ArrayList<SimilarMovies> SimilarmoviesList = new ArrayList<>();
    private Context context;

    public SimilarMoviesAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setMoviesList(ArrayList<SimilarMovies> SimilarmoviesList) {
        this.SimilarmoviesList = SimilarmoviesList;
        notifyItemRangeChanged(0, SimilarmoviesList.size());
    }

    @Override
    public SimilarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.similar_movies_grid_view, parent, false);
        return new SimilarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimilarViewHolder holder, int position) {
        SimilarMovies current = SimilarmoviesList.get(position);
        final String ID = current.getID();
        final String Title = current.getTitle();
        final String BackDropPath = current.getBackDropPath();
        final String PosterPath = "http://image.tmdb.org/t/p/w150" + current.getPosterPath();

        holder.Poster.setImageUrl(PosterPath, mImageLoader);
        holder.MovieName.setText(Title);
        holder.OnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context != null) {
                    Bundle b = new Bundle();
                    b.putString("movieTitle", Title);
                    b.putString("backdropPath", "http://image.tmdb.org/t/p/w500" + BackDropPath);
                    b.putString("posterPath", "http://image.tmdb.org/t/p/w300" + PosterPath);
                    b.putString("id", ID);
                    ((DisplayMovie) context).changeFrag(b, 0);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return SimilarmoviesList.size();
    }

    static class SimilarViewHolder extends RecyclerView.ViewHolder {
        CardView SimilarMoviesCard;
        NetworkImageView Poster;
        TextView MovieName;
        LinearLayout OnClick;

        public SimilarViewHolder(View itemView) {
            super(itemView);
            SimilarMoviesCard = (CardView) itemView.findViewById(R.id.simMov);
            Poster = (NetworkImageView) itemView.findViewById(R.id.simMovPoster);
            MovieName = (TextView) itemView.findViewById(R.id.simMovName);
            OnClick = (LinearLayout) itemView.findViewById(R.id.simMovOnClick);
        }
    }
}
