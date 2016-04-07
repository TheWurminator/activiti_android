package rendezvous.activiti;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_menu);

    }

    public void viewProfile(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ProfileViewFragment profileViewFragment = new ProfileViewFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, profileViewFragment);
        fragmentTransaction.commit();
    }

    public void editProfile(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, editProfileFragment);
        fragmentTransaction.commit();
    }

    public void viewActiviti(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ActivitiViewFragment activitiViewFragment = new ActivitiViewFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, activitiViewFragment);
        fragmentTransaction.commit();
    }
}
