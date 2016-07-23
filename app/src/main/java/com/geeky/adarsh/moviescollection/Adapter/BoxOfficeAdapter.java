package com.geeky.adarsh.moviescollection.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class BoxOfficeAdapter extends RecyclerView.Adapter<BoxOfficeAdapter.ViewHolderBoxOffice> {

    private LayoutInflater mLayoutInflater;
    private ArrayList<MoviesDB> myMoviesDb = new ArrayList<>();
    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();
    @SuppressLint("SimpleDateFormat")
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat df = new SimpleDateFormat("yyyy");


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

    //    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        MoviesDB currentMovie = myMoviesDb.get(position);
        String releaseDate = currentMovie.getReleaseDate();
        final String BackdropPath = currentMovie.getBackdropPath();
        final String movieTitle = currentMovie.getTitle();
        try {
            Date date = formatter.parse(currentMovie.getReleaseDate());
            releaseDate = df.format(date);

        } catch (ParseException ignored) {
        }
//        final Float rating = currentMovie.getRating();
        final String Url = currentMovie.getPosterPath();
        final String id = currentMovie.getId();
//        final String rating2 = " (" + currentMovie.getRatingCount() + ")";
        holder.movieTitle.setText(movieTitle);

        holder.releaseDate.setText(releaseDate);
        holder.poster.setImageUrl("http://image.tmdb.org/t/p/w300" + Url, mImageLoader);
        final Bitmap[] bit = new Bitmap[1];
        final int[] color = {0xff000000};
        holder.paletteFrame.setBackgroundColor(0xff000000);
        mImageLoader.get("http://image.tmdb.org/t/p/w300" + Url, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Response", "Error");
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Log.e("Volley Response", "Success");
                holder.poster.setImageBitmap(response.getBitmap());
                holder.poster.buildDrawingCache();
                bit[0] = holder.poster.getDrawingCache();
//        final Bitmap bit = holder.poster.getDrawingCache();
                if (bit[0] != null) {
                    Palette.from(bit[0]).clearFilters().generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Palette.Swatch vibrantDarkSwatch = palette.getDarkVibrantSwatch();
                            if (vibrantDarkSwatch != null)
                                color[0] = vibrantDarkSwatch.getRgb();
//                    Log.e("ColorB", "" + color[0]);
//                    color[0] = palette.getMutedColor(0xff000000);
//                    Log.e("ColorA", "" + color[0]);
                            holder.paletteFrame.setBackgroundColor(color[0]);
                        }
                    });
                }
            }
        });
//        holder.paletteFrame.setAlpha(0.7F);
//        holder.rating.setText(rating.toString());
//        holder.rating2.setText(rating2);

//        holder.poster.buildDrawingCache();


        holder.llview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c = view.getContext();
                Bundle b = new Bundle();
                b.putString("movieTitle", movieTitle);
                b.putString("backdropPath", "http://image.tmdb.org/t/p/w500" + BackdropPath);
                b.putString("posterPath", "http://image.tmdb.org/t/p/original" + Url);
                b.putString("id", id);
                b.putInt("colors", color[0]);
                Intent intent = new Intent(c, DisplayMovie.class);
                intent.putExtras(b);


//                b.putInt("titleBarColor", color[0]);
//                if (bit[0] != null) {
//                    Log.e("Volley Response", "Successive");
//                    intent.putExtra("BitmapImage", bit[0]);
//                }
//                b.putString("releaseDate", releaseDate);
//                b.putFloat("rating", rating);
//                b.putString("rating2", rating2);
//                setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.change_image_transform));
//                setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));
//
//                 Create new fragment to add (Fragment B)
//                Fragment fragment = new ImageFragment();
//                fragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.change_image_transform));
//                fragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));
//
//                 Our shared element (in Fragment A)
//                mProductImage   = (ImageView) mLayout.findViewById(R.id.product_detail_image);
//
//                 Add Fragment B
//                FragmentTransaction ft = getFragmentManager().beginTransaction()
//                        .replace(R.id.container, fragment)
//                        .addToBackStack("transaction")
//                        .addSharedElement(mProductImage, "MyTransition");
//                ft.commit();

//                holder.poster.setTransitionName("poster");
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) con,holder.poster,holder.poster.getTransitionName());
//                Log.e("Animation", "Success");
//                ActivityCompat.startActivity((Activity) con,intent,options.toBundle());,options.toBundle()
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