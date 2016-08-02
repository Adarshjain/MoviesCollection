package com.geeky.adarsh.moviescollection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.geeky.adarsh.moviescollection.Adapter.FullscreenImageview;

import java.util.ArrayList;

public class ImageviewFull extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview_full);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.appBarX);
        setSupportActionBar(toolbar);
        if (toolbar != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        ArrayList<String> paths = b.getStringArrayList("paths");
        int position = b.getInt("position");
        if (toolbar != null)
            getSupportActionBar().setTitle(b.getString("title"));

        FullscreenImageview adapter = new FullscreenImageview(ImageviewFull.this, paths);

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);
//        NetworkImageView NetView = (NetworkImageView) findViewById(R.id.net_imageview);
        FrameLayout frame = (FrameLayout) findViewById(R.id.framehide);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (true) {
                    Toast.makeText(getApplicationContext(),"Tapped! hide",Toast.LENGTH_SHORT).show();
//                    getSupportActionBar().hide();
//                    bool[0] = !bool[0];
//                }else{
                    Toast.makeText(getApplicationContext(),"Tapped! show",Toast.LENGTH_SHORT).show();
//                    getSupportActionBar().show();
//                    bool[0] = !bool[0];
//                }
            }
        });
    }
}
