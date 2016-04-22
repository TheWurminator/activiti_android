package rendezvous.activiti;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.MapFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {
    private ListResultsFragment listResultsFragment = new ListResultsFragment();
    private ProfileViewFragment profileViewFragment = new ProfileViewFragment();
    private EditProfileFragment editProfileFragment = new EditProfileFragment();
    private ActivitiViewFragment activitiViewFragment = new ActivitiViewFragment();
    private AllActivitiFragment allActivitiFragment = new AllActivitiFragment();
    private CreateActivitiFragment createActivitiFragment = new CreateActivitiFragment();
    private FindActivitiFragment findActivitiFragment = new FindActivitiFragment();
    private FriendProfileViewFragment friendProfileViewFragment = new FriendProfileViewFragment();
    private BadgeViewFragment badgeViewFragment = new BadgeViewFragment();
    private LeaveBadgeFragment leaveBadgeFragment = new LeaveBadgeFragment();
    private ChatFragment chatFragment = new ChatFragment();

    private SlidingMenuFragment slidingMenuFragment = new SlidingMenuFragment();
    SlidingUpPanelLayout slideLayout;

    private JSONObject jsonObject = new JSONObject();
    private final String url = "https://activiti.servebeer.com:8081/api/";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.content_main_menu);
        setContentView(R.layout.content_menu);
        slideLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slideLayout.setTouchEnabled(false);

        trustEveryone();

        ProfileViewFragment profileViewFragment = new ProfileViewFragment();
        navigate(profileViewFragment);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void openSlideMenu(View view) {
        SlidingUpPanelLayout.PanelState temp = slideLayout.getPanelState();
        if(temp.equals(SlidingUpPanelLayout.PanelState.COLLAPSED))slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        else slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
            /*Toast toast = Toast.makeText(MyApplication.getAppContext(), "Back works!", Toast.LENGTH_SHORT);
            toast.show();*/
        }
    }

    public void navigate(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void searchActivities(View view) {
        navigate(listResultsFragment);
        //Code to send search query to server
    }


    public void viewProfile(View view) {
        navigate(profileViewFragment);

        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("token", "admin");

        JSONObject body = new JSONObject();

        String path = getResources().getString(R.string.url) + getResources().getString(R.string.userPath);

        RequestManager.sendRequest(Request.Method.GET, path, headerMap, body, new RequestCallBack() {
            public void callback(JSONObject res) {
                displayProfile(res);
            }
        });
    }

    public void viewActiviti(View view) {
        navigate(activitiViewFragment);
        //sendRequest(Request.Method.GET, "activiti/", jsonObject);
    }

    public void saveProfile(View view) {
        EditText editLocation = (EditText) findViewById(R.id.editLocation);
        EditText editBio = (EditText) findViewById(R.id.editBio);
        EditText editTag = (EditText) findViewById(R.id.editTag0Profile);

        String location = editLocation.getText().toString();
        String bio = editBio.getText().toString();
        String tags = editTag.getText().toString();

        JSONObject updateProfile = new JSONObject();
        try {
            updateProfile.put("location", location);
            updateProfile.put("bio", bio);
            updateProfile.put("tags", tags);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //sendRequest(Request.Method.PUT, "user/", updateProfile);
        viewProfile(view);
    }

    public void submitNewActiviti(View view) {
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editDescription = (EditText) findViewById(R.id.editDescription);
        //EditText editCost = (EditText) findViewByID(R.id.editCost);
        //EditText editMaxAttendees = (EditText) findViewById(R.id.editMaxAttendees);
        Button editDate = (Button) findViewById(R.id.editDateButton);
        Button editTime = (Button) findViewById(R.id.editTimeButton);
        //Button editLocation = (Button) findViewById(R.id.editLocationButton);
        EditText editTag = (EditText) findViewById(R.id.editTag0Activiti);

        String name = editName.getText().toString();
        String description = editDescription.getText().toString();
        //double cost = editCost.getText().toDouble();
        //int maxAttendees = editMaxAttendees.getText.toInt();
        //Date
        //Time
        //double lat
        //double long
        String tags = editTag.getText().toString();

        JSONObject newActiviti = new JSONObject();
        try {
            newActiviti.put("name", name);
            newActiviti.put("description", description);
            //newActiviti.put("cost", cost);
            //newActiviti.put("max_attendee", maxAttendees;
            //newActiviti.put("start_date", startDate);
            //newActiviti.put("end_date", endDate);
            //newActiviti.put("start_time", startTime);
            //newActiviti.put("end_time", endTime);
            //newActiviti.put("latitude", lat);
            //newActiviti.put("longitude", long);
            //newActiviti.put("tags", tags);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //sendRequest(Request.Method.POST, "activiti/", newActiviti);
        viewProfile(view);
    }

    //4/20 - Amon
    public void viewOtherProfile(View view) {
        navigate(friendProfileViewFragment);
    }

    public void useTestSlide(View view) {
        navigate(slidingMenuFragment);
    }

    public void seeAllActivitis(View view) {
        navigate(allActivitiFragment);
    }

    public void editProfile(View view) {
        navigate(editProfileFragment);
    }

    public void createActiviti(View view) {
        navigate(createActivitiFragment);
    }

    public void findActiviti(View view) {
        navigate(findActivitiFragment);
    }

    public void badgeView(View view) {
        navigate(badgeViewFragment);
    }

    public void viewAllActivities(View view) {
        navigate(allActivitiFragment);
    }

    public void leaveBadge(View view) {
        navigate(leaveBadgeFragment);
    }

    public void chatFragment(View view) {
        navigate(chatFragment);
    }

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }

    public void displayProfile(JSONObject userinfo) {
        //Take the information and display to the userry{
        try {
            String name = userinfo.getString("first_name");
            String bio = userinfo.getString("bio");

            Log.d("info", name);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void displayActiviti(JSONObject activitiinfo) {
        //Take the information and display to the user
        try {
            String name = activitiinfo.getString("name");
            String description = activitiinfo.getString("description");
            double cost = activitiinfo.getDouble("cost");
            int maxAttendees = activitiinfo.getInt("max_attendees");
            //Date
            //Time
            //Lat
            //Long
            //Tags
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setDate(View view) {
        // final EditText datePick = (EditText) findViewById(R.id.editDate);
        // datePick.setInputType(EditorInfo.TYPE_NULL);
        Calendar mDate = Calendar.getInstance();
        int mYear = mDate.get(Calendar.YEAR);
        int mMonth = mDate.get(Calendar.MONTH);
        int mDay = mDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                // datePick.setText(Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    public void setTime(View view) {
        // final EditText timePick = (EditText) findViewById(R.id.editTime);
        Calendar mTime = Calendar.getInstance();
        int mHour = mTime.get(Calendar.HOUR_OF_DAY);
        int mMinute = mTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // timePick.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
        }, mHour, mMinute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void chooseLocation(View view) {
        Fragment mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mapContainer, mMapFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://rendezvous.activiti/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://rendezvous.activiti/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
