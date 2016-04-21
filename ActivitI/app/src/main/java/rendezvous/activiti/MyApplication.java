package rendezvous.activiti;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;

/**
 * Created by James on 4/6/2016.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance = new MyApplication();

    @Override
    public void onCreate(){
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
}
