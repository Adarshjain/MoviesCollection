package com.geeky.adarsh.moviescollection.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.geeky.adarsh.moviescollection.ImageviewFull;
import com.geeky.adarsh.moviescollection.Interfaces.Keys.CastNCrew;
import com.geeky.adarsh.moviescollection.R;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CastInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CastInfoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();
    private RequestQueue mRequestQueue = mVolleySingleton.getRequestQueue();

    private NetworkImageView ProfileBack;
    private TextView PersonName, Age;
    private CircleImageView ImdbExt, PersonProfile;
    private ImageView FacebookExt, TwitterExt;
    private String CastId;


    public CastInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CastInfoFragment.
     */
    public static CastInfoFragment newInstance(String param1, String param2) {
        CastInfoFragment fragment = new CastInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
            CastId = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast_info, container, false);
        init(view);
        setup();
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new CastInfoFragment.MyAdapter(getFragmentManager()));
        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) view.findViewById(R.id.nts);
        navigationTabStrip.setViewPager(mViewPager);
        return view;
    }

    private void init(View view) {
        ProfileBack = (NetworkImageView) view.findViewById(R.id.back_image);
        PersonName = (TextView) view.findViewById(R.id.person_name);
        Age = (TextView) view.findViewById(R.id.age);
        ImdbExt = (CircleImageView) view.findViewById(R.id.imdb_ext);
        PersonProfile = (CircleImageView) view.findViewById(R.id.person_pic);
        FacebookExt = (ImageView) view.findViewById(R.id.fb_ext);
        TwitterExt = (ImageView) view.findViewById(R.id.twitter_ext);
    }

    private void setup() {
//        &append_to_response=combined_credits,external_ids,images
        String fetchDataUrl = "http://api.themoviedb.org/3/person/" + CastId + "?api_key=39f9635028e0a25da413d4e90255bb20&append_to_response=external_ids,images";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    parseJson(response);
                    String ProfilePath = null, Name = null, DOB = null, ImdbId = null, FbId = null, TwitterId = null;
                    final ArrayList<String> ImagePath = new ArrayList<>();
                    if (response.has(CastNCrew.PROFILE_PATH) && !response.isNull(CastNCrew.PROFILE_PATH))
                        ProfilePath = response.getString(CastNCrew.PROFILE_PATH);
                    if (response.has(CastNCrew.NAME) && !response.isNull(CastNCrew.NAME))
                        Name = response.getString(CastNCrew.NAME);
                    if (response.has(CastNCrew.BIRTHDAY) && !response.isNull(CastNCrew.BIRTHDAY))
                        DOB = response.getString(CastNCrew.BIRTHDAY);
                    if (response.has(CastNCrew.EXTERNAL_IDS) && !response.isNull(CastNCrew.EXTERNAL_IDS)) {
                        JSONObject temp = response.getJSONObject(CastNCrew.EXTERNAL_IDS);
                        if (temp.has(CastNCrew.IMDB_ID_EXT) && !temp.isNull(CastNCrew.IMDB_ID_EXT)) {
                            ImdbId = temp.getString(CastNCrew.IMDB_ID_EXT);
                            ImdbExt.setVisibility(View.VISIBLE);
                        }
                        if (temp.has(CastNCrew.FB_ID_EXT) && !temp.isNull(CastNCrew.FB_ID_EXT)) {
                            FbId = temp.getString(CastNCrew.FB_ID_EXT);
                            FacebookExt.setVisibility(View.VISIBLE);
                        }
                        if (temp.has(CastNCrew.TWITTER_ID_EXT) && !temp.isNull(CastNCrew.TWITTER_ID_EXT)) {
                            TwitterId = temp.getString(CastNCrew.TWITTER_ID_EXT);
                            TwitterExt.setVisibility(View.VISIBLE);
                        }
                    }
                    if (response.has(CastNCrew.IMAGES) && !response.isNull(CastNCrew.IMAGES)) {
                        JSONObject ImageJsonObject = response.getJSONObject(CastNCrew.IMAGES);
                        JSONArray Profiles = ImageJsonObject.getJSONArray(CastNCrew.PROFILES);

                        for (int i = 0; i < Profiles.length(); i++) {
                            JSONObject temp = Profiles.getJSONObject(i);
                            if (temp.has(CastNCrew.IMAGE_PATH) && !temp.isNull(CastNCrew.IMAGE_PATH)) {
                                ImagePath.add("http://image.tmdb.org/t/p/w500" + temp.getString(CastNCrew.IMAGE_PATH));
                            }

                        }
                    }
//
                    
                    if (ProfilePath != null) {
                        ProfileBack.setImageUrl("http://image.tmdb.org/t/p/w500" + ProfilePath, mImageLoader);
                        mImageLoader.get("http://image.tmdb.org/t/p/w500" + ProfilePath, new ImageLoader.ImageListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }

                            @Override
                            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                PersonProfile.setImageBitmap(response.getBitmap());
                            }
                        });
                    }
                    final String finalName = Name;
                    PersonProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ImageviewFull.class);
                            Bundle b = new Bundle();
                            b.putStringArrayList("paths", ImagePath);
                            b.putInt("position", 0);
                            b.putString("title", finalName);
                            intent.putExtras(b);
//                                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                                            makeSceneTransitionAnimation(DisplayMovie.this, Images, "fullView");, options.toBundle()
                            startActivity(intent);
                        }
                    });


                    PersonName.setText(Name);
                    Age.setText(DOB);
                    final String finalImdbId = ImdbId;
                    ImdbExt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.imdb.com/name/" + finalImdbId)));
                        }
                    });
                    final String finalFacebookId = FbId;
                    FacebookExt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + finalFacebookId)));
                        }
                    });
                    final String finalTwitterId = TwitterId;
                    TwitterExt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + finalTwitterId)));
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error Fetching Cast Details!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }


    class MyAdapter extends FragmentPagerAdapter {


        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = CastBioFragment.newInstance("", "");
                    break;
                case 1:
                    fragment = FragmentBoxOffice.newInstance("", "");
                    break;
                case 2:
                    fragment = FragmentSomething.newInstance("", "");
                    break;
            }
            return fragment;
        }


        @Override
        public int getCount() {
            return 3;
        }
    }

}
