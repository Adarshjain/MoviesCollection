package com.geeky.adarsh.moviescollection.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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
        View view = mLayoutInflater.inflate(R.layout.grid_view, parent, false);
        return new ViewHolderBoxOffice(view);
    }

//    @SuppressLint("SimpleDateFormat")
//    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//    @SuppressLint("SimpleDateFormat")
//    private SimpleDateFormat df = new SimpleDateFormat("yyyy");

    //    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        MoviesDB currentMovie = myMoviesDb.get(position);
//        String releaseDate = currentMovie.getReleaseDate();
        final String movieTitle = currentMovie.getTitle();
//        try {
//            Date date = formatter.parse(currentMovie.getReleaseDate());
//            releaseDate = df.format(date);
//
//        } catch (ParseException ignored) {}
//        final Float rating = currentMovie.getRating();
        final String Url = currentMovie.getPosterPath();
        final String id = currentMovie.getId();
//        final String rating2 = " (" + currentMovie.getRatingCount() + ")";
//        holder.movieTitle.setText(movieTitile);

//        holder.releaseDate.setText(releaseDate);
//        holder.rating.setText(rating.toString());
//        holder.rating2.setText(rating2);
//        holder.poster.buildDrawingCache();
//        Bitmap bit = holder.poster.getDrawingCache();
//        if (bit != null) {
//            Palette.from(bit).clearFilters().generate(new Palette.PaletteAsyncListener() {
//                @Override
//                public void onGenerated(Palette palette) {
//                    color[0] = palette.getMutedColor(0x000000);
//                    holder.paletteFrame.setBackgroundColor(color[0]);
//                }
//            });
//        }else{
//            color[0] = 0x000000;
//            holder.paletteFrame.setBackgroundColor(color[0]);
//        }

        holder.poster.setImageUrl("http://image.tmdb.org/t/p/w300" + Url, mImageLoader);
        holder.llview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c = view.getContext();
                Bundle b = new Bundle();
                b.putString("movieTitle", movieTitle);
//                b.putString("releaseDate", releaseDate);
//                b.putFloat("rating", rating);
//                b.putString("rating2", rating2);
                b.putString("posterPath", "http://image.tmdb.org/t/p/original" + Url);
                b.putString("id", id);
//                b.putInt("titleBarColor", color[0]);
                Intent intent = new Intent(c, DisplayMovie.class);
//                intent.putExtra("BitmapImage", bitmap);
                intent.putExtras(b);

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

        //        TextView movieTitle, releaseDate;
        //                , rating2, rating, genre;
        NetworkImageView poster;
        RelativeLayout llview;
//        LinearLayout paletteFrame;

        ViewHolderBoxOffice(View itemView) {
            super(itemView);
            llview = (RelativeLayout) itemView.findViewById(R.id.onclickLayout);
//            paletteFrame = (LinearLayout) itemView.findViewById(R.id.palette_frame);
//            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
//            releaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
//            rating2 = (TextView) itemView.findViewById(R.id.rating2);
//            rating = (TextView) itemView.findViewById(R.id.rating);
            poster = (NetworkImageView) itemView.findViewById(R.id.movieImage);
//            genre = (TextView) itemView.findViewById(R.id.genre);
        }
    }
}