package com.example.ksingletary.activiti;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by James on 4/1/2016.
 */
public class VolleySingleton {
    private static VolleySingleton sInstance = null;
    private RequestQueue sQueue;
    private ImageLoader sImageLoader;
    private static final String TAG = "DEFAULT";

    private VolleySingleton(){
        sQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        sImageLoader = new ImageLoader(sQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingleton getsInstance() {
        if(sInstance == null){
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        return sQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag){
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequest(Request<T> request){
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelAllRequest(Object tag){
        if(sQueue != null){
            sQueue.cancelAll(tag);
        }
    }
}
