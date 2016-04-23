package rendezvous.activiti;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by James on 4/6/2016.
 */
public class VolleySingleton {
    private static VolleySingleton sInstance = null;
    private RequestQueue sQueue;
    private ImageLoader sImageLoader;

    //volley singleton, because a new request queue is too expensive
    public static VolleySingleton getInstance() {
        if(sInstance == null){
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }

    //This way, Volley.newRequestQueue only needs to be called once
    public RequestQueue getRequestQueue(){
        if(sQueue == null){
            sQueue = Volley.newRequestQueue(MyApplication.returnInstance().getApplicationContext());
        }
        return sQueue;
    }

    //Image loader, because images were going to be a part of the app, images are unimplemented
    private VolleySingleton() {
        sQueue = Volley.newRequestQueue(MyApplication.returnInstance().getApplicationContext());
        sImageLoader = new ImageLoader(sQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);

            @Override
            public Bitmap getBitmap(String url){
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }
}
