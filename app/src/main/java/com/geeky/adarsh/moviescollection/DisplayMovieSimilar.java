package com.geeky.adarsh.moviescollection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.geeky.adarsh.moviescollection.Interfaces.Keys.Main;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayMovieSimilar extends AppCompatActivity {
    private String id, movieTitle;
    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoaderX = mVolleySingleton.getImageLoader();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();
    private RequestQueue mRequestQueue = mVolleySingleton.getRequestQueue();
    private TextView MetascoreV, ImdbRatingV, ImdbVoteCountV, TmdbRatingV, TmdbVoteCountV,
            TomatoConsensusV, RottenCriticV, CriticRatingV, RottenUserMeterV, RottenUserRatingV,
            RatedV, TaglineV, OverviewV, ReleaseDateV, RuntimeV, StatusV, BudgetV, RevenueV, GenreV,
            AdultV, HomepageV, CollectionNameV, AwardsV, TCTV;
    private TextView CastNameV[] = new TextView[3],
            CastCharacterV[] = new TextView[3],
            CrewNameV[] = new TextView[3],
            CrewJobV[] = new TextView[3],
            ProdCompNameV[] = new TextView[3],
            SimMovNameV[] = new TextView[3];
    //            SimMovYearV[] = new TextView[3];
    private LinearLayout HorizontalImageViewV, BTC, LinearMain, HompV, CastV, CrewV, SimilarV,SimilarOnClickV[] = new LinearLayout[3];
    private RelativeLayout GoToCollectionV, IMDBV, MetaV, RTCV, RTUV, TMDBV;
    private NetworkImageView CollectionPosterV, CastPosterV[] = new NetworkImageView[3], BackdropV, posterImage,
            CrewPosterV[] = new NetworkImageView[3],
            ProdCompPosterV[] = new NetworkImageView[3],
            SimMovPosterV[] = new NetworkImageView[3];
    private FloatingActionButton CastFullV, CrewFullV, ProdCompFullV, SimMovFullV, FABMain;
    private CardView ProdCompCard[] = new CardView[3],
            SimMovCard[] = new CardView[3];
    private ArrayList<String> ImagePaths = new ArrayList<>();
    private ArrayList<String> PosterPaths = new ArrayList<>();
//            CastCard[] = new CardView[3],
//            CrewCard[] = new CardView[3];
//    @SuppressLint("SimpleDateFormat")
//    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//    @SuppressLint("SimpleDateFormat")
//    private SimpleDateFormat df = new SimpleDateFormat("yyyy");

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
        movieTitle = b.getString("movieTitle");
        String PosterUrl = b.getString("posterPath");
        String BackdropPath = b.getString("backdropPath");
        id = b.getString("id");
        final CollapsingToolbarLayout collapsingToolbarLayout;
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        BackdropV = (NetworkImageView) findViewById(R.id.moviePoster);
        BackdropV.setTransitionName("posterX");
        final int[] color = {0xff000000};
        final LinearLayout ratingCard = (LinearLayout) findViewById(R.id.linearCard);
//        final LinearLayout overviewCard = (LinearLayout) findViewById(R.id.overviewCard);
        posterImage = (NetworkImageView) findViewById(R.id.posterImage);
//        final int[] bodyTextColor = {0xff000000};
        mImageLoaderX.get(PosterUrl, new ImageLoader.ImageListener() {

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
                            ratingCard.setBackgroundColor(color[0]);
//                            fab.setBackgroundTintList(ColorStateList.valueOf(color[0]));
//                            overviewCard.setBackgroundColor(color[0]);
                        }
                    });
//                }
                } else {
                    int color = 0xff000000;
                    collapsingToolbarLayout.setStatusBarScrimColor(color);
                    collapsingToolbarLayout.setContentScrimColor(color);
                    collapsingToolbarLayout.setBackgroundColor(color);
                    ratingCard.setBackgroundColor(color);
//                    fab.setBackgroundTintList(ColorStateList.valueOf(color));
//                    overviewCard.setBackgroundColor(color);
//                    ix.setImageUrl(PosterUrl, mImageLoader);
                }
            }
        });
