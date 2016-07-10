package com.geeky.adarsh.moviescollection;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.geeky.adarsh.moviescollection.Fragments.FragmentBoxOffice;
import com.geeky.adarsh.moviescollection.Fragments.FragmentSearch;
import com.geeky.adarsh.moviescollection.Fragments.FragmentSomething;
import com.geeky.adarsh.moviescollection.ThirdParty.SlidingTabLayout;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        setup();

    }

    private void setup() {

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts);
        navigationTabStrip.setViewPager(mViewPager);
        navigationTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigationTabStrip.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {
            }

            @Override
            public void onEndTabSelected(String title, int index) {

            }
        });
    }

//    private String getIcon(int i) {
//        int icons[] = {R.drawable.ic_search_white_24dp, R.drawable.ic_trending_up_white_24dp, R.drawable.ic_info_white_24dp};
//        Drawable drawable = getDrawable(icons[i]);
//        ImageSpan mImageSpan = new ImageSpan(drawable);
//        SpannableString mSpannableString = new SpannableString(" ");
//        mSpannableString.setSpan(mImageSpan, 0, mSpannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return (String)mSpannableString;
//    }

    class MyAdapter extends FragmentPagerAdapter {


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = FragmentSearch.newInstance("", "");
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
