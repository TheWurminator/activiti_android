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
import org.json.JSONTokener;

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
    //Fragment objects used to handle navigation
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

    //List view for displaying activities
    private ListView listView;
    private ArrayList<ActivitiListModel> activitiList = new ArrayList<>();
    private SlidingMenuFragment slidingMenuFragment = new SlidingMenuFragment();
    SlidingUpPanelLayout slideLayout;
    ActivitiAdapter adapter;

    //variables to store date and time information
    private int dateHour, dateMinute, dateMonth, dateYear, dateDay;
    private int dateMonth2, dateYear2, dateDay2;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_menu);

        //Sets up the menu bar
        adapter = new ActivitiAdapter(this, activitiList);
        listView = (ListView)findViewById(R.id.list_menu);
        listView.setAdapter(adapter);

        //Sets up the sliding layout
        slideLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slideLayout.setTouchEnabled(false);

        //Allows volley to send requests to self signed certificates
        trustEveryone();

        //On create, navigate to view profile
        ProfileViewFragment profileViewFragment = new ProfileViewFragment();
        navigate(profileViewFragment);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        viewProfile();
    }

    //handles opening the sliding menu
    public void openSlideMenu(View view) {
        SlidingUpPanelLayout.PanelState temp = slideLayout.getPanelState();
        if(temp.equals(SlidingUpPanelLayout.PanelState.COLLAPSED))slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        else slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    //handles closing the sliding menu
    public void closeSlideMenu(View view) {
        slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    //Overriden function of onbackpressed . This makes you return to one screen prior
    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    //Handles navigation by inflating whatever fragment it is passed
    public void navigate(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    public void makeSearch(View view) {
        //Sets up the GUI components for the activiti search function
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

        //variable to store tags. tags are comma delimited
        String[] mTags = tags.getText().toString().split(",");
        String mName = name.getText().toString(), mDescription = description.getText().toString();

        //placeholder body jsonobject
        JSONObject body = new JSONObject();

        //Everything in this try catch block is used to handle input validation
        try {
            try {
                int mStartDay = dateDay, mStartMonth = dateMonth, mStartYear = dateYear, mEndDay = dateDay2,
                        mEndMonth = dateMonth2, mEndYear = dateYear2, mMinAttend = Integer.parseInt(minAttend.getText().toString()),
                        mMaxAttend = Integer.parseInt(maxAttend.getText().toString()), mMinCost = Integer.parseInt(minCost.getText().toString()),
                        mMaxCost = Integer.parseInt(maxCost.getText().toString());
                double mMaxDist = Double.parseDouble(maxDist.getText().toString()), mLongitude = Double.parseDouble(longitude.getText().toString()),
                        mLatitutde = Double.parseDouble(latitude.getText().toString());
            }
            catch(Exception ex) {

            }

            if(mName.length() > 0)
                body.put("name", mName);

            if(mDescription.equals("") == false)
                body.put("description", mDescription);

            JSONArray ar = new JSONArray();

            if(mTags[0].equals("") == false) {
                for (int i = 0; i < mTags.length; i++) {
                    ar.put(mTags[i]);
                }
                 body.put("tags", ar);
            }


        } catch (Exception e) {
        }
        //End input validation

        //create a json array
        JSONArray ar2 = new JSONArray();
        ar2.put(body);

        openSlideMenu(view);

        //reset all the text fields
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

        searchActivities(ar2);
    }

    private void searchActivities(JSONArray body) {
        //sets up for sending the token to the server in the header
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("token", getToken());

        //assemble the full path of the server
        String path = getResources().getString(R.string.url) + getResources().getString(R.string.activitiSearchPath);

        //Send request, using post, path, the header, body, and a callback function
        RequestManager.sendArrayRequest(Request.Method.POST, path, headerMap, body, new RequestArrayCallBack() {
            public void callback(JSONArray res) {

                activitiList.clear();
                adapter.notifyDataSetChanged();
                if(res!=null && res.length()>0){


                    for (int i = 0; i < res.length(); i++) {
                        try {
                            Log.d("searches", res.get(i).toString());
                            ActivitiListModel tmp = new ActivitiListModel();
                            JSONObject obj = (JSONObject) new JSONTokener(res.get(i).toString()).nextValue();
                            tmp.title = obj.getString("name");
                            tmp.description = obj.getString("description");

                            activitiList.add(tmp);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });

    }

    //Returns the token from local storage
    public String getToken() {
        SharedPreferences prefs = getSharedPreferences(MyApplication.getAppContext().getString(R.string.token_store), 0);
        String token = prefs.getString("token", null);
        return token;
    }

    //view profile button click listener
    public void viewProfile(View view) {
        //Navigate to and display the user's profile
        closeSlideMenu(view);
        navigate(profileViewFragment);
        viewProfile();
    }

    private void viewProfile() {
        //prepares the header with a get request
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("token", getToken());

        //prepares the body of the request
        JSONObject body = new JSONObject();

        //assembles the full path
        String path = getResources().getString(R.string.url) + getResources().getString(R.string.userPath);

        //sends the request
        RequestManager.sendRequest(Request.Method.GET, path, headerMap, body, new RequestCallBack() {
            public void callback(JSONObject res) {
                //call display profile on successful response
                displayProfile(res);
            }
        });
    }

    public void viewActiviti(View view) {
        navigate(activitiViewFragment);
    }

    //for viewing the details of 1 specific activiti. Not fully implemented
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

    //Save profile submit changes. Not fully implemented
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
        //Sets up the gui components as objects
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editDescription = (EditText) findViewById(R.id.editDescription);
        EditText editCost = (EditText) findViewById(R.id.moneyTextBox);
        EditText editMaxAttendees = (EditText) findViewById(R.id.attendeesText);
        Button editDate = (Button) findViewById(R.id.editDateButton);
        Button editTime = (Button) findViewById(R.id.editTimeButton);
        Button editLocation = (Button) findViewById(R.id.locationPick);
        EditText editTag = (EditText) findViewById(R.id.editTag0Activiti);

        //Assign values from respective gui components
        String name = editName.getText().toString();
        String description = editDescription.getText().toString();
        double cost = Double.parseDouble(editCost.getText().toString());
        int maxAttendees = Integer.parseInt(editMaxAttendees.getText().toString());
        String tags = editTag.getText().toString();

        //Tags are comma delimited
        String[] tag = tags.split(",");
        for(int j = 0; j < tag.length; j++){
            Log.d("Tags", tag[j]);

        }

        //Prepare an activitilistmodel
        ActivitiListModel temp = new ActivitiListModel();
        temp.cost = cost;
        temp.dateStart = new DateTime(dateDay, dateMonth, dateYear, dateHour, dateMinute);
        temp.description = description;
        temp.maxAttendees = maxAttendees;
        temp.title = name;

        activitiList.add(temp);
        adapter.notifyDataSetChanged();
        //assemble a temporary jsonobject
        JSONObject objtemp = new JSONObject();
        try{
            objtemp.put("name",temp.title);
            objtemp.put("cost", temp.cost);
            objtemp.put("description", temp.description);
            objtemp.put("max_attendees", temp.maxAttendees);
            objtemp.put("start_date", temp.dateStart.getRequestFormat());
            //objtemp.put("end_date", temp.dateEnd.getRequestFormat());
            objtemp.put("latitude", "28.097");
            objtemp.put("longitude", "56.78");
            JSONArray ty = new JSONArray();

            for(int i = 0; i < tag.length; i++)
                ty.put(tag[i]);

            objtemp.put("tags", ty);

        }catch (Exception e){
            Log.d("iwjij", "wodk");
        }
        Log.d("objtemp", objtemp.toString());
        //Resets all the gui components text
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

    //The following eleven functoins are all solesly used in navigation and inflating fraggments
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

    //Function used to trust self signed certificates
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

    //Takes the information from the JSONObject and places them in text views
    public void displayProfile(JSONObject userinfo) {
        //Take the information and display to the userry{
        try {
            String first_name = userinfo.getString("first_name");
            String last_name = userinfo.getString("last_name");
            String gender = userinfo.getString("gender");
            String bio = userinfo.getString("bio");
            String dob = userinfo.getString("dob");

            //TextViews
            TextView nameText = (TextView)findViewById(R.id.nameText);
            TextView genderText = (TextView)findViewById(R.id.genderText);
            TextView bioText = (TextView)findViewById(R.id.bioText);
            TextView dobText = (TextView)findViewById(R.id.dobText);

            //Setting all the label's text
            nameText.setText(first_name);
            genderText.setText(gender);
            bioText.setText(bio);
            dobText.setText(dob);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Displays the activity information. Not fully implemented
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

    //Date picker
    public void setDate(View view) {
        Calendar mDate = Calendar.getInstance();
        //variables to hold the year month and day
        int mYear = mDate.get(Calendar.YEAR);
        int mMonth = mDate.get(Calendar.MONTH);
        int mDay = mDate.get(Calendar.DAY_OF_MONTH);
        dateDay = mDay;dateMonth = mMonth-1;dateYear = mYear;

        final Button datePick = (Button) findViewById(R.id.editDateButton);
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
               //When a date is set, set the text of that button to the date picked
                datePick.setText(Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }
    public void setDate3(View view) {
        Calendar mDate = Calendar.getInstance();
        //variables to hold the year month and day
        int mYear = mDate.get(Calendar.YEAR);
        int mMonth = mDate.get(Calendar.MONTH);
        int mDay = mDate.get(Calendar.DAY_OF_MONTH);
        dateDay = mDay;dateMonth = mMonth-1;dateYear = mYear;

        final Button datePick = (Button)findViewById(R.id.search_startdateButt);
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                //When a date is set, set the text of that button to the date picked
                datePick.setText(Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }
    public void setDate2(View view) {
        Calendar mDate = Calendar.getInstance();
        //variables to hold the year month and day
        int mYear = mDate.get(Calendar.YEAR);
        int mMonth = mDate.get(Calendar.MONTH);
        int mDay = mDate.get(Calendar.DAY_OF_MONTH);
        dateDay2 = mDay;dateMonth2 = mMonth-1;dateYear2 = mYear;

        final Button datePick = (Button) findViewById(R.id.search_enddateButt);
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                //When a date is set, set the text of that button to the date picked
                datePick.setText(Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    public void setTime(View view) {
        final Button timePick = (Button) findViewById(R.id.editTimeButton);
        Calendar mTime = Calendar.getInstance();
        //variables to hold the year minute and hour
        int mHour = mTime.get(Calendar.HOUR_OF_DAY);
        int mMinute = mTime.get(Calendar.MINUTE);
        dateHour = mHour; dateMinute = mMinute;

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //When a time is set, set the text of that button to the time picked
                timePick.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
        }, mHour, mMinute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    //used for choosing location, not fully implemented
    public void chooseLocation(View view) {
        Fragment mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mapContainer, mMapFragment);
        fragmentTransaction.commit();
    }

    //Used for maps, not implemented
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

    //Used for maps, not implemented
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
