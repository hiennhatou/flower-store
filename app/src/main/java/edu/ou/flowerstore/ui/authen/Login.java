package edu.ou.flowerstore.ui.authen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.ui.main.MainActivity;
import edu.ou.flowerstore.utils.firebase.AppFirebase;

public class Login extends AppCompatActivity {
    private final FlowerStoreApplication application = FlowerStoreApplication.getInstance();
    private final AppFirebase firebase = new AppFirebase();
    private Button btnDangnhap, backBtn;
    private TextInputEditText etEmail, etPassword;
    private FirebaseAuth mAuth;
    private TextView tvSignUp;

    private void AnhXa() {
        btnDangnhap = findViewById(R.id.btnSignIn);
        etEmail = findViewById(R.id.edtEmail);
        etPassword = findViewById(R.id.edtPassword);
        backBtn = findViewById(R.id.iv_back);
        tvSignUp = findViewById(R.id.tvSignUp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AnhXa();
        mAuth = FirebaseAuth.getInstance();
        btnDangnhap.setOnClickListener(v -> {
            Dangnhap();
        });
        backBtn.setOnClickListener(v -> {
            finish();
        });
        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, SignUp.class));
            finish();
        });
    }


    private void Dangnhap() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(Login.this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Exception exception = task.getException();
                        if (exception instanceof FirebaseAuthInvalidCredentialsException)
                            Toast.makeText(this, "Thông tin đăng nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}