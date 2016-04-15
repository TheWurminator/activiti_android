package rendezvous.activiti;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ksingletary on 4/15/16.
 */
public class CustomJSONRequest extends JsonObjectRequest {

    private String token = "YPTVjthbt365PsNJPzmBAzVCAqQOptTM3bHIUz6C47ccmuomo19sJ6p3ukYQ8uvUwRUMab9CNlWPpA7ALOtnj7rCWxHdPBCaRqhwUPZuAzSaRsZoopQekYlAn3RkUAqFwrsxmT3ZqTY8JVCY0OPjhKIRRmr2QryMI0GDvLA2JO0Fix7C2TQm7hMNys6Gv8lHWZNNyTTXtIbEPdyjYKd7RnxH36FV0auasAWjHgHuBbyOLB1H2Nbdw4Ku5JlOJQk";

    public CustomJSONRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        Map headers = new HashMap();
        headers.put("token", token);
        return headers;
    }
}
