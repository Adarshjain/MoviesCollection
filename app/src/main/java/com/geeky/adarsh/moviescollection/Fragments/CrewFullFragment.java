package com.geeky.adarsh.moviescollection.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.geeky.adarsh.moviescollection.Adapter.CrewAdapter;
import com.geeky.adarsh.moviescollection.Interfaces.Keys;
import com.geeky.adarsh.moviescollection.R;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;
import com.geeky.adarsh.moviescollection.pojo.Crew;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrewFullFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrewFullFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String fetchDataUrl,title;
    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();
    private RequestQueue mRequestQueue = mVolleySingleton.getRequestQueue();
    private ArrayList<Crew> myCrew = new ArrayList<>();
    private CrewAdapter mCrewAdapter;
    private int color;

    private String mParam1;
    private String mParam2;


    public CrewFullFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrewFullFragment.
     */
    public static CrewFullFragment newInstance(String param1, String param2) {
        CrewFullFragment fragment = new CrewFullFragment();
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
            title = getArguments().getString("movieTitle");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crew_full, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.appBarPadding);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Crew - " + title);
        toolbar.setBackgroundColor(color);
        RecyclerView crewRecyclerView = (RecyclerView) view.findViewById(R.id.crew_full_recycler);
        crewRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mCrewAdapter = new CrewAdapter(getActivity());
        crewRecyclerView.setAdapter(mCrewAdapter);

        fetchData();
        return view;
    }

    private void fetchData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        myCrew = parseJson(response);
                        mCrewAdapter.setCrewList(myCrew);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error Fetching Crew Details!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private ArrayList<Crew> parseJson(JSONObject response) {
        try {
            if (response.has(Keys.Main.CREW) && !response.isNull(Keys.Main.CREW)) {
                JSONArray CrewTemp;
//                        CreditsTemp = response.getJSONObject(Main.CREDITS);
                CrewTemp = response.getJSONArray(Keys.Main.CREW);
                for (int i = 0; i < CrewTemp.length(); i++) {
                    String CrewId = null, CrewName = null, CrewCharacter = null, CrewProfile = null;
                    JSONObject temp = CrewTemp.getJSONObject(i);
                    if (temp.has(Keys.Main.ID))
                        CrewId = temp.getString(Keys.Main.ID);
                    if (temp.has(Keys.Main.NAME))
                        CrewName = temp.getString(Keys.Main.NAME);
                    if (temp.has(Keys.Main.JOB))
                        CrewCharacter = temp.getString(Keys.Main.JOB);
                    if (temp.has(Keys.Main.PROFILE_PATH) && !temp.isNull(Keys.Main.PROFILE_PATH))
                        CrewProfile = temp.getString(Keys.Main.PROFILE_PATH);


                    Crew tempCrew = new Crew(CrewCharacter, CrewId, CrewName, CrewProfile);
                    myCrew.add(tempCrew);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myCrew;
    }

}
