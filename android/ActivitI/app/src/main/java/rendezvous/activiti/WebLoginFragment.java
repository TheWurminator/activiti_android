package rendezvous.activiti;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by ksingletary on 4/15/16.
 */
public class WebLoginFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_web_login, container, false);
        //WebView myWebView = (WebView) getView().findViewById(R.id.webview);
        //myWebView.loadUrl("https://activiti.servebeer.com:8081/api/login/facebook");
        return view;
    }
}
