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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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
                    Toast.makeText(MyApplication.getAppContext(), "Response Good", Toast.LENGTH_LONG).show();

                    requestCallBack.callback(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MyApplication.getAppContext(), "Response Err" + error, Toast.LENGTH_LONG).show();
                    Log.d("fb", error.toString());
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
}
