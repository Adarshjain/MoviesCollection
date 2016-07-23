package com.geeky.adarsh.moviescollection;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;
import com.geeky.adarsh.moviescollection.pojo.Cast;
import com.geeky.adarsh.moviescollection.pojo.Crew;
import com.geeky.adarsh.moviescollection.pojo.Genre;
import com.geeky.adarsh.moviescollection.pojo.ProductionCompanies;
import com.geeky.adarsh.moviescollection.pojo.SimilarMovies;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class DisplayMovie extends AppCompatActivity {
    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoaderx = mVolleySingleton.getImageLoader();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();
    private RequestQueue mRequestQueue = mVolleySingleton.getRequestQueue();
    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_movie);
        supportPostponeEnterTransition();

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        final Bundle b = getIntent().getExtras();
        String movieTitle = b.getString("movieTitle");
        String PosterUrl = b.getString("posterPath");
        String BackdropPath = b.getString("backdropPath");
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final CollapsingToolbarLayout collapsingToolbarLayout;
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final NetworkImageView ix = (NetworkImageView) findViewById(R.id.moviePoster);
        final int[] color = {0xff000000};
//        final int[] bodyTextColor = {0xff000000};
        mImageLoaderx.get(PosterUrl, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Image", "Error");
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    Palette.from(response.getBitmap()).clearFilters().generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
//                            color[0] = palette.getLightVibrantColor(color[0]);
//                            Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
//                            Palette.Swatch vibrantLightSwatch = palette.getLightVibrantSwatch();
                            Palette.Swatch vibrantDarkSwatch = palette.getDarkVibrantSwatch();
//                            Palette.Swatch mutedSwatch = palette.getMutedSwatch();
//                            Palette.Swatch mutedLightSwatch = palette.getLightMutedSwatch();
//                            Palette.Swatch mutedDarkSwatch = palette.getDarkMutedSwatch();
                            if (vibrantDarkSwatch != null) {
                                color[0] = vibrantDarkSwatch.getRgb();
//                                bodyTextColor[0] = vibrantDarkSwatch.getTitleTextColor();
//                                collapsingToolbarLayout.setCollapsedTitleTextColor(bodyTextColor[0]);
                            }
//                            collapsingToolbarLayout.setCollapsedTitleTextAppearance(color[0]);
                            collapsingToolbarLayout.setStatusBarScrimColor(color[0]);
                            collapsingToolbarLayout.setContentScrimColor(color[0]);
                            collapsingToolbarLayout.setBackgroundColor(color[0]);
                            fab.setBackgroundTintList(ColorStateList.valueOf(color[0]));
                        }
                    });
//                }
                } else {
                    int color = 0xff000000;
                    collapsingToolbarLayout.setStatusBarScrimColor(color);
                    collapsingToolbarLayout.setContentScrimColor(color);
                    collapsingToolbarLayout.setBackgroundColor(color);
                    fab.setBackgroundTintList(ColorStateList.valueOf(color));
//                    ix.setImageUrl(PosterUrl, mImageLoader);
                }
            }
        });
        ix.setImageUrl(BackdropPath, mImageLoader);
        collapsingToolbarLayout.setTitle(movieTitle);

        fetchData();

//        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordLay);
//        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
//
//        mAppBarLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                int heightPx = findViewById(R.id.app_bar_layout).getHeight();
//                setAppBarOffset(dpToPx(256));
//                Toast.makeText(getApplicationContext(), "d" + dpToPx(256), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void fetchData() {
        String fetchDataUrl = "https://api.themoviedb.org/3/movie/244786?api_key=39f9635028e0a25da413d4e90255bb20&append_to_response=credits,images,videos,similar,reviews";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
        Boolean adult = false;
        String backdropPath = null;
        String belongsToCollection = null;
        String budget = null;
        ArrayList<Genre> genreList = new ArrayList<>();
        String homepage = null;
        Long imdbId = -1L;
        String Overview = null;
        ArrayList<ProductionCompanies> prodComp = new ArrayList<>();
        Date releaseDate = null;
        Long revenue = -1L;
        String status = null;
        String tagline = null;
        String title = null;
        Float rating = -1F;
        Long ratingCount = -1L;
        ArrayList<Cast> cast = new ArrayList<>();
        ArrayList<Crew> crew = new ArrayList<>();
        String BackdropImage = null;
        String PosterIamge = null;
        String TrailerKey = null;
        String TrailerName = null;
        ArrayList<SimilarMovies> similarMovies = new ArrayList<>();

    }

//    public int dpToPx(int dp) {
//        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
//        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
//    }
//
//    private void setAppBarOffset(int offsetPx) {
//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
//        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
//        behavior.onNestedPreScroll(mCoordinatorLayout, mAppBarLayout, null, 0, offsetPx, new int[]{0, 0});
//    }
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selected = item.getItemId();
        if (selected == android.R.id.home) {
            supportFinishAfterTransition();
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
