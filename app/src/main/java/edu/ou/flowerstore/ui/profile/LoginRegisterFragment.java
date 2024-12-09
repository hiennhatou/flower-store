package edu.ou.flowerstore.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.ou.flowerstore.R;

public class LoginRegisterFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_register, container, false);
        Button signInBtn = view.findViewById(R.id.sign_in_btn);
        Button register = view.findViewById(R.id.register_btn);
        signInBtn.setOnClickListener(v -> {

        });

        register.setOnClickListener(v -> {

        });
        return view;
    }
}