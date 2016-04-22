package rendezvous.activiti;

import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by dm on 4/22/16.
 */
public class MyJavaScriptInterface {
    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processHTML(String html) {
        String token = stripToken(html);
        //Saves the token into a local storage
        SharedPreferences sharedPref = MyApplication.getAppContext().getSharedPreferences(MyApplication.getAppContext().getString(R.string.token_store), 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(MyApplication.getAppContext().getString(R.string.token_store), token);
        editor.commit();
    }
    //Takes the response from the server and strips the token
    public String stripToken(String html) {
        int start = 19;
        int end = html.length() - 7;
        return html.substring(start, end);
    }
}
