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
import com.geeky.adarsh.moviescollection.Adapter.CastAdapter;
import com.geeky.adarsh.moviescollection.Interfaces.Keys;
import com.geeky.adarsh.moviescollection.R;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;
import com.geeky.adarsh.moviescollection.pojo.Cast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CastFullFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CastFullFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String fetchDataUrl,title;
    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();
    private RequestQueue mRequestQueue = mVolleySingleton.getRequestQueue();
    private ArrayList<Cast> myCast = new ArrayList<>();
    private CastAdapter mCastAdapter;
    private int color;

    private String mParam1;
    private String mParam2;


    public CastFullFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CastFullFragment.
     */
    public static CastFullFragment newInstance(String param1, String param2) {
        CastFullFragment fragment = new CastFullFragment();
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
        View view = inflater.inflate(R.layout.fragment_cast_full, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.appBarPadding);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Cast - " + title);
        toolbar.setBackgroundColor(color);
        RecyclerView castRecyclerView = (RecyclerView) view.findViewById(R.id.cast_full_recycler);
        castRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mCastAdapter = new CastAdapter(getActivity());
        castRecyclerView.setAdapter(mCastAdapter);

        fetchData();
        return view;
    }

    private void fetchData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        myCast = parseJson(response);
                        mCastAdapter.setCastList(myCast);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error Fetching Cast Details!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private ArrayList<Cast> parseJson(JSONObject response) {
        try {
            if (response.has(Keys.Main.CAST) && !response.isNull(Keys.Main.CAST)) {
                JSONArray CastTemp, CrewTemp;
//                        CreditsTemp = response.getJSONObject(Main.CREDITS);
                CastTemp = response.getJSONArray(Keys.Main.CAST);
                for (int i = 0; i < CastTemp.length(); i++) {
                    String CastId = null, CastName = null, CastCharacter = null, CastProfile = null;
                    JSONObject temp = CastTemp.getJSONObject(i);
                    if (temp.has(Keys.Main.ID))
                        CastId = temp.getString(Keys.Main.ID);
                    if (temp.has(Keys.Main.NAME))
                        CastName = temp.getString(Keys.Main.NAME);
                    if (temp.has(Keys.Main.CHARACTER))
                        CastCharacter = temp.getString(Keys.Main.CHARACTER);
                    if (temp.has(Keys.Main.PROFILE_PATH) && !temp.isNull(Keys.Main.PROFILE_PATH))
                        CastProfile = temp.getString(Keys.Main.PROFILE_PATH);


                    Cast tempCast = new Cast(CastCharacter, CastId, CastName, CastProfile);
                    myCast.add(tempCast);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myCast;
    }

}