//        fab.setBackgroundTintList(ColorStateList.valueOf(0xffffffff));
        BackdropV.setImageUrl(BackdropPath, mImageLoader);
        posterImage.setImageUrl(PosterUrl, mImageLoader);

        collapsingToolbarLayout.setTitle(movieTitle);

        initViews();
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

    private void initViews() {
        AwardsV = (TextView) findViewById(R.id.awards);
        MetascoreV = (TextView) findViewById(R.id.metascore);
        ImdbRatingV = (TextView) findViewById(R.id.imdbRate);
        ImdbVoteCountV = (TextView) findViewById(R.id.imdbVoteCount);
        TmdbRatingV = (TextView) findViewById(R.id.tmdbRate);
        TmdbVoteCountV = (TextView) findViewById(R.id.tmdbVoteCount);
        TomatoConsensusV = (TextView) findViewById(R.id.tomatoConsensus);
        RottenCriticV = (TextView) findViewById(R.id.rottenCritic);
        CriticRatingV = (TextView) findViewById(R.id.criticRating);
        RottenUserMeterV = (TextView) findViewById(R.id.rottenUserMeter);
        RottenUserRatingV = (TextView) findViewById(R.id.rottenUserRating);
        RatedV = (TextView) findViewById(R.id.rated);
        TaglineV = (TextView) findViewById(R.id.tagline);
        OverviewV = (TextView) findViewById(R.id.overview);
        ReleaseDateV = (TextView) findViewById(R.id.ReleaseDate);
        RuntimeV = (TextView) findViewById(R.id.runtime);
        StatusV = (TextView) findViewById(R.id.status);
        BudgetV = (TextView) findViewById(R.id.budget);
        RevenueV = (TextView) findViewById(R.id.revenue);
        GenreV = (TextView) findViewById(R.id.genre);
        AdultV = (TextView) findViewById(R.id.adult);
        HomepageV = (TextView) findViewById(R.id.homepage);
        CollectionNameV = (TextView) findViewById(R.id.collectionName);
        CastNameV[0] = (TextView) findViewById(R.id.Cast1Name);
        CastNameV[1] = (TextView) findViewById(R.id.Cast2Name);
        CastNameV[2] = (TextView) findViewById(R.id.Cast3Name);
        CastCharacterV[0] = (TextView) findViewById(R.id.cast1Character);
        CastCharacterV[1] = (TextView) findViewById(R.id.cast2Character);
        CastCharacterV[2] = (TextView) findViewById(R.id.cast3Character);
        CrewNameV[0] = (TextView) findViewById(R.id.crew1Name);
        CrewNameV[1] = (TextView) findViewById(R.id.crew2Name);
        CrewNameV[2] = (TextView) findViewById(R.id.crew3Name);
        CrewJobV[0] = (TextView) findViewById(R.id.crew1Character);
        CrewJobV[1] = (TextView) findViewById(R.id.crew2Character);
        CrewJobV[2] = (TextView) findViewById(R.id.crew3Character);
        ProdCompNameV[0] = (TextView) findViewById(R.id.prodComp1Name);
        ProdCompNameV[1] = (TextView) findViewById(R.id.prodComp2Name);
        ProdCompNameV[2] = (TextView) findViewById(R.id.prodComp3Name);
        SimMovNameV[0] = (TextView) findViewById(R.id.simMov1Name);
        SimMovNameV[1] = (TextView) findViewById(R.id.simMov2Name);
        SimMovNameV[2] = (TextView) findViewById(R.id.simMov3Name);
        TCTV = (TextView) findViewById(R.id.tctv);
//        SimMovYearV[0] = (TextView) findViewById(R.id.simMov1Year);
//        SimMovYearV[1] = (TextView) findViewById(R.id.simMov1Year);
//        SimMovYearV[2] = (TextView) findViewById(R.id.simMov1Year);
        HorizontalImageViewV = (LinearLayout) findViewById(R.id.horizontalImageView);
        LinearMain = (LinearLayout) findViewById(R.id.layoutMain);
        BTC = (LinearLayout) findViewById(R.id.btc);
        CastV = (LinearLayout) findViewById(R.id.cast);
        CrewV = (LinearLayout) findViewById(R.id.crew);
        SimilarV = (LinearLayout) findViewById(R.id.similar);
        HompV = (LinearLayout) findViewById(R.id.homp);
        SimilarOnClickV[0] = (LinearLayout) findViewById(R.id.simMovOnClick1);
        SimilarOnClickV[1] = (LinearLayout) findViewById(R.id.simMovOnClick2);
        SimilarOnClickV[2] = (LinearLayout) findViewById(R.id.simMovOnClick3);
        IMDBV = (RelativeLayout) findViewById(R.id.imdb);
        MetaV = (RelativeLayout) findViewById(R.id.meta);
        RTCV = (RelativeLayout) findViewById(R.id.RTC);
        RTUV = (RelativeLayout) findViewById(R.id.RTU);
        TMDBV = (RelativeLayout) findViewById(R.id.tmdb);
//        GoToCollectionV = (RelativeLayout) findViewById(R.id.goToCollection);
        CastPosterV[0] = (NetworkImageView) findViewById(R.id.cast1Poster);
        CastPosterV[1] = (NetworkImageView) findViewById(R.id.cast2Poster);
        CastPosterV[2] = (NetworkImageView) findViewById(R.id.cast3Poster);
        CrewPosterV[0] = (NetworkImageView) findViewById(R.id.crew1Poster);
        CrewPosterV[1] = (NetworkImageView) findViewById(R.id.crew2Poster);
        CrewPosterV[2] = (NetworkImageView) findViewById(R.id.crew3Poster);
        ProdCompPosterV[0] = (NetworkImageView) findViewById(R.id.prodComp1Poster);
        ProdCompPosterV[1] = (NetworkImageView) findViewById(R.id.prodComp2Poster);
        ProdCompPosterV[2] = (NetworkImageView) findViewById(R.id.prodComp3Poster);
        SimMovPosterV[0] = (NetworkImageView) findViewById(R.id.simMov1Poster);
        SimMovPosterV[1] = (NetworkImageView) findViewById(R.id.simMov2Poster);
        SimMovPosterV[2] = (NetworkImageView) findViewById(R.id.simMov3Poster);
        CollectionPosterV = (NetworkImageView) findViewById(R.id.collectionPoster);
        CastFullV = (FloatingActionButton) findViewById(R.id.castFull);
        CrewFullV = (FloatingActionButton) findViewById(R.id.crewFull);
        FABMain = (FloatingActionButton) findViewById(R.id.fab);
        ProdCompFullV = (FloatingActionButton) findViewById(R.id.prodCompFull);
        SimMovFullV = (FloatingActionButton) findViewById(R.id.simMovFull);
        ProdCompCard[0] = (CardView) findViewById(R.id.prodComp1);
        ProdCompCard[1] = (CardView) findViewById(R.id.prodComp2);
        ProdCompCard[2] = (CardView) findViewById(R.id.prodComp3);
        SimMovCard[0] = (CardView) findViewById(R.id.simMov1);
        SimMovCard[1] = (CardView) findViewById(R.id.simMov2);
        SimMovCard[2] = (CardView) findViewById(R.id.simMov3);

//        CastCard[0] = (CardView) findViewById(R.id.Cast1);
//        CastCard[1] = (CardView) findViewById(R.id.Cast2);
//        CastCard[2] = (CardView) findViewById(R.id.Cast3);
//        CrewCard[0] = (CardView) findViewById(R.id.Crew1);
//        CrewCard[1] = (CardView) findViewById(R.id.Crew2);
//        CrewCard[2] = (CardView) findViewById(R.id.Crew3);
    }

    private void fetchData() {
        LinearMain.setVisibility(View.GONE);
        CastV.setVisibility(View.GONE);
        CrewV.setVisibility(View.GONE);
        SimilarV.setVisibility(View.GONE);
//        FABMain.setVisibility(View.GONE);
//        TomatoConsensusV.setVisibility(View.GONE);
//        TCTV.setVisibility(View.GONE);
        String fetchDataUrl = "https://api.themoviedb.org/3/movie/" + id + "?api_key=39f9635028e0a25da413d4e90255bb20&append_to_response=videos";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LinearMain.setVisibility(View.VISIBLE);
                    parseJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Fetching Movie Details!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    @SuppressLint("DefaultLocale")
    private void parseJson(JSONObject response) throws JSONException {
        String Adult = "No";
        String Budget = null;
        String CollectionId = null;
        String CollectionName = null;
        String CollectionPoster = null;
        String Genre = "";
        String Homepage = null;
        String ImdbId = null;
        String Overview = null;
        String ProdCompName = null;
        String ProdCompId = null;
//        String ReleaseDate = null;
        String Revenue = null;
        String Status = null;
        String Tagline;
        String VideoKey = null;
        String Rating;
        String RatingCount;
        String Runtime = null;
        JSONArray GenreJsonArray, ProdComp, Video;
//        String backdropPath = null;
//        String belongsToCollection = null;
//        ArrayList<Genre> GenreList = new ArrayList<>();
//        String title = null;
//        ArrayList<ProductionCompanies> ProdComp = new ArrayList<>();
//        Long RatingCount = -1L;
//        ArrayList<Cast> cast = new ArrayList<>();
//        ArrayList<Crew> crew = new ArrayList<>();
//        JSONObject CreditsTemp;
//        ArrayList<SimilarMovies> similarMovies = new ArrayList<>();

        if (response.has(Main.IMDB_ID) && !response.isNull(Main.IMDB_ID))
            ImdbId = response.getString(Main.IMDB_ID);

        fetchOmdb(ImdbId);

        if (response.has(Main.VIDEOS) && !response.isNull(Main.VIDEOS)) {
            JSONObject VideoObject = response.getJSONObject(Main.VIDEOS);
            Video = VideoObject.getJSONArray(Main.RESULTS);
            if (Video != null && Video.length() > 0) {
                JSONObject VideoObjectFinal = Video.getJSONObject(0);
                VideoKey = "http://www.youtube.com/watch?v=" + VideoObjectFinal.getString(Main.KEY);
                FABMain.setVisibility(View.VISIBLE);
            } else {
                FABMain.setVisibility(View.GONE);
            }
        }
        if (response.has(Main.ADULT) && !response.isNull(Main.ADULT)) {
            Adult = response.getString(Main.ADULT);
            if (Adult.equals("false"))
                Adult = "No";
            else
                Adult = "Yes";
        }
        if (response.has(Main.BUDGET) && !response.isNull(Main.BUDGET)) {
            Budget = response.getString(Main.BUDGET);
            if (Budget.equals("0"))
                Budget = "N/A";
            else
                Budget = "$" + Budget;
        }
        if (response.has(Main.HOMEPAGE) && !response.isNull(Main.HOMEPAGE)) {
            Homepage = response.getString(Main.HOMEPAGE);
            if (Homepage.isEmpty())
                HompV.setVisibility(View.GONE);
            else {
                final String finalHomepage = Homepage;
                HompV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(finalHomepage)));
                    }
                });
            }
        }
        if (response.has(Main.OVERVIEW) && !response.isNull(Main.OVERVIEW))
            Overview = response.getString(Main.OVERVIEW);
        if (response.has(Main.REVENUE) && !response.isNull(Main.REVENUE)) {
            Revenue = response.getString(Main.REVENUE);
            if (Revenue.equals("0"))
                Revenue = "N/A";
            else
                Revenue = "$" + Revenue;
        }
        if (response.has(Main.RUNTIME) && !response.isNull(Main.RUNTIME))
            Runtime = response.getString(Main.RUNTIME) + " Mins";
        if (response.has(Main.STATUS) && !response.isNull(Main.STATUS))
            Status = response.getString(Main.STATUS);
        if (response.has(Main.TAGLINE) && !response.isNull(Main.TAGLINE)) {
            Tagline = response.getString(Main.TAGLINE);
            TaglineV.setText(Tagline);
            TaglineV.setVisibility(View.VISIBLE);
        }
        if (response.has(Main.RATING) && !response.isNull(Main.RATING)) {
            Rating = response.getString(Main.RATING);
            if (!Rating.equals("N/A")) {
                String RatX = String.format("%.1f", Float.parseFloat(Rating)) + "/10";
                TmdbRatingV.setText(RatX);
                TMDBV.setVisibility(View.VISIBLE);
                if (response.has(Main.RATING_COUNT) && !response.isNull(Main.RATING_COUNT)) {
                    RatingCount = response.getString(Main.RATING_COUNT);
                    TmdbVoteCountV.setText(RatingCount);
                }
            }
        }

        if (response.has(Main.GENRE) && !response.isNull(Main.GENRE)) {
            GenreJsonArray = response.getJSONArray(Main.GENRE);
            for (int i = 0; i < GenreJsonArray.length(); i++) {
                JSONObject temp = GenreJsonArray.getJSONObject(i);
                Genre += temp.getString(Main.NAME);
                if (!(i + 1 == GenreJsonArray.length()))
                    Genre += ", ";
            }
        }
        if (response.has(Main.BELONGS_TO_COLLECTION) && !response.isNull(Main.BELONGS_TO_COLLECTION)) {
            JSONObject temp = response.getJSONObject(Main.BELONGS_TO_COLLECTION);
            if (temp.has(Main.ID))
                CollectionId = temp.getString(Main.ID);
            if (temp.has(Main.NAME))
                CollectionName = temp.getString(Main.NAME);
            if (temp.has(Main.POSTER_PATH) && !temp.isNull(Main.POSTER_PATH))
                CollectionPoster = temp.getString(Main.POSTER_PATH);
            CollectionNameV.setText(CollectionName);
            if (CollectionPoster != null)
                CollectionPosterV.setImageUrl("http://image.tmdb.org/t/p/w150" + CollectionPoster, mImageLoader);
        } else {
            BTC.setVisibility(View.GONE);
        }
        if (response.has(Main.PRODUCTION_COMPANIES) && !response.isNull(Main.PRODUCTION_COMPANIES)) {
            ProdComp = response.getJSONArray(Main.PRODUCTION_COMPANIES);
            for (int i = 0; i < ProdComp.length() && i < 3; i++) {
                JSONObject temp = ProdComp.getJSONObject(i);
                if (temp.has(Main.ID))
                    ProdCompId = temp.getString(Main.ID);
                if (temp.has(Main.NAME))
                    ProdCompName = temp.getString(Main.NAME);

                ProdCompNameV[i].setText(ProdCompName);
                ProdCompNameV[i].setTag(ProdCompId);
                fetchProduction(ProdCompId, i);
            }
            if (ProdComp.length() == 1) {
                ProdCompCard[1].setVisibility(View.GONE);
                ProdCompCard[2].setVisibility(View.GONE);
                ProdCompFullV.setVisibility(View.GONE);
            } else if (ProdComp.length() == 2) {
                ProdCompCard[2].setVisibility(View.GONE);
                ProdCompFullV.setVisibility(View.GONE);
            } else if (ProdComp.length() == 3) {
                ProdCompFullV.setVisibility(View.GONE);
            }
        }

        fetchCredits();
        fetchSimilar();
        fetchImages();


        AdultV.setText(Adult);
        BudgetV.setText(Budget);
