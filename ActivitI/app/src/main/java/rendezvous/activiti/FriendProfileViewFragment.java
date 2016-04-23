package rendezvous.activiti;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
//A subclass of fragment, FriendProfileViewFragment was intended to run whenever the user viewed another user's profile.
public class FriendProfileViewFragment extends Fragment {
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_friend_profile_view, container, false);
        return view;
    }
}
