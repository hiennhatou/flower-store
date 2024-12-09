package edu.ou.flowerstore.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.ui.cart.CartActivity;

public class ProfileMenuFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_menu, container, false);
        Context context = getContext();
        LinearLayout profileBtn = view.findViewById(R.id.profile_btn);
        LinearLayout cartBtn = view.findViewById(R.id.cart_btn);
        if (context != null) {
            profileBtn.setOnClickListener(v -> {
                context.startActivity(new Intent(context, ModifyProfileActivity.class));
            });

            cartBtn.setOnClickListener(v -> {
                context.startActivity(new Intent(context, CartActivity.class));
            });
        }
        return view;
    }
}
