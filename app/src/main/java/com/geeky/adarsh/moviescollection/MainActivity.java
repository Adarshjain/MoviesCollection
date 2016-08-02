package com.geeky.adarsh.moviescollection;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.geeky.adarsh.moviescollection.Fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_main);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment mainFragment = MainFragment.newInstance("", "");
        ft.add(R.id.dummy_main, mainFragment);
        ft.commit();
    }

    public Activity getContext() {
        return this;
    }

}