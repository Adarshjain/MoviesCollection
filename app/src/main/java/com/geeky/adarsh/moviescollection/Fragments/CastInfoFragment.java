package com.geeky.adarsh.moviescollection.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geeky.adarsh.moviescollection.R;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CastInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CastInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CastInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CastInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CastInfoFragment newInstance(String param1, String param2) {
        CastInfoFragment fragment = new CastInfoFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast_info, container, false);
//        http://api.themoviedb.org/3/person/996701?api_key=39f9635028e0a25da413d4e90255bb20&append_to_response=combined_credits,external_ids,images
        // Inflate the layout for this fragment
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new CastInfoFragment.MyAdapter(getFragmentManager()));
        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) view.findViewById(R.id.nts);
        mViewPager.setCurrentItem(1);
        navigationTabStrip.setViewPager(mViewPager);
        return view;
    }

    class MyAdapter extends FragmentPagerAdapter {


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
