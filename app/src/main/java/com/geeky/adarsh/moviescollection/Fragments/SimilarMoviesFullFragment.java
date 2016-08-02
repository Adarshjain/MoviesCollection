package com.geeky.adarsh.moviescollection.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.geeky.adarsh.moviescollection.Adapter.SimilarMoviesAdapter;
import com.geeky.adarsh.moviescollection.Interfaces.Keys;
import com.geeky.adarsh.moviescollection.R;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;
import com.geeky.adarsh.moviescollection.pojo.SimilarMovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimilarMoviesFullFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimilarMoviesFullFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private String fetchDataUrl;
    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();
    private RequestQueue mRequestQueue = mVolleySingleton.getRequestQueue();
    private ArrayList<SimilarMovies> mySimilarMovies = new ArrayList<>();
    private SimilarMoviesAdapter mSimilarMoviesAdapter;
    private int color;


    public SimilarMoviesFullFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SimilarMoviesFullFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SimilarMoviesFullFragment newInstance(String param1, String param2) {
        SimilarMoviesFullFragment fragment = new SimilarMoviesFullFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            fetchDataUrl = getArguments().getString("fetchDataUrl");
            color = getArguments().getInt("color");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_similar_movies_full, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.appBarPadding);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Similar Movies");
        toolbar.setBackgroundColor(color);
        RecyclerView similarMoviesRecyclerView = (RecyclerView) view.findViewById(R.id.similar_movies_recycler);
        similarMoviesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mSimilarMoviesAdapter = new SimilarMoviesAdapter(getActivity());
        similarMoviesRecyclerView.setAdapter(mSimilarMoviesAdapter);

        fetchData();
        return view;
    }

    private void fetchData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mySimilarMovies = parseJson(response);
                        mSimilarMoviesAdapter.setMoviesList(mySimilarMovies);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error Fetching Similar Movie Details!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private ArrayList<SimilarMovies> parseJson(JSONObject response) {
        ArrayList<SimilarMovies> SimilarMoviesArray = new ArrayList<>();
        JSONArray SimMovArray;
        if (response.has(Keys.Main.RESULTS) && !response.isNull(Keys.Main.RESULTS)) {
            try {
                SimMovArray = response.getJSONArray(Keys.Main.RESULTS);
                Log.e("Length", "" + SimMovArray.length());
                for (int i = 0; i < SimMovArray.length(); i++) {
                    String SimMovId = null, SimMovTitle = null, SimMovPoster = null, SimMovBackdrop = null;
                    JSONObject temp = SimMovArray.getJSONObject(i);
                    if (temp.has(Keys.Main.ID))
                        SimMovId = temp.getString(Keys.Main.ID);
                    if (temp.has(Keys.Main.TITLE))
                        SimMovTitle = temp.getString(Keys.Main.TITLE);
                    if (temp.has(Keys.Main.POSTER_PATH))
                        SimMovPoster = temp.getString(Keys.Main.POSTER_PATH);
                    if (temp.has(Keys.Main.BACKDROP_PATH))
                        SimMovBackdrop = temp.getString(Keys.Main.BACKDROP_PATH);
//                    if (temp.has(Keys.Main.BACKDROP_PATH))
//                        SimMovBackdrop = temp.getString(Keys.Main.BACKDROP_PATH);
                    SimilarMovies tempSimilarMovies = new SimilarMovies(SimMovId, SimMovTitle, SimMovPoster, SimMovBackdrop);
                    SimilarMoviesArray.add(tempSimilarMovies);
                }
            } catch (JSONException e) {
                Log.e("Similar", "Fetch Error");
                e.printStackTrace();
            }
        }
        return SimilarMoviesArray;
    }
}