package edu.ou.flowerstore.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.ui.profile.LoginRegisterFragment;
import edu.ou.flowerstore.ui.profile.ProfileMenuFragment;

public class ProfileFragment extends Fragment {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_profile, container, false);
        FragmentActivity activity = getActivity();
        if (activity == null) return view;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if (firebaseAuth.getCurrentUser() != null) {
            fragmentManager.beginTransaction().add(R.id.main_profile, LoginRegisterFragment.class, null).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.main_profile, ProfileMenuFragment.class, null).commit();
        }
        return view;
    }
}