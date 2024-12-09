package edu.ou.flowerstore.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Flow;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.ui.profile.LoginRegisterFragment;
import edu.ou.flowerstore.ui.profile.ProfileMenuFragment;
import edu.ou.flowerstore.utils.firebase.AppFirebase;

public class ProfileFragment extends Fragment {
    FlowerStoreApplication application = FlowerStoreApplication.getInstance();
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment_profile, container, false);
        application.getCurrentUserLiveData().observe(getViewLifecycleOwner(), this::renderFragment);
        return view;
    }

    private void renderFragment(FirebaseUser firebaseUser) {
        FragmentManager fragmentManager = getChildFragmentManager();
        if (firebaseUser == null) {
            fragmentManager.beginTransaction().replace(R.id.main_profile, LoginRegisterFragment.class, null).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.main_profile, ProfileMenuFragment.class, null).commit();
        }
    }
}