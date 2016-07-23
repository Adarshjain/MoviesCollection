package com.geeky.adarsh.moviescollection.Testing;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.geeky.adarsh.moviescollection.Interfaces.Keys;
import com.geeky.adarsh.moviescollection.R;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;
import com.geeky.adarsh.moviescollection.pojo.Cast;
import com.geeky.adarsh.moviescollection.pojo.Crew;
import com.geeky.adarsh.moviescollection.pojo.Genre;
import com.geeky.adarsh.moviescollection.pojo.ProductionCompanies;
import com.geeky.adarsh.moviescollection.pojo.SimilarMovies;
import com.klinker.android.sliding.SlidingActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Bla extends SlidingActivity {
    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();
    private RequestQueue mRequestQueue = mVolleySingleton.getRequestQueue();
    private CardView mCardView;

    @Override
    public void init(Bundle savedInstanceState) {
//        Intent i = getIntent();
        Bundle b = getIntent().getExtras();
//        Bitmap bit = (Bitmap) i.getParcelableExtra("BitmapImage");
        String movieTitle = b.getString("movieTitle");
//        String PosterUrl = b.getString("posterPath");
        String BackdropPath = b.getString("backdropPath");
        String id = b.getString("id");
        if (movieTitle != null) {
            setTitle(movieTitle);
        }
        int color = b.getInt("colors");

        setPrimaryColors(color, color);
        setFab(0xffffffff, R.drawable.ic_person_black_24dp, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        enableFullscreen();
        setContent(R.layout.activity_bla);
//        mCardView = (CardView) findViewById(R.id.ratingCard);
//        mCardView.setBackgroundColor(color);
        LinearLayout llview = (LinearLayout) findViewById(R.id.linearCard);
        llview.setBackgroundColor(color);
        Log.e("Volley Response", "Bla");
        mImageLoader.get(BackdropPath, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Response", "Error");
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Log.e("Volley Response", "Succession");
                Bitmap bit = response.getBitmap();
                if (bit != null)
                    setImage(bit);

            }
        });
        fetchData(id);

    }

    private void fetchData(String id) {
        String fetchDataUrl = "https://api.themoviedb.org/3/movie/" + id + "?api_key=39f9635028e0a25da413d4e90255bb20&append_to_response=credits,images,videos,similar,reviews";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Fetched", Toast.LENGTH_SHORT).show();
                parseJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Fetching Movie Details!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private void parseJson(JSONObject response) {
        Boolean Adult = false;
        String BackdropPath = null;
        String BelongsToCollection = null;
        String Budget = null;
        ArrayList<Genre> GenreList = new ArrayList<>();
        String Homepage = null;
        Long ImdbId = -1L;
        String Overview = null;
        ArrayList<ProductionCompanies> ProdComp = new ArrayList<>();
        Date ReleaseDate = null;
        Long Revenue = -1L;
        String Status = null;
        String Tagline = null;
        String Title = null;
        Float Rating = -1F;
        Long RatingCount = -1L;
        ArrayList<Cast> Cast = new ArrayList<>();
        ArrayList<Crew> Crew = new ArrayList<>();
        String BackdropImage = null;
        String PosterIamge = null;
        String TrailerKey = null;
        String TrailerName = null;
        ArrayList<SimilarMovies> similarMovies = new ArrayList<>();

        if (response.has(Keys.Main.ADULT)) {
//            response.getString(Keys.Main.ADULT);
        }


    }

}
