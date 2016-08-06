package com.geeky.adarsh.moviescollection.Adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.geeky.adarsh.moviescollection.R;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;
import com.geeky.adarsh.moviescollection.pojo.Crew;

import java.util.ArrayList;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {

    private LayoutInflater mLayoutInflater;
    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();
    private ArrayList<Crew> CrewList = new ArrayList<>();
    private Context context;

    public CrewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setCrewList(ArrayList<Crew> crewList) {
        this.CrewList = crewList;
        notifyItemRangeChanged(0, CrewList.size());
    }

    @Override
    public CrewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.crew_grid_view, parent, false);
        return new CrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CrewViewHolder holder, int position) {
        Crew temp = CrewList.get(position);
        String CrewName = temp.getName();
        String CrewCharacter = temp.getJob();
        String Poster = temp.getProfilePath();
        String CrewId = temp.getId();

        holder.CrewName.setText(CrewName);
        holder.CrewCharacter.setText(CrewCharacter);
        if (Poster != null)
            holder.Poster.setImageUrl("http://image.tmdb.org/t/p/w150" + Poster, mImageLoader);

    }

    @Override
    public int getItemCount() {
        return CrewList.size();
    }

    static class CrewViewHolder extends RecyclerView.ViewHolder {
        CardView CrewCard;
        NetworkImageView Poster;
        TextView CrewName, CrewCharacter;
        LinearLayout OnClick;

        public CrewViewHolder(View itemView) {
            super(itemView);
            CrewCard = (CardView) itemView.findViewById(R.id.crew);
            Poster = (NetworkImageView) itemView.findViewById(R.id.crew_poster);
            CrewName = (TextView) itemView.findViewById(R.id.crew_name);
            CrewCharacter = (TextView) itemView.findViewById(R.id.crew_character);
            OnClick = (LinearLayout) itemView.findViewById(R.id.crew_on_click);
        }
    }
}
