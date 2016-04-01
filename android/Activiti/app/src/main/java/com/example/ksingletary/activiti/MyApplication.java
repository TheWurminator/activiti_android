package com.example.ksingletary.activiti;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by James on 4/1/2016.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static RequestQueue mQueue;
    private static final String TAG = "DEFAULT";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication returnInstance(){
        if(mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }

    public RequestQueue getRequestQueue(){

        if(mQueue == null) {
            mQueue = Volley.newRequestQueue(this.getApplicationContext());
        }
        return mQueue;
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
        if(mQueue != null){
            mQueue.cancelAll(tag);
        }
    }
}
