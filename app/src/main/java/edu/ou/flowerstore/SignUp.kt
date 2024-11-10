package edu.ou.flowerstore

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUp : AppCompatActivity() {
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Thiết lập cho giao diện edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Xử lý bật/tắt hiển thị mật khẩu
        val passwordEditText = findViewById<EditText>(R.id.etPassword)
        val showPasswordIcon = findViewById<ImageView>(R.id.ivShowPassword)

        showPasswordIcon.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                // Hiển thị mật khẩu
                passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showPasswordIcon.setImageResource(R.drawable.ic_eye_closed)
            } else {
                // Ẩn mật khẩu
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordIcon.setImageResource(R.drawable.ic_eye_open)
            }
            // Đưa con trỏ về cuối dòng
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        // Chuyển sang màn hình đăng nhập khi nhấp vào "Đăng nhập"
        val signInLink = findViewById<TextView>(R.id.tvSignInLink)
        signInLink.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}
