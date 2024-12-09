package edu.ou.flowerstore.ui.profile;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import edu.ou.flowerstore.R;

public class PasswordManagerActivity extends AppCompatActivity {

    private boolean isCurrentPasswordVisible = false;
    private boolean isNewPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_manager);

        // Nút quay lại
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // Chuyển đổi hiển thị mật khẩu cho mật khẩu hiện tại
        setupPasswordToggle(
                R.id.current_password,
                R.id.toggle_current_password,
                () -> {
                    isCurrentPasswordVisible = !isCurrentPasswordVisible;
                    return isCurrentPasswordVisible;
                });


        setupPasswordToggle(
                R.id.new_password,
                R.id.toggle_new_password,
                () -> {
                    isNewPasswordVisible = !isNewPasswordVisible;
                    return isNewPasswordVisible;
                });


        setupPasswordToggle(
                R.id.confirm_new_password,
                R.id.toggle_confirm_password,
                () -> {
                    isConfirmPasswordVisible = !isConfirmPasswordVisible;
                    return isConfirmPasswordVisible;
                });


        Button changePasswordButton = findViewById(R.id.change_password_button);
        changePasswordButton.setOnClickListener(v -> {
            // Lấy giá trị từ các ô nhập mật khẩu
            EditText currentPassword = findViewById(R.id.current_password);
            EditText newPassword = findViewById(R.id.new_password);
            EditText confirmPassword = findViewById(R.id.confirm_new_password);

            String currentPass = currentPassword.getText().toString();
            String newPass = newPassword.getText().toString();
            String confirmPass = confirmPassword.getText().toString();


            if (newPass.equals(confirmPass)) {
                // Xử lý thay đổi mật khẩu
                // TODO: Gửi yêu cầu thay đổi mật khẩu hoặc thực hiện xử lý cần thiết
            } else {
                // TODO: Hiển thị thông báo lỗi xác nhận mật khẩu không khớp
            }
        });
    }

    // Hàm thiết lập chuyển đổi hiển thị mật khẩu
    private void setupPasswordToggle(int editTextId, int toggleButtonId, VisibilityProvider visibilityProvider) {
        EditText passwordField = findViewById(editTextId);
        ImageView toggleButton = findViewById(toggleButtonId);

        toggleButton.setOnClickListener(v -> {
            if (visibilityProvider.isPasswordVisible()) {
                passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                toggleButton.setImageResource(R.drawable.ic_visibility_on);
            } else {
                passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                toggleButton.setImageResource(R.drawable.ic_visibility_off);
            }
            passwordField.setSelection(passwordField.length());
        });
    }

    // Interface để quản lý trạng thái hiển thị của mật khẩu
    interface VisibilityProvider {
        boolean isPasswordVisible();
    }
}
