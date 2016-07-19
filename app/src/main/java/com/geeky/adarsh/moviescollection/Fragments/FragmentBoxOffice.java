package com.geeky.adarsh.moviescollection.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.geeky.adarsh.moviescollection.Adapter.BoxOfficeAdapter;
import com.geeky.adarsh.moviescollection.Interfaces.Keys.Terms;
import com.geeky.adarsh.moviescollection.R;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;
import com.geeky.adarsh.moviescollection.pojo.MoviesDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=39f9635028e0a25da413d4e90255bb20";
    private static final String STATE_SAVE = "state_save";
    private ArrayList<MoviesDB> myMoviesDB = new ArrayList<>();

    private String mParam1;
    private String mParam2;
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private BoxOfficeAdapter mBoxOfficeAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView xText;


    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
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
        }

        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_SAVE, myMoviesDB);
    }

    private void sendJsonrequest() {
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        myMoviesDB = parseJsonresponse(response);
                        mBoxOfficeAdapter.setMoviesList(myMoviesDB);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        mRequestQueue.add(mJsonObjectRequest);
    }

    private ArrayList<MoviesDB> parseJsonresponse(JSONObject response) {
        ArrayList<MoviesDB> listMovies = new ArrayList<>();

        if (response != null) {
            try {
                if (response.has(Terms.RESULTS)) {
                    JSONArray mJsonArray = response.getJSONArray(Terms.RESULTS);
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        String title;
                        String posterPath;
                        String overview;
                        String releaseDate;
                        String id;
                        Float rating;
                        String ratingCount;

                        JSONObject currentMovie = mJsonArray.getJSONObject(i);
                        title = currentMovie.getString(Terms.TITLE);
                        posterPath = currentMovie.getString(Terms.POSTER_PATH);
                        overview = currentMovie.getString(Terms.OVERVIEW);
                        releaseDate = currentMovie.getString(Terms.RELEASE_DATE);
                        id = currentMovie.getString(Terms.ID);
                        rating = Float.parseFloat(currentMovie.getString(Terms.RATING)) / 2;
                        ratingCount = currentMovie.getString(Terms.RATING_COUNT);

                        MoviesDB current = new MoviesDB(title, posterPath, overview, releaseDate, id, rating, ratingCount);

                        listMovies.add(current);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listMovies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);
        xText = (TextView) view.findViewById(R.id.volleyErrorText);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeTop10);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.boxOfficeRecycler);
        GridLayoutManager g = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mBoxOfficeAdapter = new BoxOfficeAdapter(getActivity());
        mRecyclerView.setAdapter(mBoxOfficeAdapter);
        if (savedInstanceState != null) {
            myMoviesDB = savedInstanceState.getParcelableArrayList(STATE_SAVE);
            mBoxOfficeAdapter.setMoviesList(myMoviesDB);
        } else {
            mSwipeRefreshLayout.setRefreshing(true);
            onRefresh();
//            sendJsonrequest();
        }
        return view;
    }

    @Override
    public void onRefresh() {
        if (mSwipeRefreshLayout.isRefreshing())
            Toast.makeText(getActivity(), "Loading Data...", Toast.LENGTH_SHORT).show();
        sendJsonrequest();
            mSwipeRefreshLayout.setRefreshing(false);
    }
}
