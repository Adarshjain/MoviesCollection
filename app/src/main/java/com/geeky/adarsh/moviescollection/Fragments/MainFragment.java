package com.geeky.adarsh.moviescollection.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.geeky.adarsh.moviescollection.R;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;


    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.appBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setup(view);
//        Log.e("Frag","Start");
//        FragmentManager manager = getChildFragmentManager();
//        FragmentTransaction ft = manager.beginTransaction();
//        Fragment mainFragment = DummyFragment.newInstance("","");
//        ft.add(R.id.activity_main, mainFragment);
//        ft.commit();
//        Log.e("Frag","End");
        return view;
    }


    private void setup(View view) {

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new MyAdapter(getFragmentManager()));
        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) view.findViewById(R.id.nts);
        mViewPager.setCurrentItem(1);
        setWindowColor();
        navigationTabStrip.setViewPager(mViewPager);
    }

    private void setWindowColor() {
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
    }


    private class MyAdapter extends FragmentPagerAdapter {


        MyAdapter(FragmentManager fm) {
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