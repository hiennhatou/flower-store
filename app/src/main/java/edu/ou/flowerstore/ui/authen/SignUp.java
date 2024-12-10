package edu.ou.flowerstore.ui.authen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.utils.firebase.AppFirebase;

public class SignUp extends AppCompatActivity {
    Button btnSignup, backBtn;
    EditText edtEmail, edtPassword, etName;
    private AppFirebase appFirebase = new AppFirebase();
    private final FlowerStoreApplication application = FlowerStoreApplication.getInstance();
    private MutableLiveData<Boolean> isProgressing = new MutableLiveData<>(false);
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();

        loadingDialog = new AlertDialog.Builder(this).setView(getLayoutInflater().inflate(R.layout.dialog_loading, null)).create();

        btnSignup.setOnClickListener(v -> {
            onSignUp();
        });

        isProgressing.observe(this, v -> {
            if (v) {
                loadingDialog.show();
            } else {
                loadingDialog.cancel();
            }
        });

        TextView tvSignInLink = findViewById(R.id.tvSignInLink);
        tvSignInLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
            finish();
        });
        backBtn.setOnClickListener(v -> {
            finish();
        });
    }

    private void onSignUp() {
        isProgressing.setValue(true);
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString();
        String name = etName.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            isProgressing.setValue(false);
            Toast.makeText(SignUp.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            isProgressing.setValue(false);
            Toast.makeText(SignUp.this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (name.isEmpty()) {
            isProgressing.setValue(false);
            Toast.makeText(SignUp.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        appFirebase.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && task.getResult().getUser() != null) {
                        FirebaseUser user = task.getResult().getUser();
                        application.getCurrentUserLiveData().setValue(user);
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("name", name);
                        userData.put("created_date", FieldValue.serverTimestamp());
                        userData.put("updated_date", FieldValue.serverTimestamp());
                        userData.put("avatar", "https://res.cloudinary.com/flower-store/image/upload/q_auto:low,w_100,h_100,f_webp/v1733504138/mrxglys3bp4jjxyliapm.png");
                        userData.put("birthday", null);
                        userData.put("phoneNumber", null);
                        userData.put("gender", null);
                        userData.put("status", "active");
                        userData.put("role", "customer");
                        appFirebase.getUsersCollection().document(user.getUid()).set(userData).addOnCompleteListener(this, firestoreTask -> {
                            if (firestoreTask.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SignUp.this, "Lỗi!!!", Toast.LENGTH_SHORT).show();
                            }
                            isProgressing.setValue(false);
                            finish();
                        });
                    } else {
                        isProgressing.setValue(false);
                        Toast.makeText(SignUp.this, "Lỗi!!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void AnhXa() {
        btnSignup = findViewById(R.id.btnSignUp);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        etName = findViewById(R.id.edtName);
        backBtn = findViewById(R.id.iv_back);
    }
}