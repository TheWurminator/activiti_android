package rendezvous.activiti;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by James on 4/8/2016.
 */
//A subclass of fragment, FindActivitiFragment is used when the user wants to enter a query to search for an activiti
public class FindActivitiFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_find_activiti, container, false);
        return view;
    }
}
