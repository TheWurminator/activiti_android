package rendezvous.activiti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

   // private CallbackManager callbackManager;
    private String responseString = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.content_login);
        CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        //sendRequest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
