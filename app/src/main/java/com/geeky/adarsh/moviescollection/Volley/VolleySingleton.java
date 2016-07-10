package com.geeky.adarsh.moviescollection.Volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.geeky.adarsh.moviescollection.MyVolleySingleton;

public class VolleySingleton {
    private static VolleySingleton VSInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(MyVolleySingleton.getContext());
        mImageLoader = new ImageLoader(mRequestQueue,new ImageLoader.ImageCache(){

            private LruCache<String,Bitmap> cache = new LruCache<>((int) ((Runtime.getRuntime().maxMemory()/1024)/8));

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
    }

    public static VolleySingleton getInstance() {
        if (VSInstance == null) {
            VSInstance = new VolleySingleton();
        }
        return VSInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