//        HomepageV.setText(Homepage);
        OverviewV.setText(Overview);
        RuntimeV.setText(Runtime);
        RevenueV.setText(Revenue);
        StatusV.setText(Status);
        GenreV.setText(Genre);

        final String finalVideoKey = VideoKey;
        FABMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(finalVideoKey)));
            }
        });
    }

    private void fetchOmdb(String ImdbId) {
        String fetchDataUrl = "http://www.omdbapi.com/?i=" + ImdbId + "&tomatoes=true";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseOmdb(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Fetching OMDB Data!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private void parseOmdb(JSONObject response) throws JSONException {
        String Released = null,
                Rated = null,
                Awards = null,
                Metascore = null,
                ImdbRating = null,
                ImdbVotes = null,
                TomatoMeter = null,
                TomatoRating = null,
                TomatoConsensus = null,
                TomatoUserMeter = null,
                TomatoUserRating = null;

        if (response.has(Main.RELEASED) && !response.isNull(Main.RELEASED))
            Released = response.getString(Main.RELEASED);
        if (response.has(Main.RATED) && !response.isNull(Main.RATED))
            Rated = response.getString(Main.RATED);
        if (response.has(Main.AWARDS) && !response.isNull(Main.AWARDS))
            Awards = response.getString(Main.AWARDS);
        if (response.has(Main.METASCORE) && !response.isNull(Main.METASCORE)) {
            Metascore = response.getString(Main.METASCORE);
            if (!Metascore.equals("N/A"))
                Metascore += " %";
        }
        if (response.has(Main.IMDB_RATING) && !response.isNull(Main.IMDB_RATING)) {
            ImdbRating = response.getString(Main.IMDB_RATING);
            if (!ImdbRating.equals("N/A"))
                ImdbRating += "/10";
        }
        if (response.has(Main.IMDB_VOTES) && !response.isNull(Main.IMDB_VOTES))
            ImdbVotes = response.getString(Main.IMDB_VOTES);
        if (response.has(Main.TOMATO_METER) && !response.isNull(Main.TOMATO_METER)) {
            TomatoMeter = response.getString(Main.TOMATO_METER);
            if (!TomatoMeter.equals("N/A"))
                TomatoMeter += "%";
        }
        if (response.has(Main.TOMATO_RATING) && !response.isNull(Main.TOMATO_RATING)) {
            TomatoRating = response.getString(Main.TOMATO_RATING);
            if (!TomatoRating.equals("N/A"))
                TomatoRating += "/10";
            else
                CriticRatingV.setVisibility(View.GONE);
        }
        if (response.has(Main.TOMATO_CONSENSUS) && !response.isNull(Main.TOMATO_CONSENSUS)) {
            TomatoConsensus = response.getString(Main.TOMATO_CONSENSUS);
            if (!TomatoConsensus.equals("N/A")) {
                TomatoConsensusV.setVisibility(View.VISIBLE);
                TCTV.setVisibility(View.VISIBLE);
            }
        }
        if (response.has(Main.TOMATO_USER_METER) && !response.isNull(Main.TOMATO_USER_METER)) {
            TomatoUserMeter = response.getString(Main.TOMATO_USER_METER);
            if (!TomatoUserMeter.equals("N/A"))
                TomatoUserMeter += "%";
        }
        if (response.has(Main.TOMATO_USER_RATING) && !response.isNull(Main.TOMATO_USER_RATING)) {
            TomatoUserRating = response.getString(Main.TOMATO_USER_RATING);
            if (!TomatoUserRating.equals("N/A"))
                TomatoUserRating += "/5";
            else
                RottenUserRatingV.setVisibility(View.GONE);
        }

        AwardsV.setText(Awards);
        ReleaseDateV.setText(Released);
        RatedV.setText(Rated);
        MetascoreV.setText(Metascore);
        ImdbRatingV.setText(ImdbRating);
        ImdbVoteCountV.setText(ImdbVotes);
        RottenCriticV.setText(TomatoMeter);
        CriticRatingV.setText(TomatoRating);
        TomatoConsensusV.setText(TomatoConsensus);
        RottenUserMeterV.setText(TomatoUserMeter);
        RottenUserRatingV.setText(TomatoUserRating);
    }

    private void fetchProduction(String Prodid, final int i) {
        String fetchDataUrl = "https://api.themoviedb.org/3/company/" + Prodid + "?api_key=39f9635028e0a25da413d4e90255bb20";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has(Main.LOGO_PATH) && !response.isNull(Main.LOGO_PATH)) {
                        ProdCompPosterV[i].setBackgroundColor(0xffffffff);
                        ProdCompPosterV[i].setImageUrl("http://image.tmdb.org/t/p/w150" + response.getString(Main.LOGO_PATH), mImageLoader);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Fetching Production Details!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private void fetchSimilar() {
        String fetchDataUrl = "https://api.themoviedb.org/3/movie/" + id + "/similar?api_key=39f9635028e0a25da413d4e90255bb20";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SimilarV.setVisibility(View.VISIBLE);
                JSONArray SimMovArray;
                if (response.has(Main.RESULTS) && !response.isNull(Main.RESULTS)) {
                    try {
//                        JSONObject SimMov = response.getJSONObject(Main.SIMILAR);
                        SimMovArray = response.getJSONArray(Main.RESULTS);
                        for (int i = 0; i < SimMovArray.length() && i < 3; i++) {
                            String SimMovId = null, SimMovTitle = null, SimMovPoster = null, SimMovBackdrop = null;
                            JSONObject temp = SimMovArray.getJSONObject(i);
                            if (temp.has(Main.ID))
                                SimMovId = temp.getString(Main.ID);
                            if (temp.has(Main.TITLE))
                                SimMovTitle = temp.getString(Main.TITLE);
                            if (temp.has(Main.POSTER_PATH))
                                SimMovPoster = temp.getString(Main.POSTER_PATH);
                            if (temp.has(Main.BACKDROP_PATH))
                                SimMovBackdrop = temp.getString(Main.BACKDROP_PATH);

                            SimMovNameV[i].setText(SimMovTitle);
                            SimMovNameV[i].setTag(SimMovId);
                            if (SimMovPoster != null)
                                SimMovPosterV[i].setImageUrl("http://image.tmdb.org/t/p/w150" + SimMovPoster, mImageLoader);
                            final String finalSimMovTitle = SimMovTitle;
                            final String finalSimMovBackdrop = SimMovBackdrop;
                            final String finalSimMovPoster = SimMovPoster;
                            final String finalSimMovId = SimMovId;
                            final int x = i;
                            SimilarOnClickV[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(DisplayMovieSimilar.this,DisplayMovieSimilar2.class);
                                    Bundle b = new Bundle();
                                    b.putString("movieTitle", finalSimMovTitle);
                                    b.putString("backdropPath", "http://image.tmdb.org/t/p/w500" + finalSimMovBackdrop);
                                    b.putString("posterPath", "http://image.tmdb.org/t/p/w300" + finalSimMovPoster);
                                    b.putString("id", finalSimMovId);
                                    Log.e("Similar","pos "+x + finalSimMovTitle + finalSimMovBackdrop + finalSimMovPoster + finalSimMovId);
                                    intent.putExtras(b);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                    startActivity(intent);
//                                    finish();
                                    Log.e("Similar","pos "+x + finalSimMovTitle + finalSimMovBackdrop + finalSimMovPoster + finalSimMovId);
                                }
                            });
                        }
                        if (SimMovArray.length() < 4)
                            if (SimMovArray.length() == 1) {
                                SimMovCard[1].setVisibility(View.GONE);
                                SimMovCard[2].setVisibility(View.GONE);
                                SimMovFullV.setVisibility(View.GONE);
                            } else if (SimMovArray.length() == 2) {
                                SimMovCard[2].setVisibility(View.GONE);
                                SimMovFullV.setVisibility(View.GONE);
                            } else if (SimMovArray.length() == 3) {
                                SimMovFullV.setVisibility(View.GONE);
                            }
                    } catch (JSONException e) {
                        Log.e("Similar", "Fetch Error");
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Fetching Similar Movie Details!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private void fetchImages() {
        String fetchDataUrl = "https://api.themoviedb.org/3/movie/" + id + "/images?api_key=39f9635028e0a25da413d4e90255bb20";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        dpToPx(350), dpToPx(210)
                );
                params.setMarginStart(dpToPx(8));
                try {
                    if (response.has(Main.BACKDROPS) && !response.isNull(Main.BACKDROPS)) {
//                        JSONObject ImagesObj = response.getJSONObject(Main.IMAGES);
                        JSONArray ImagesArray = response.getJSONArray(Main.BACKDROPS);
                        String ImagePath = null;
                        for (int i = 0; i < ImagesArray.length(); i++) {
                            JSONObject temp = ImagesArray.getJSONObject(i);
                            if (temp.has(Main.FILE_PATH) && !temp.isNull(Main.FILE_PATH)) {
                                ImagePath = temp.getString(Main.FILE_PATH);
                                ImagePaths.add(ImagePath);
                            }
                        }
                        BackdropV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), ImageviewFull.class);
                                Bundle b = new Bundle();
                                b.putStringArrayList("paths", ImagePaths);
                                b.putInt("position", 0);
                                b.putString("title", movieTitle);
                                intent.putExtras(b);
                                startActivity(intent);
                            }
                        });
                        for (int i = 0; i < ImagesArray.length() && i < 6; i++) {
                            if (i == 0)
                                continue;
                            JSONObject temp = ImagesArray.getJSONObject(i);
                            if (temp.has(Main.FILE_PATH) && !temp.isNull(Main.FILE_PATH))
                                ImagePath = temp.getString(Main.FILE_PATH);

                            final NetworkImageView Images = new NetworkImageView(getApplication());
                            Images.setLayoutParams(params);
                            Images.setAdjustViewBounds(true);
                            Images.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            Images.setTransitionName("fullView");
                            Images.setImageUrl("http://image.tmdb.org/t/p/w500" + ImagePath, mImageLoader);
//                            Activity act = getAc
                            final int finalI = i;
                            Images.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getApplicationContext(), ImageviewFull.class);
                                    Bundle b = new Bundle();
                                    b.putStringArrayList("paths", ImagePaths);
                                    b.putInt("position", finalI);
                                    b.putString("title", movieTitle);
                                    intent.putExtras(b);
//                                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                                            makeSceneTransitionAnimation(DisplayMovie.this, Images, "fullView");, options.toBundle()
                                    startActivity(intent);
                                }
                            });
                            HorizontalImageViewV.addView(Images);
                        }
                    }
                    if (response.has(Main.POSTERS) && !response.isNull(Main.POSTERS)) {
//                        JSONObject ImagesObj = response.getJSONObject(Main.IMAGES);
                        JSONArray ImagesArray = response.getJSONArray(Main.POSTERS);
                        String PosterPath = null;
                        for (int i = 0; i < ImagesArray.length(); i++) {
                            JSONObject temp = ImagesArray.getJSONObject(i);
                            if (temp.has(Main.FILE_PATH) && !temp.isNull(Main.FILE_PATH)) {
                                PosterPath = temp.getString(Main.FILE_PATH);
                                PosterPaths.add(PosterPath);
                            }
                        }
                        posterImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), ImageviewFull.class);
                                Bundle b = new Bundle();
                                b.putStringArrayList("paths", PosterPaths);
                                b.putInt("position", 0);
                                b.putString("title", movieTitle);
                                intent.putExtras(b);
                                startActivity(intent);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Fetching Images!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private void fetchCredits() {
        String fetchDataUrl = "https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=39f9635028e0a25da413d4e90255bb20";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                CastV.setVisibility(View.VISIBLE);
                CrewV.setVisibility(View.VISIBLE);
                try {
                    if (response.has(Main.CAST) && !response.isNull(Main.CAST)) {
                        JSONArray CastTemp, CrewTemp;
//                        CreditsTemp = response.getJSONObject(Main.CREDITS);
                        CastTemp = response.getJSONArray(Main.CAST);
                        for (int i = 0; i < CastTemp.length() && i < 3; i++) {
                            String CastId = null, CastName = null, CastCharacter = null, CastProfile = null;
                            JSONObject temp = CastTemp.getJSONObject(i);
                            if (temp.has(Main.ID))
                                CastId = temp.getString(Main.ID);
                            if (temp.has(Main.NAME))
                                CastName = temp.getString(Main.NAME);
                            if (temp.has(Main.CHARACTER))
                                CastCharacter = temp.getString(Main.CHARACTER);
                            if (temp.has(Main.PROFILE_PATH) && !temp.isNull(Main.PROFILE_PATH))
                                CastProfile = temp.getString(Main.PROFILE_PATH);

                            CastNameV[i].setText(CastName);
                            CastCharacterV[i].setText(CastCharacter);
                            CastNameV[i].setTag(CastName);
                            if (CastProfile != null)
                                CastPosterV[i].setImageUrl("http://image.tmdb.org/t/p/w150" + CastProfile, mImageLoader);

                        }
                        if (response.has(Main.CREW) && !response.isNull(Main.CREW)) {
                            CrewTemp = response.getJSONArray(Main.CREW);
                            for (int i = 0; i < CrewTemp.length() && i < 3; i++) {
                                String CrewId = null, CrewName = null, CrewJob = null, CrewProfile = null;
                                JSONObject temp = CrewTemp.getJSONObject(i);
                                if (temp.has(Main.ID))
                                    CrewId = temp.getString(Main.ID);
                                if (temp.has(Main.NAME))
                                    CrewName = temp.getString(Main.NAME);
                                if (temp.has(Main.JOB))
                                    CrewJob = temp.getString(Main.JOB);
                                if (temp.has(Main.PROFILE_PATH) && !temp.isNull(Main.PROFILE_PATH))
                                    CrewProfile = temp.getString(Main.PROFILE_PATH);

                                CrewNameV[i].setText(CrewName);
                                CrewJobV[i].setText(CrewJob);
                                CrewNameV[i].setTag(CrewName);
                                if (CrewProfile != null)
                                    CrewPosterV[i].setImageUrl("http://image.tmdb.org/t/p/w150" + CrewProfile, mImageLoader);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Fetching Credits Details!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

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
//
//    private void setAppBarOffset(int offsetPx) {
//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
//        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
//        behavior.onNestedPreScroll(mCoordinatorLayout, mAppBarLayout, null, 0, offsetPx, new int[]{0, 0});
//    }
//
