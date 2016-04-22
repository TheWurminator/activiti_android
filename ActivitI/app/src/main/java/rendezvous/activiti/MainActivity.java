package rendezvous.activiti;


import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.TextView;
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

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
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

    private ListView listView;
    private ArrayList<ActivitiListModel> activitiList = new ArrayList<>();
    private SlidingMenuFragment slidingMenuFragment = new SlidingMenuFragment();
    SlidingUpPanelLayout slideLayout;
    ActivitiAdapter adapter;

    private int dateHour, dateMinute, dateMonth, dateYear, dateDay;
    private int dateMonth2, dateYear2, dateDay2;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_menu);
        
        adapter = new ActivitiAdapter(this, activitiList);
        listView = (ListView)findViewById(R.id.list_menu);
        listView.setAdapter(adapter);


        slideLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slideLayout.setTouchEnabled(false);

        trustEveryone();

        ProfileViewFragment profileViewFragment = new ProfileViewFragment();
        navigate(profileViewFragment);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        viewProfile();
        searchActivities();
    }

    public void openSlideMenu(View view) {
        SlidingUpPanelLayout.PanelState temp = slideLayout.getPanelState();
        if(temp.equals(SlidingUpPanelLayout.PanelState.COLLAPSED))slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        else slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    public void closeSlideMenu(View view) {
        slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    public void navigate(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    public void makeSearch(View view) {
        EditText name = (EditText) findViewById(R.id.search_nameText);
        EditText description = (EditText) findViewById(R.id.search_descriptionText);
        EditText tags = (EditText) findViewById(R.id.search_tagsText);
        EditText maxAttend = (EditText) findViewById(R.id.search_maxattendText);
        EditText minAttend = (EditText) findViewById(R.id.search_minattendText);
        EditText minCost = (EditText) findViewById(R.id.search_mincostText);
        EditText maxCost = (EditText) findViewById(R.id.search_maxcostText);
        EditText maxDist = (EditText) findViewById(R.id.search_maxdistText);
        EditText longitude = (EditText) findViewById(R.id.search_longitudeText);
        EditText latitude = (EditText) findViewById(R.id.search_latitudeText);

        String[] mTags = tags.getText().toString().split(",");
        String mName = name.getText().toString(), mDescription = description.getText().toString();

        try {
            int mStartDay = dateDay, mStartMonth = dateMonth, mStartYear = dateYear, mEndDay = dateDay2,
                    mEndMonth = dateMonth2, mEndYear = dateYear2, mMinAttend = Integer.parseInt(minAttend.getText().toString()),
                    mMaxAttend = Integer.parseInt(maxAttend.getText().toString()), mMinCost = Integer.parseInt(minCost.getText().toString()),
                    mMaxCost = Integer.parseInt(maxCost.getText().toString());
            double mMaxDist = Double.parseDouble(maxDist.getText().toString()), mLongitude = Double.parseDouble(longitude.getText().toString()),
                    mLatitutde = Double.parseDouble(latitude.getText().toString());
        } catch (Exception e) {
        }

        openSlideMenu(view);

        name.setText("");
        description.setText("");
        tags.setText("");
        latitude.setText("");
        longitude.setTag("");
        maxDist.setText("");
        maxCost.setText("");
        minCost.setText("");
        minAttend.setText("");
        maxAttend.setText("");
    }

    private void searchActivities() {
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("token", getToken());

        JSONObject body = new JSONObject();
        try{
            body.put("name", "birthday");

        } catch(Exception e){
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();
        Iterator x = body.keys();
        jsonArray.put(body);
        Log.d("keys3", jsonArray.toString());
        String path = getResources().getString(R.string.url) + getResources().getString(R.string.activitiSearchPath);


        RequestManager.sendArrayRequest(Request.Method.POST, path, headerMap, jsonArray, new RequestArrayCallBack() {
            public void callback(JSONArray res) {
                //Where amon comes in
                
            }
        });

    }

    public void searchActivities(View view) {
        navigate(listResultsFragment);
        searchActivities();
        //Code to send search query to server
    }

    public String getToken() {
        SharedPreferences prefs = getSharedPreferences(MyApplication.getAppContext().getString(R.string.token_store), 0);
        String token = prefs.getString("token", null);
        return token;
    }

    public void viewProfile(View view) {
        closeSlideMenu(view);
        navigate(profileViewFragment);

        viewProfile();
    }

    private void viewProfile() {
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("token", getToken());

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
    }

    public void viewActiviti(String aid) {
        HashMap<String, String> headerMap = new HashMap<String, String>();

        headerMap.put("token", getToken());
        headerMap.put("aid", aid);

        JSONObject body = new JSONObject();

        String path = getResources().getString(R.string.url) + getResources().getString(R.string.activitiPath);

        RequestManager.sendRequest(Request.Method.GET, path, headerMap, body, new RequestCallBack() {
            public void callback(JSONObject res) {
                displayActiviti(res);
            }
        });
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
    @TargetApi(19)
    public void submitNewActiviti(View view) {
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editDescription = (EditText) findViewById(R.id.editDescription);
        EditText editCost = (EditText) findViewById(R.id.moneyTextBox);
        EditText editMaxAttendees = (EditText) findViewById(R.id.attendeesText);
        Button editDate = (Button) findViewById(R.id.editDateButton);
        Button editTime = (Button) findViewById(R.id.editTimeButton);
        Button editLocation = (Button) findViewById(R.id.locationPick);
        EditText editTag = (EditText) findViewById(R.id.editTag0Activiti);

        String name = editName.getText().toString();
        String description = editDescription.getText().toString();
        double cost = Double.parseDouble(editCost.getText().toString());
        int maxAttendees = Integer.parseInt(editMaxAttendees.getText().toString());
        String tags = editTag.getText().toString();

        String[] tag = tags.split(",");
        for(int j = 0; j < tag.length; j++){
            Log.d("Tags", tag[j]);

        }

        ActivitiListModel temp = new ActivitiListModel();
        temp.cost = cost;
        temp.dateStart = new DateTime(dateDay, dateMonth, dateYear, dateHour, dateMinute);
        temp.description = description;
        temp.maxAttendees = maxAttendees;
        temp.title = name;

        activitiList.add(temp);
        adapter.notifyDataSetChanged();
        JSONObject objtemp = new JSONObject();
        try{
            objtemp.put("name",temp.title);
            objtemp.put("cost", temp.cost);
            objtemp.put("description", temp.description);
            objtemp.put("max_attendees", temp.maxAttendees);
            objtemp.put("start_date", temp.dateStart.getRequestFormat());
            objtemp.put("end_date", temp.dateEnd.getRequestFormat());
            objtemp.put("latitude", "28.097");
            objtemp.put("longitude", "56.78");
            JSONArray ty = new JSONArray();
            for(int i = 0; i < tag.length; i++){
                ty.put(tag[i]);
                Log.d("objtemp", tag[i].toString());
            }
            objtemp.put("tags",ty);
        }catch (Exception e){
            Log.d("iwjij", "wodk");
        }
        Log.d("objtemp", objtemp.toString());
        editName.setText("");
        editDescription.setText("");
        editCost.setText("");
        editTag.setText("");
        editMaxAttendees.setText("");
        String path = getResources().getString(R.string.url) + getResources().getString(R.string.activitiPath);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("token", getToken());
        RequestManager.sendRequest(Request.Method.POST, path, headerMap, objtemp, new RequestCallBack() {
            @Override
            public void callback(JSONObject res) {

            }
        } );
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
        closeSlideMenu(view);
        navigate(createActivitiFragment);
    }

    public void createActiviti(JSONObject info){

    }

    public void findActiviti(View view) {
        closeSlideMenu(view);
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
            String first_name = userinfo.getString("first_name");
            String last_name = userinfo.getString("last_name");
            String gender = userinfo.getString("gender");
            String bio = userinfo.getString("bio");
            String dob = userinfo.getString("dob");

            TextView nameText = (TextView)findViewById(R.id.nameText);
            TextView genderText = (TextView)findViewById(R.id.genderText);
            TextView bioText = (TextView)findViewById(R.id.bioText);
            TextView dobText = (TextView)findViewById(R.id.dobText);

            nameText.setText(first_name);
            genderText.setText(gender);
            bioText.setText(bio);
            dobText.setText(dob);

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
        Calendar mDate = Calendar.getInstance();
        int mYear = mDate.get(Calendar.YEAR);
        int mMonth = mDate.get(Calendar.MONTH);
        int mDay = mDate.get(Calendar.DAY_OF_MONTH);
        dateDay = mDay;dateMonth = mMonth-1;dateYear = mYear;

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
    public void setDate2(View view) {
        Calendar mDate = Calendar.getInstance();
        int mYear = mDate.get(Calendar.YEAR);
        int mMonth = mDate.get(Calendar.MONTH);
        int mDay = mDate.get(Calendar.DAY_OF_MONTH);
        dateDay2 = mDay;dateMonth2 = mMonth-1;dateYear2 = mYear;

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
        dateHour = mHour; dateMinute = mMinute;

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
