package edu.ou.flowerstore.ui.profile;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import edu.ou.flowerstore.R;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private LinearLayout logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.profile_image);
        logOutButton = findViewById(R.id.log_out_button);


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Edit Profile clicked", Toast.LENGTH_SHORT).show();
                // Thêm logic để chỉnh sửa profile tại đây
            }
        });

        // Xử lý sự kiện khi nhấn vào nút Log Out
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });
    }

    private void showLogoutDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_logout_confirmation);
        dialog.setCancelable(true);


        TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
        TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
        Button cancelButton = dialog.findViewById(R.id.cancel_button);
        Button logoutButton = dialog.findViewById(R.id.logout_button);

        // Xử lý sự kiện khi nhấn Cancel
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Xử lý sự kiện khi nhấn Yes, Logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(ProfileActivity.this, "Logged out", Toast.LENGTH_SHORT).show();

            }
        });

        dialog.show();
    }
}
