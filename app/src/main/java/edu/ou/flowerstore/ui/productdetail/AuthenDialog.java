package edu.ou.flowerstore.ui.productdetail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.ui.authen.Login;
import edu.ou.flowerstore.ui.authen.SignUp;

public class AuthenDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.dialog_require_auth, null);
        view.findViewById(R.id.sign_up_btn).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SignUp.class));
        });
        view.findViewById(R.id.login_btn).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), Login.class));
        });
        return new AlertDialog.Builder(getContext()).setView(view).create();
    }
}
