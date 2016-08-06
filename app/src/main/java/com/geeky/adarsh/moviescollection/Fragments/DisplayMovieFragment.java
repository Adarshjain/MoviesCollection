package com.geeky.adarsh.moviescollection.Fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.geeky.adarsh.moviescollection.DisplayMovie;
import com.geeky.adarsh.moviescollection.ImageviewFull;
import com.geeky.adarsh.moviescollection.Interfaces.Keys;
import com.geeky.adarsh.moviescollection.R;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayMovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayMovieFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int Tagger;
    private String id, movieTitle, PosterUrl, BackdropPath;
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
    private LinearLayout HorizontalImageViewV, BTC, LinearMain, HompV, CastV, CrewV, SimilarV, SimilarOnClickV[] = new LinearLayout[3];
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
    final int[] color = {0xff000000};
//            CastCard[] = new CardView[3],
//            CrewCard[] = new CardView[3];
//    @SuppressLint("SimpleDateFormat")
//    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//    @SuppressLint("SimpleDateFormat")
//    private SimpleDateFormat df = new SimpleDateFormat("yyyy");

    public DisplayMovieFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayMovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayMovieFragment newInstance(String param1, String param2) {
        DisplayMovieFragment fragment = new DisplayMovieFragment();
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
            movieTitle = getArguments().getString("movieTitle");
            PosterUrl = getArguments().getString("posterPath");
            BackdropPath = getArguments().getString("backdropPath");
            id = getArguments().getString("id");
            Tagger = getArguments().getInt("tagger");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_movie, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout;
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        BackdropV = (NetworkImageView) view.findViewById(R.id.moviePoster);
        BackdropV.setTransitionName("posterX");
        final LinearLayout ratingCard = (LinearLayout) view.findViewById(R.id.linearCard);
//        final LinearLayout overviewCard = (LinearLayout) view.findViewById(R.id.overviewCard);
        posterImage = (NetworkImageView) view.findViewById(R.id.posterImage);
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
        initViews(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchData();
    }

    private void initViews(View view) {
        AwardsV = (TextView) view.findViewById(R.id.awards);
        MetascoreV = (TextView) view.findViewById(R.id.metascore);
        ImdbRatingV = (TextView) view.findViewById(R.id.imdbRate);
        ImdbVoteCountV = (TextView) view.findViewById(R.id.imdbVoteCount);
        TmdbRatingV = (TextView) view.findViewById(R.id.tmdbRate);
        TmdbVoteCountV = (TextView) view.findViewById(R.id.tmdbVoteCount);
        TomatoConsensusV = (TextView) view.findViewById(R.id.tomatoConsensus);
        RottenCriticV = (TextView) view.findViewById(R.id.rottenCritic);
        CriticRatingV = (TextView) view.findViewById(R.id.criticRating);
        RottenUserMeterV = (TextView) view.findViewById(R.id.rottenUserMeter);
        RottenUserRatingV = (TextView) view.findViewById(R.id.rottenUserRating);
        RatedV = (TextView) view.findViewById(R.id.rated);
        TaglineV = (TextView) view.findViewById(R.id.tagline);
        OverviewV = (TextView) view.findViewById(R.id.overview);
        ReleaseDateV = (TextView) view.findViewById(R.id.ReleaseDate);
        RuntimeV = (TextView) view.findViewById(R.id.runtime);
        StatusV = (TextView) view.findViewById(R.id.status);
        BudgetV = (TextView) view.findViewById(R.id.budget);
        RevenueV = (TextView) view.findViewById(R.id.revenue);
        GenreV = (TextView) view.findViewById(R.id.genre);
        AdultV = (TextView) view.findViewById(R.id.adult);
        HomepageV = (TextView) view.findViewById(R.id.homepage);
        CollectionNameV = (TextView) view.findViewById(R.id.collectionName);
        CastNameV[0] = (TextView) view.findViewById(R.id.Cast1Name);
        CastNameV[1] = (TextView) view.findViewById(R.id.Cast2Name);
        CastNameV[2] = (TextView) view.findViewById(R.id.Cast3Name);
        CastCharacterV[0] = (TextView) view.findViewById(R.id.cast1Character);
        CastCharacterV[1] = (TextView) view.findViewById(R.id.cast2Character);
        CastCharacterV[2] = (TextView) view.findViewById(R.id.cast3Character);
        CrewNameV[0] = (TextView) view.findViewById(R.id.crew1Name);
        CrewNameV[1] = (TextView) view.findViewById(R.id.crew2Name);
        CrewNameV[2] = (TextView) view.findViewById(R.id.crew3Name);
        CrewJobV[0] = (TextView) view.findViewById(R.id.crew1Character);
        CrewJobV[1] = (TextView) view.findViewById(R.id.crew2Character);
        CrewJobV[2] = (TextView) view.findViewById(R.id.crew3Character);
        ProdCompNameV[0] = (TextView) view.findViewById(R.id.prodComp1Name);
        ProdCompNameV[1] = (TextView) view.findViewById(R.id.prodComp2Name);
        ProdCompNameV[2] = (TextView) view.findViewById(R.id.prodComp3Name);
        SimMovNameV[0] = (TextView) view.findViewById(R.id.simMov1Name);
        SimMovNameV[1] = (TextView) view.findViewById(R.id.simMov2Name);
        SimMovNameV[2] = (TextView) view.findViewById(R.id.simMov3Name);
        TCTV = (TextView) view.findViewById(R.id.tctv);
//        SimMovYearV[0] = (TextView) view.findViewById(R.id.simMov1Year);
//        SimMovYearV[1] = (TextView) view.findViewById(R.id.simMov1Year);
//        SimMovYearV[2] = (TextView) view.findViewById(R.id.simMov1Year);
        HorizontalImageViewV = (LinearLayout) view.findViewById(R.id.horizontalImageView);
        LinearMain = (LinearLayout) view.findViewById(R.id.layoutMain);
        BTC = (LinearLayout) view.findViewById(R.id.btc);
        CastV = (LinearLayout) view.findViewById(R.id.cast);
        CrewV = (LinearLayout) view.findViewById(R.id.crew);
        SimilarV = (LinearLayout) view.findViewById(R.id.similar);
        HompV = (LinearLayout) view.findViewById(R.id.homp);
        SimilarOnClickV[0] = (LinearLayout) view.findViewById(R.id.simMovOnClick1);
        SimilarOnClickV[1] = (LinearLayout) view.findViewById(R.id.simMovOnClick2);
        SimilarOnClickV[2] = (LinearLayout) view.findViewById(R.id.simMovOnClick3);
        IMDBV = (RelativeLayout) view.findViewById(R.id.imdb);
        MetaV = (RelativeLayout) view.findViewById(R.id.meta);
        RTCV = (RelativeLayout) view.findViewById(R.id.RTC);
        RTUV = (RelativeLayout) view.findViewById(R.id.RTU);
        TMDBV = (RelativeLayout) view.findViewById(R.id.tmdb);
//        GoToCollectionV = (RelativeLayout) view.findViewById(R.id.goToCollection);
        CastPosterV[0] = (NetworkImageView) view.findViewById(R.id.cast1Poster);
        CastPosterV[1] = (NetworkImageView) view.findViewById(R.id.cast2Poster);
        CastPosterV[2] = (NetworkImageView) view.findViewById(R.id.cast3Poster);
        CrewPosterV[0] = (NetworkImageView) view.findViewById(R.id.crew1Poster);
        CrewPosterV[1] = (NetworkImageView) view.findViewById(R.id.crew2Poster);
        CrewPosterV[2] = (NetworkImageView) view.findViewById(R.id.crew3Poster);
        ProdCompPosterV[0] = (NetworkImageView) view.findViewById(R.id.prodComp1Poster);
        ProdCompPosterV[1] = (NetworkImageView) view.findViewById(R.id.prodComp2Poster);
        ProdCompPosterV[2] = (NetworkImageView) view.findViewById(R.id.prodComp3Poster);
        SimMovPosterV[0] = (NetworkImageView) view.findViewById(R.id.simMov1Poster);
        SimMovPosterV[1] = (NetworkImageView) view.findViewById(R.id.simMov2Poster);
        SimMovPosterV[2] = (NetworkImageView) view.findViewById(R.id.simMov3Poster);
        CollectionPosterV = (NetworkImageView) view.findViewById(R.id.collectionPoster);
        CastFullV = (FloatingActionButton) view.findViewById(R.id.castFull);
        CrewFullV = (FloatingActionButton) view.findViewById(R.id.crewFull);
        FABMain = (FloatingActionButton) view.findViewById(R.id.fab);
        ProdCompFullV = (FloatingActionButton) view.findViewById(R.id.prodCompFull);
        SimMovFullV = (FloatingActionButton) view.findViewById(R.id.simMovFull);
        ProdCompCard[0] = (CardView) view.findViewById(R.id.prodComp1);
        ProdCompCard[1] = (CardView) view.findViewById(R.id.prodComp2);
        ProdCompCard[2] = (CardView) view.findViewById(R.id.prodComp3);
        SimMovCard[0] = (CardView) view.findViewById(R.id.simMov1);
        SimMovCard[1] = (CardView) view.findViewById(R.id.simMov2);
        SimMovCard[2] = (CardView) view.findViewById(R.id.simMov3);

//        CastCard[0] = (CardView) view.findViewById(R.id.Cast1);
//        CastCard[1] = (CardView) view.findViewById(R.id.Cast2);
//        CastCard[2] = (CardView) view.findViewById(R.id.Cast3);
//        CrewCard[0] = (CardView) view.findViewById(R.id.Crew1);
//        CrewCard[1] = (CardView) view.findViewById(R.id.Crew2);
//        CrewCard[2] = (CardView) view.findViewById(R.id.Crew3);
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
                Toast.makeText(getActivity(), "Error Fetching Movie Details!!", Toast.LENGTH_SHORT).show();
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

        if (response.has(Keys.Main.IMDB_ID) && !response.isNull(Keys.Main.IMDB_ID))
            ImdbId = response.getString(Keys.Main.IMDB_ID);

        fetchOmdb(ImdbId);

        if (response.has(Keys.Main.VIDEOS) && !response.isNull(Keys.Main.VIDEOS)) {
            JSONObject VideoObject = response.getJSONObject(Keys.Main.VIDEOS);
            Video = VideoObject.getJSONArray(Keys.Main.RESULTS);
            if (Video != null && Video.length() > 0) {
                JSONObject VideoObjectFinal = Video.getJSONObject(0);
                VideoKey = "http://www.youtube.com/watch?v=" + VideoObjectFinal.getString(Keys.Main.KEY);
                FABMain.setVisibility(View.VISIBLE);
            } else {
                FABMain.setVisibility(View.GONE);
            }
        }
        if (response.has(Keys.Main.ADULT) && !response.isNull(Keys.Main.ADULT)) {
            Adult = response.getString(Keys.Main.ADULT);
            if (Adult.equals("false"))
                Adult = "No";
            else
                Adult = "Yes";
        }
        if (response.has(Keys.Main.BUDGET) && !response.isNull(Keys.Main.BUDGET)) {
            Budget = response.getString(Keys.Main.BUDGET);
            if (Budget.equals("0"))
                Budget = "N/A";
            else
                Budget = "$" + Budget;
        }
        if (response.has(Keys.Main.HOMEPAGE) && !response.isNull(Keys.Main.HOMEPAGE)) {
            Homepage = response.getString(Keys.Main.HOMEPAGE);
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
        if (response.has(Keys.Main.OVERVIEW) && !response.isNull(Keys.Main.OVERVIEW))
            Overview = response.getString(Keys.Main.OVERVIEW);
        if (response.has(Keys.Main.REVENUE) && !response.isNull(Keys.Main.REVENUE)) {
            Revenue = response.getString(Keys.Main.REVENUE);
            if (Revenue.equals("0"))
                Revenue = "N/A";
            else
                Revenue = "$" + Revenue;
        }
        if (response.has(Keys.Main.RUNTIME) && !response.isNull(Keys.Main.RUNTIME))
            Runtime = response.getString(Keys.Main.RUNTIME) + " Mins";
        if (response.has(Keys.Main.STATUS) && !response.isNull(Keys.Main.STATUS))
            Status = response.getString(Keys.Main.STATUS);
        if (response.has(Keys.Main.TAGLINE) && !response.isNull(Keys.Main.TAGLINE)) {
            Tagline = response.getString(Keys.Main.TAGLINE);
            TaglineV.setText(Tagline);
            TaglineV.setVisibility(View.VISIBLE);
        }
        if (response.has(Keys.Main.RATING) && !response.isNull(Keys.Main.RATING)) {
            Rating = response.getString(Keys.Main.RATING);
            if (!Rating.equals("N/A")) {
                String RatX = String.format("%.1f", Float.parseFloat(Rating)) + "/10";
                TmdbRatingV.setText(RatX);
                TMDBV.setVisibility(View.VISIBLE);
                if (response.has(Keys.Main.RATING_COUNT) && !response.isNull(Keys.Main.RATING_COUNT)) {
                    RatingCount = response.getString(Keys.Main.RATING_COUNT);
                    TmdbVoteCountV.setText(RatingCount);
                }
            }
        }

        if (response.has(Keys.Main.GENRE) && !response.isNull(Keys.Main.GENRE)) {
            GenreJsonArray = response.getJSONArray(Keys.Main.GENRE);
            for (int i = 0; i < GenreJsonArray.length(); i++) {
                JSONObject temp = GenreJsonArray.getJSONObject(i);
                Genre += temp.getString(Keys.Main.NAME);
                if (!(i + 1 == GenreJsonArray.length()))
                    Genre += ", ";
            }
        }
        if (response.has(Keys.Main.BELONGS_TO_COLLECTION) && !response.isNull(Keys.Main.BELONGS_TO_COLLECTION)) {
            JSONObject temp = response.getJSONObject(Keys.Main.BELONGS_TO_COLLECTION);
            if (temp.has(Keys.Main.ID))
                CollectionId = temp.getString(Keys.Main.ID);
            if (temp.has(Keys.Main.NAME))
                CollectionName = temp.getString(Keys.Main.NAME);
            if (temp.has(Keys.Main.POSTER_PATH) && !temp.isNull(Keys.Main.POSTER_PATH))
                CollectionPoster = temp.getString(Keys.Main.POSTER_PATH);
            CollectionNameV.setText(CollectionName);
            if (CollectionPoster != null)
                CollectionPosterV.setImageUrl("http://image.tmdb.org/t/p/w150" + CollectionPoster, mImageLoader);
        } else {
            BTC.setVisibility(View.GONE);
        }
        if (response.has(Keys.Main.PRODUCTION_COMPANIES) && !response.isNull(Keys.Main.PRODUCTION_COMPANIES)) {
            ProdComp = response.getJSONArray(Keys.Main.PRODUCTION_COMPANIES);
            for (int i = 0; i < ProdComp.length() && i < 3; i++) {
                JSONObject temp = ProdComp.getJSONObject(i);
                if (temp.has(Keys.Main.ID))
                    ProdCompId = temp.getString(Keys.Main.ID);
                if (temp.has(Keys.Main.NAME))
                    ProdCompName = temp.getString(Keys.Main.NAME);

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
                Toast.makeText(getActivity(), "Error Fetching OMDB Data!!", Toast.LENGTH_SHORT).show();
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

        if (response.has(Keys.Main.RELEASED) && !response.isNull(Keys.Main.RELEASED))
            Released = response.getString(Keys.Main.RELEASED);
        if (response.has(Keys.Main.RATED) && !response.isNull(Keys.Main.RATED))
            Rated = response.getString(Keys.Main.RATED);
        if (response.has(Keys.Main.AWARDS) && !response.isNull(Keys.Main.AWARDS))
            Awards = response.getString(Keys.Main.AWARDS);
        if (response.has(Keys.Main.METASCORE) && !response.isNull(Keys.Main.METASCORE)) {
            Metascore = response.getString(Keys.Main.METASCORE);
            if (!Metascore.equals("N/A"))
                Metascore += " %";
        }
        if (response.has(Keys.Main.IMDB_RATING) && !response.isNull(Keys.Main.IMDB_RATING)) {
            ImdbRating = response.getString(Keys.Main.IMDB_RATING);
            if (!ImdbRating.equals("N/A"))
                ImdbRating += "/10";
        }
        if (response.has(Keys.Main.IMDB_VOTES) && !response.isNull(Keys.Main.IMDB_VOTES))
            ImdbVotes = response.getString(Keys.Main.IMDB_VOTES);
        if (response.has(Keys.Main.TOMATO_METER) && !response.isNull(Keys.Main.TOMATO_METER)) {
            TomatoMeter = response.getString(Keys.Main.TOMATO_METER);
            if (!TomatoMeter.equals("N/A"))
                TomatoMeter += "%";
        }
        if (response.has(Keys.Main.TOMATO_RATING) && !response.isNull(Keys.Main.TOMATO_RATING)) {
            TomatoRating = response.getString(Keys.Main.TOMATO_RATING);
            if (!TomatoRating.equals("N/A"))
                TomatoRating += "/10";
            else
                CriticRatingV.setVisibility(View.GONE);
        }
        if (response.has(Keys.Main.TOMATO_CONSENSUS) && !response.isNull(Keys.Main.TOMATO_CONSENSUS)) {
            TomatoConsensus = response.getString(Keys.Main.TOMATO_CONSENSUS);
            if (!TomatoConsensus.equals("N/A")) {
                TomatoConsensusV.setVisibility(View.VISIBLE);
                TCTV.setVisibility(View.VISIBLE);
            }
        }
        if (response.has(Keys.Main.TOMATO_USER_METER) && !response.isNull(Keys.Main.TOMATO_USER_METER)) {
            TomatoUserMeter = response.getString(Keys.Main.TOMATO_USER_METER);
            if (!TomatoUserMeter.equals("N/A"))
                TomatoUserMeter += "%";
        }
        if (response.has(Keys.Main.TOMATO_USER_RATING) && !response.isNull(Keys.Main.TOMATO_USER_RATING)) {
            TomatoUserRating = response.getString(Keys.Main.TOMATO_USER_RATING);
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
                    if (response.has(Keys.Main.LOGO_PATH) && !response.isNull(Keys.Main.LOGO_PATH)) {
                        ProdCompPosterV[i].setBackgroundColor(0xffffffff);
                        ProdCompPosterV[i].setImageUrl("http://image.tmdb.org/t/p/w150" + response.getString(Keys.Main.LOGO_PATH), mImageLoader);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error Fetching Production Details!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private void fetchSimilar() {
        final String fetchDataUrl = "https://api.themoviedb.org/3/movie/" + id + "/similar?api_key=39f9635028e0a25da413d4e90255bb20";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SimilarV.setVisibility(View.VISIBLE);
                JSONArray SimMovArray;
                if (response.has(Keys.Main.RESULTS) && !response.isNull(Keys.Main.RESULTS)) {
                    try {
//                        JSONObject SimMov = response.getJSONObject(Main.SIMILAR);
                        SimMovArray = response.getJSONArray(Keys.Main.RESULTS);
                        for (int i = 0; i < SimMovArray.length() && i < 3; i++) {
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

                            SimMovNameV[i].setText(SimMovTitle);
                            SimMovNameV[i].setTag(SimMovId);
                            if (SimMovPoster != null)
                                SimMovPosterV[i].setImageUrl("http://image.tmdb.org/t/p/w150" + SimMovPoster, mImageLoader);
                            final String finalSimMovTitle = SimMovTitle;
                            final String finalSimMovBackdrop = SimMovBackdrop;
                            final String finalSimMovPoster = SimMovPoster;
                            final String finalSimMovId = SimMovId;
                            SimilarOnClickV[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Bundle b = new Bundle();
                                    b.putString("movieTitle", finalSimMovTitle);
                                    b.putString("backdropPath", "http://image.tmdb.org/t/p/w500" + finalSimMovBackdrop);
                                    b.putString("posterPath", "http://image.tmdb.org/t/p/w300" + finalSimMovPoster);
                                    b.putString("id", finalSimMovId);

                                    Activity activity = getActivity();
                                    if (activity != null && isAdded())
                                        ((DisplayMovie) activity).changeFrag(b, Tagger);
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
                        SimMovFullV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle b = new Bundle();
                                b.putString("fetchDataUrl",fetchDataUrl);
                                b.putInt("color",color[0]);
                                Activity activity = getActivity();
                                if (activity != null && isAdded())
                                    ((DisplayMovie) activity).changeSimMov(b, Tagger);
                            }
                        });
                    } catch (JSONException e) {
                        Log.e("Similar", "Fetch Error");
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error Fetching Similar Movie Details!!", Toast.LENGTH_SHORT).show();
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
                    if (response.has(Keys.Main.BACKDROPS) && !response.isNull(Keys.Main.BACKDROPS)) {
//                        JSONObject ImagesObj = response.getJSONObject(Main.IMAGES);
                        JSONArray ImagesArray = response.getJSONArray(Keys.Main.BACKDROPS);
                        String ImagePath = null;
                        for (int i = 0; i < ImagesArray.length(); i++) {
                            JSONObject temp = ImagesArray.getJSONObject(i);
                            if (temp.has(Keys.Main.FILE_PATH) && !temp.isNull(Keys.Main.FILE_PATH)) {
                                ImagePath = temp.getString(Keys.Main.FILE_PATH);
                                ImagePaths.add(ImagePath);
                            }
                        }
                        BackdropV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), ImageviewFull.class);
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
                            if (temp.has(Keys.Main.FILE_PATH) && !temp.isNull(Keys.Main.FILE_PATH))
                                ImagePath = temp.getString(Keys.Main.FILE_PATH);

                            final NetworkImageView Images;
                            Activity activity = getActivity();
                            if (activity != null && isAdded()) {
                                Images = new NetworkImageView(getActivity());
                                Images.setLayoutParams(params);
                                Images.setAdjustViewBounds(true);
                                Images.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                Images.setTransitionName("fullView");
                                Images.setImageUrl("http://image.tmdb.org/t/p/w500" + ImagePath, mImageLoader);

                                final int finalI = i;
                                Images.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(), ImageviewFull.class);
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
                    }
                    if (response.has(Keys.Main.POSTERS) && !response.isNull(Keys.Main.POSTERS)) {
//                        JSONObject ImagesObj = response.getJSONObject(Main.IMAGES);
                        JSONArray ImagesArray = response.getJSONArray(Keys.Main.POSTERS);
                        String PosterPath;
                        for (int i = 0; i < ImagesArray.length(); i++) {
                            JSONObject temp = ImagesArray.getJSONObject(i);
                            if (temp.has(Keys.Main.FILE_PATH) && !temp.isNull(Keys.Main.FILE_PATH)) {
                                PosterPath = temp.getString(Keys.Main.FILE_PATH);
                                PosterPaths.add(PosterPath);
                            }
                        }
                        posterImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), ImageviewFull.class);
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
                Toast.makeText(getActivity(), "Error Fetching Images!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private void fetchCredits() {
        final String fetchDataUrl = "https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=39f9635028e0a25da413d4e90255bb20";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchDataUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                CastV.setVisibility(View.VISIBLE);
                CrewV.setVisibility(View.VISIBLE);
                try {
                    if (response.has(Keys.Main.CAST) && !response.isNull(Keys.Main.CAST)) {
                        JSONArray CastTemp, CrewTemp;
//                        CreditsTemp = response.getJSONObject(Main.CREDITS);
                        CastTemp = response.getJSONArray(Keys.Main.CAST);
                        for (int i = 0; i < CastTemp.length() && i < 3; i++) {
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

                            CastNameV[i].setText(CastName);
                            CastCharacterV[i].setText(CastCharacter);
                            CastNameV[i].setTag(CastName);
                            if (CastProfile != null)
                                CastPosterV[i].setImageUrl("http://image.tmdb.org/t/p/w150" + CastProfile, mImageLoader);

                        }
                        if (response.has(Keys.Main.CREW) && !response.isNull(Keys.Main.CREW)) {
                            CrewTemp = response.getJSONArray(Keys.Main.CREW);
                            for (int i = 0; i < CrewTemp.length() && i < 3; i++) {
                                String CrewId = null, CrewName = null, CrewJob = null, CrewProfile = null;
                                JSONObject temp = CrewTemp.getJSONObject(i);
                                if (temp.has(Keys.Main.ID))
                                    CrewId = temp.getString(Keys.Main.ID);
                                if (temp.has(Keys.Main.NAME))
                                    CrewName = temp.getString(Keys.Main.NAME);
                                if (temp.has(Keys.Main.JOB))
                                    CrewJob = temp.getString(Keys.Main.JOB);
                                if (temp.has(Keys.Main.PROFILE_PATH) && !temp.isNull(Keys.Main.PROFILE_PATH))
                                    CrewProfile = temp.getString(Keys.Main.PROFILE_PATH);

                                CrewNameV[i].setText(CrewName);
                                CrewJobV[i].setText(CrewJob);
                                CrewNameV[i].setTag(CrewName);
                                if (CrewProfile != null)
                                    CrewPosterV[i].setImageUrl("http://image.tmdb.org/t/p/w150" + CrewProfile, mImageLoader);
                            }
                        }
                        CastFullV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle b = new Bundle();
                                b.putString("fetchDataUrl",fetchDataUrl);
                                b.putInt("color",color[0]);
                                b.putString("movieTitle",movieTitle);
                                Activity activity = getActivity();
                                if (activity != null && isAdded())
                                    ((DisplayMovie) activity).changeCast(b, Tagger);
                            }
                        });
                        CrewFullV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle b = new Bundle();
                                b.putString("movieTitle",movieTitle);
                                b.putString("fetchDataUrl",fetchDataUrl);
                                b.putInt("color",color[0]);
                                Activity activity = getActivity();
                                if (activity != null && isAdded())
                                    ((DisplayMovie) activity).changeCrew(b, Tagger);
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
                Toast.makeText(getActivity(), "Error Fetching Credits Details!!", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private int dpToPx(int dp) {
        Activity activity = getActivity();
        int x = 0;
        if (activity != null && isAdded()) {
            DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
            x = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        }
        return x;
    }


}
