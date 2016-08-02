package com.geeky.adarsh.moviescollection.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.geeky.adarsh.moviescollection.ImageviewFull;
import com.geeky.adarsh.moviescollection.R;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;
import com.geeky.adarsh.moviescollection.ZoomImageview;

import java.util.ArrayList;


public class FullscreenImageview extends PagerAdapter {
    private Activity mActivity;
    private ArrayList<String> mPaths = new ArrayList<>();
    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();

    public FullscreenImageview(Activity activity, ArrayList<String> paths) {
        this.mActivity = activity;
        this.mPaths = paths;
    }

    @Override
    public int getCount() {
        return mPaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ZoomImageview NetView;
        LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.imageview_adapter, container, false);
//        final View v = layoutInflater.inflate(R.layout.activity_imageview_full,container,false);
        NetView = (ZoomImageview) view.findViewById(R.id.net_imageview);
        NetView.setTransitionName("fullView");
        NetView.setImageUrl("http://image.tmdb.org/t/p/original" + mPaths.get(position), mImageLoader);
//        final Boolean[] bool = {true};
//        NetView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (bool[0]) {
//                    Toast.makeText(mActivity,"Tapped! hide",Toast.LENGTH_SHORT).show();
//                    v.findViewById(R.id.appBarX).setVisibility(View.GONE);
//                    bool[0] = !bool[0];
//                }else{
//                    Toast.makeText(mActivity,"Tapped! show",Toast.LENGTH_SHORT).show();
//                    v.findViewById(R.id.appBarX).setVisibility(View.GONE);
//                    bool[0] = !bool[0];
//                }
//            }
//        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
