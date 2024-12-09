package edu.ou.flowerstore.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.ui.authen.Login;
import edu.ou.flowerstore.ui.authen.SignUp;

public class LoginRegisterFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_register, container, false);
        Context context = getContext();
        Button signInBtn = view.findViewById(R.id.sign_in_btn);
        Button register = view.findViewById(R.id.register_btn);
        if (context != null) {
            signInBtn.setOnClickListener(v -> {
                context.startActivity(new Intent(context, Login.class));
            });

            register.setOnClickListener(v -> {
                context.startActivity(new Intent(context, SignUp.class));
            });
        }
        return view;
    }
}