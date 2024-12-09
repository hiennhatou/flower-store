package edu.ou.flowerstore.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.ui.cart.CartActivity;

public class ProfileMenuFragment extends Fragment {
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FlowerStoreApplication application = FlowerStoreApplication.getInstance();
    private View view;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_menu, container, false);
        LinearLayout profileBtn = view.findViewById(R.id.profile_btn);
        LinearLayout cartBtn = view.findViewById(R.id.cart_btn);
        LinearLayout logoutBtn = view.findViewById(R.id.log_out_button);
        if (context != null) {
            profileBtn.setOnClickListener(v -> {
                context.startActivity(new Intent(context, ModifyProfileActivity.class));
            });

            cartBtn.setOnClickListener(v -> {
                context.startActivity(new Intent(context, CartActivity.class));
            });
            logoutBtn.setOnClickListener(v -> {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_logout_confirmation, null);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();

                dialogView.findViewById(R.id.logout_button).setOnClickListener(l -> {
                    firebaseAuth.signOut();
                    alertDialog.cancel();
                    application.getCurrentUserLiveData().setValue(firebaseAuth.getCurrentUser());
                });
                dialogView.findViewById(R.id.cancel_button).setOnClickListener(l -> {
                    alertDialog.cancel();
                });
                alertDialog.show();
            });
        }
        return view;
    }
}
