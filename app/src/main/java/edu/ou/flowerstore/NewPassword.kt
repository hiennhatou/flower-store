package edu.ou.flowerstore

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NewPassword : AppCompatActivity() {
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)

        // Thiết lập cho giao diện edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Xử lý bật/tắt hiển thị mật khẩu mới
        val newPasswordEditText = findViewById<EditText>(R.id.etNewPassword)
        val showNewPasswordIcon = findViewById<ImageView>(R.id.ivShowNewPassword)

        showNewPasswordIcon.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                // Hiển thị mật khẩu
                newPasswordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showNewPasswordIcon.setImageResource(R.drawable.ic_eye_open) // Đảm bảo có ic_eye_open trong drawable
            } else {
                // Ẩn mật khẩu
                newPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showNewPasswordIcon.setImageResource(R.drawable.ic_eye_closed) // Đảm bảo có ic_eye_closed trong drawable
            }
            // Đưa con trỏ về cuối dòng
            newPasswordEditText.setSelection(newPasswordEditText.text.length)
        }

        // Xử lý bật/tắt hiển thị xác nhận mật khẩu
        val confirmPasswordEditText = findViewById<EditText>(R.id.etConfirmPassword)
        val showConfirmPasswordIcon = findViewById<ImageView>(R.id.ivShowConfirmPassword)

        showConfirmPasswordIcon.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            if (isConfirmPasswordVisible) {
                // Hiển thị mật khẩu
                confirmPasswordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showConfirmPasswordIcon.setImageResource(R.drawable.ic_eye_open) // Đảm bảo có ic_eye_open trong drawable
            } else {
                // Ẩn mật khẩu
                confirmPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showConfirmPasswordIcon.setImageResource(R.drawable.ic_eye_closed) // Đảm bảo có ic_eye_closed trong drawable
            }
            // Đưa con trỏ về cuối dòng
            confirmPasswordEditText.setSelection(confirmPasswordEditText.text.length)
        }
    }
}
