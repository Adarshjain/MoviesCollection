package com.geeky.adarsh.moviescollection.Adapter;


import android.app.Activity;
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
import com.geeky.adarsh.moviescollection.pojo.Cast;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private LayoutInflater mLayoutInflater;
    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();
    private ArrayList<Cast> CastList = new ArrayList<>();
    private Context context;

    public CastAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setCastList(ArrayList<Cast> castList) {
        this.CastList = castList;
        notifyItemRangeChanged(0, CastList.size());
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.cast_grid_view, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {
        Cast temp = CastList.get(position);
        String CastName = temp.getName();
        String CastCharacter = temp.getCharacter();
        String Poster = temp.getProfilePath();
        final String CastId = temp.getId();

        holder.CastName.setText(CastName);
        holder.CastCharacter.setText(CastCharacter);

        holder.Poster.setImageUrl("http://image.tmdb.org/t/p/w150" + Poster, mImageLoader);
        holder.Poster.setErrorImageResId(R.drawable.placeholderperson);

        holder.OnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("id", CastId);
                ((DisplayMovie) context).changeCastNCrew(b, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return CastList.size();
    }

    static class CastViewHolder extends RecyclerView.ViewHolder {
        CardView CastCard;
        NetworkImageView Poster;
        TextView CastName, CastCharacter;
        LinearLayout OnClick;

        public CastViewHolder(View itemView) {
            super(itemView);
            CastCard = (CardView) itemView.findViewById(R.id.cast);
            Poster = (NetworkImageView) itemView.findViewById(R.id.cast_poster);
            CastName = (TextView) itemView.findViewById(R.id.cast_name);
            CastCharacter = (TextView) itemView.findViewById(R.id.cast_character);
            OnClick = (LinearLayout) itemView.findViewById(R.id.cast_on_click);
        }
    }
}
