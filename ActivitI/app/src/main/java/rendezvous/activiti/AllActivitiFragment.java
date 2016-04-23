package rendezvous.activiti;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by James on 4/8/2016.
 */
public class AllActivitiFragment extends Fragment {
    //A subclass of Fragment, AllActivitiFragment is used when the user wants to see all of their activities
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_see_all_activities, container, false);
        return view;
    }
}
