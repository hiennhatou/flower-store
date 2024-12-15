package edu.ou.flowerstore.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.ui.admin.AdminActivity;
import edu.ou.flowerstore.ui.cart.CartActivity;
import edu.ou.flowerstore.ui.orders.CustomerOrdersActivity;
import edu.ou.flowerstore.utils.firebase.AppFirebase;

public class ProfileMenuFragment extends Fragment {
    private final FlowerStoreApplication application = FlowerStoreApplication.getInstance();
    private final AppFirebase appFirebase = application.getAppFirebase();
    private View view;
    private Context context;
    private AlertDialog alertDialog;

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
        if (context != null) {
            initEvent();
            initData();
        }
        return view;
    }

    private void initEvent() {
        LinearLayout profileBtn = view.findViewById(R.id.profile_btn);
        LinearLayout cartBtn = view.findViewById(R.id.cart_btn);
        LinearLayout logoutBtn = view.findViewById(R.id.log_out_button);
        LinearLayout orderBtn = view.findViewById(R.id.order_btn);
        Button adminBtn = view.findViewById(R.id.admin_btn);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_logout_confirmation, null);
        dialogView.findViewById(R.id.logout_button).setOnClickListener(l -> {
            appFirebase.getFirebaseAuth().signOut();
            alertDialog.cancel();
            application.getCurrentUserLiveData().setValue(appFirebase.getFirebaseAuth().getCurrentUser());
        });
        dialogView.findViewById(R.id.cancel_button).setOnClickListener(l -> {
            alertDialog.cancel();
        });
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();

        profileBtn.setOnClickListener(v -> {
            context.startActivity(new Intent(context, ModifyProfileActivity.class));
        });
        orderBtn.setOnClickListener(v -> {
            context.startActivity(new Intent(context, CustomerOrdersActivity.class));
        });
        cartBtn.setOnClickListener(v -> {
            context.startActivity(new Intent(context, CartActivity.class));
        });
        logoutBtn.setOnClickListener(v -> {
            alertDialog.show();
        });
        adminBtn.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AdminActivity.class));
        });
        if (appFirebase.getFirebaseAuth().getCurrentUser() != null)
            appFirebase.getUsersCollection().document(appFirebase.getFirebaseAuth().getCurrentUser().getUid()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc.getString("role") != null && doc.getString("role").equals("admin"))
                                adminBtn.setVisibility(View.VISIBLE);
                        }
                    });
    }

    private void initData() {
        TextView nameTv = view.findViewById(R.id.profile_name);
        ImageView avatarImgView = view.findViewById(R.id.profile_image);
        if (appFirebase.getFirebaseAuth().getCurrentUser() == null) return;
        String uid = appFirebase.getFirebaseAuth().getCurrentUser().getUid();
        appFirebase.getUsersCollection().document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot result = task.getResult();
                nameTv.setText(result.getString("name"));
                Picasso.get().load(result.getString("avatar")).into(avatarImgView);
            }
        });
    }
}
