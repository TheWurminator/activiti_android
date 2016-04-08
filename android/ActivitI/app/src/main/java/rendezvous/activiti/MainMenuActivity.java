package rendezvous.activiti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainMenuActivity extends AppCompatActivity {

    private int numTags = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_menu);
        ProfileViewFragment profileViewFragment = new ProfileViewFragment();
        navigate(profileViewFragment);

    }

    public void navigate(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public void searchActivities(View view) {
        ListResultsFragment listResultsFragment = new ListResultsFragment();
        navigate(listResultsFragment);
        //Code to send search query to server
    }


    public void viewProfile(View view) {
        ProfileViewFragment profileViewFragment = new ProfileViewFragment();
        navigate(profileViewFragment);
    }

    public void editProfile(View view) {
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        navigate(editProfileFragment);
    }

    public void viewActiviti(View view) {
        ActivitiViewFragment activitiViewFragment = new ActivitiViewFragment();
        navigate(activitiViewFragment);
    }

    public void saveProfile(View view) {
        viewProfile(view);
        //Code for sending updated data to server
    }

    public void seeAllActivitis(View view) {
        AllActivitiFragment allActivitiFragment = new AllActivitiFragment();
        navigate(allActivitiFragment);
    }

    public void submitNewActiviti(View view) {
        //Code for sending new activiti data to server
        viewProfile(view);
    }

    public void createActiviti(View view) {
        CreateActivitiFragment createActivitiFragment = new CreateActivitiFragment();
        navigate(createActivitiFragment);
    }

    public void findActiviti(View view) {
        FindActivitiFragment findActivitiFragment = new FindActivitiFragment();
        navigate(findActivitiFragment);
    }


        /*
        public boolean checkText() {
          /*int numTags = getNumTags();
            int id;
            EditText currEditTag;

            for(int i = 0; i < numTags; i++) {
                id = getResources().getIdentifier("editTag"+i, "id", MyApplication.getAppContext().getPackageName());
                currEditTag = (EditText) findViewById(id);
                if(currEditTag.getText().toString().trim().length() == 0)
                    return false;
            }

            return true;

        }
        */
    public void incNumTags() {
        numTags++;
    }


    public void viewAllActivities(View view) {
        AllActivitiFragment allActivitiFragment = new AllActivitiFragment();
        navigate(allActivitiFragment);
    }

}
