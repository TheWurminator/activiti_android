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
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.Calendar;

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
    private String token = "YPTVjthbt365PsNJPzmBAzVCAqQOptTM3bHIUz6C47ccmuomo19sJ6p3ukYQ8uvUwRUMab9CNlWPpA7ALOtnj7rCWxHdPBCaRqhwUPZuAzSaRsZoopQekYlAn3RkUAqFwrsxmT3ZqTY8JVCY0OPjhKIRRmr2QryMI0GDvLA2JO0Fix7C2TQm7hMNys6Gv8lHWZNNyTTXtIbEPdyjYKd7RnxH36FV0auasAWjHgHuBbyOLB1H2Nbdw4Ku5JlOJQk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_menu);
        ProfileViewFragment profileViewFragment = new ProfileViewFragment();
        navigate(profileViewFragment);

    }

    @Override
    public void onBackPressed(){
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 1){
            super.onBackPressed();
            //additional code
        }

        else{
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

    public void viewOtherProfile(View view) {

        navigate(friendProfileViewFragment);
    } 

    public void viewProfile(View view) {

        navigate(profileViewFragment);
        sendRequest(Request.Method.GET, "https://activiti.serverbeer.com:8081/user");
    }

    public void editProfile(View view) {
        navigate(editProfileFragment);
    }

    public void viewActiviti(View view) {
        navigate(activitiViewFragment);
    }

    public void saveProfile(View view) {
        viewProfile(view);
        //Code for sending updated data to server
    }

    public void seeAllActivitis(View view) {
        navigate(allActivitiFragment);
    }

    public void submitNewActiviti(View view) {
        //Code for sending new activiti data to server
        viewProfile(view);
    }

    public void createActiviti(View view) {
        navigate(createActivitiFragment);
    }

    public void findActiviti(View view) {
        navigate(findActivitiFragment);
    }

    public void badgeView(View view){
        navigate(badgeViewFragment);
    }

    public void viewAllActivities(View view) {
        navigate(allActivitiFragment);
    }

    public void leaveBadge(View view){
        navigate(leaveBadgeFragment);
    }

    public void chatFragment(View view) {
        navigate(chatFragment);
    }

    public boolean isValidDate(){
        Calendar c = Calendar.getInstance();
        int month = 0, day = 0, year = 0;

        //check month
        if(month < 1 || month > 12){
            return false;
        }

        //check leap year days
        if(month==2){
            if(year % 4 == 0){
                if(day > 29)
                    return false;
            }

            else
                if(day > 28)
                    return false;
        }

        else if(month == 4 || month == 6 || month == 9 || month == 11){
            if(day > 30)
                return false;
        }

        else{
            if(day > 31)
                return false;
        }

        if(day < 1)
            return false;

        if(year < c.YEAR || year > (c.YEAR + 10)){
            return false;
        }

        return true;
    }

    public void sendRequest(int requestMethod, String url){
        VolleySingleton volleySing = VolleySingleton.getInstance();
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
        JSONObject jsonObject = new JSONObject();
        CustomJSONRequest testRequest = new CustomJSONRequest(requestMethod,url,jsonObject, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(testRequest);
    }

    public void displayProfile(JSONObject userinfo) {

    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(MyApplication.getAppContext(), this, hour, minute, DateFormat.is24HourFormat(MyApplication.getAppContext()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

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
}
