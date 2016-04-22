package rendezvous.activiti;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mor on 4/22/16.
 */
public class RequestManager {
    public static void sendRequest(int requestMethod, String path, final HashMap<String, String> headerMap, JSONObject body, final RequestCallBack requestCallBack) {
        //Build JSON Request to send to server
        JsonObjectRequest request = new JsonObjectRequest(requestMethod, path, body,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(MyApplication.getAppContext(), "Response Good", Toast.LENGTH_LONG).show();
 //                   Log.d("fb", response.toString());

                    requestCallBack.callback(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Log.d("volleyerr", Integer.toString(error.networkResponse.statusCode));

                    Toast.makeText(MyApplication.getAppContext(), "Response Err" , Toast.LENGTH_LONG).show();
                    Log.d("fberr", error.toString());
                }
            }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };

        //Add request to queue
        VolleySingleton.getInstance().getRequestQueue().add(request);
    }

    public static void sendArrayRequest(int requestMethod, String path, final HashMap<String, String> headerMap, JSONArray body, final RequestArrayCallBack requestArrayCallBack) {
        //Build JSON Request to send to server
        JsonArrayRequest request = new JsonArrayRequest(requestMethod, path, body,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(MyApplication.getAppContext(), "Response Array Good", Toast.LENGTH_LONG).show();
                        Log.d("response5", response.toString());
                        requestArrayCallBack.callback(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyApplication.getAppContext(), "Response Array Err" + error , Toast.LENGTH_LONG).show();
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };

        //Add request to queue
        VolleySingleton.getInstance().getRequestQueue().add(request);
    }
        //Add request to queue
}
