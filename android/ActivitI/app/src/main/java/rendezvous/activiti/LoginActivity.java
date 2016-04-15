package rendezvous.activiti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.FacebookSdk;

public class LoginActivity extends AppCompatActivity {

   // private CallbackManager callbackManager;
    private String responseString = "Test";
    private WebLoginFragment webLoginFragment = new WebLoginFragment();
    private LoginFragment loginFragment = new LoginFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(MyApplication.getAppContext());
        setContentView(R.layout.content_login_page);
        //navigate(loginFragment);
        //sendRequest();
    }

    public void loginServer(View view){
        //Intent i = new Intent(Intent.ACTION_VIEW,
        //        Uri.parse("https://activiti.servebeer.com:8081/api/login/facebook"));

        // Starts Implicit Activity
        //startActivity(i);
        setContentView(R.layout.content_web_login);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://activiti.servebeer.com:8081/api/login/facebook");
        //myWebView.loadUrl("http://www.google.com");
    }

    public void navigate(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.loginFragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /*public void loginClick(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_about_me", "user_birthday"));
    }*/

    public void sendRequest(){
        VolleySingleton volleySing = VolleySingleton.getInstance();
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();

        StringRequest testRequest = new StringRequest(Request.Method.GET, "https://www.google.com/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //setResponseString(response);
                //TextView testView = (TextView) findViewById(R.id.testView);
                //testView.setText(getResponseString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                System.out.println("Something went wrong somewhere.");
                error.printStackTrace();
            }
        });

        queue.add(testRequest);
    }

    public void setResponseString(String val){
        responseString = val;
    }

    public String getResponseString(){
        return responseString;
    }

    public void getmein(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}