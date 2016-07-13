package com.geeky.adarsh.moviescollection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.geeky.adarsh.moviescollection.Volley.VolleySingleton;

public class DisplayMovie extends AppCompatActivity {
    AppBarLayout mAppBarLayout;
    private VolleySingleton mVolleySingleton = VolleySingleton.getInstance();
    private ImageLoader mImageLoader = mVolleySingleton.getImageLoader();
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_movie);
        supportPostponeEnterTransition();

        Toolbar toolbar;
        Intent i = getIntent();
        Bundle b = getIntent().getExtras();
        Bitmap bitmap = i.getParcelableExtra("BitmapImage");
        String x = b.getString("movieTitle");
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
        collapsingToolbarLayout.setTitle(x);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        NetworkImageView ix = (NetworkImageView) findViewById(R.id.moviePoster);
        String img = b.getString("posterPath");
//        ix.setImageBitmap(bitmap);
        ix.setImageUrl(img, mImageLoader);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordLay);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

//        mAppBarLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                int heightPx = findViewById(R.id.app_bar_layout).getHeight();
//                setAppBarOffset(dpToPx(256));
//                Toast.makeText(getApplicationContext(), "d" + dpToPx(256), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void setAppBarOffset(int offsetPx) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedPreScroll(mCoordinatorLayout, mAppBarLayout, null, 0, offsetPx, new int[]{0, 0});
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selected = item.getItemId();
        if (selected == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
