package edu.ou.flowerstore.ui.payment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.ActivityZaloPayBinding;
import edu.ou.flowerstore.utils.zalopay.ZaloPayUtil;
import edu.ou.flowerstore.utils.zalopay.ResponseCreateZalopayOrderBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ZaloPayPaymentActivity extends AppCompatActivity {
    ActivityZaloPayBinding binding;
    FlowerStoreApplication application;
    FirebaseAuth firebaseAuth;
    CollectionReference orderCollection;
    private DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssSSS", new Locale("vi", "vn"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityZaloPayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        application = FlowerStoreApplication.getInstance();
        firebaseAuth = application.getAppFirebase().getFirebaseAuth();
        orderCollection = application.getAppFirebase().getOrdersCollection();

        binding.paymentBtn.setOnClickListener(v -> {
            onPayment();
        });
    }

    private Callback<ResponseCreateZalopayOrderBody> createPaymentCallback(String orderId, String transId) {
        class CreatePaymentCallback implements Callback<ResponseCreateZalopayOrderBody> {
            @Override
            public void onResponse(Call<ResponseCreateZalopayOrderBody> call, Response<ResponseCreateZalopayOrderBody> response) {
                if (response.isSuccessful()) {
                    ResponseCreateZalopayOrderBody body = response.body();
                    if (body != null && body.getReturnCode() == 1) {
                        ZaloPaySDK.getInstance().payOrder(ZaloPayPaymentActivity.this, body.getZpTransToken(), String.format("littleflower://paymentresult?order_id=%s&trans_id=%s", orderId, transId), new ZalopayListener());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateZalopayOrderBody> call, Throwable t) {

            }
        }
        return new CreatePaymentCallback();
    }

    private class ZalopayListener implements PayOrderListener {
        @Override
        public void onPaymentSucceeded(String s, String s1, String s2) {
            String orderId = getIntent().getStringExtra("orderId");
            HashMap<String, Object> updatedData = new HashMap<>();
            updatedData.put("status", "pending");
            updatedData.put("trans_id", s);
            orderCollection.document(orderId).update(updatedData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ZaloPayPaymentActivity.this, "Đặt hàng thành công!", Toast.LENGTH_LONG).show();
                    ZaloPayPaymentActivity.this.finish();
                }
            });
        }

        @Override
        public void onPaymentCanceled(String s, String s1) {
        }

        @Override
        public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
            if (zaloPayError == ZaloPayError.PAYMENT_APP_NOT_FOUND) {
                ZaloPaySDK.getInstance().navigateToZaloOnStore(getApplicationContext());
                ZaloPaySDK.getInstance().navigateToZaloPayOnStore(getApplicationContext());
            }
        }
    }

    private void onPayment() {
        String orderId = getIntent().getStringExtra("orderId");
        if (orderId != null && firebaseAuth.getCurrentUser() != null)
            orderCollection.document(orderId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.getString("status") == null || !Objects.equals(doc.getString("status"), "paying"))
                        return;
                    String transId = String.format("%s%s", dateFormat.format(new Date()), doc.getId());
                    long totalPrice = ((List<HashMap<String, Object>>) doc.get("products")).stream().map(product -> (long) product.getOrDefault("unitPrice", 0) * (long) product.getOrDefault("quantity", 0)).reduce(0L, Long::sum);
                    ZaloPayUtil.createPayment(orderId, firebaseAuth.getCurrentUser().getUid(), totalPrice, transId).enqueue(createPaymentCallback(doc.getId(), transId));
                }
            });
    }
}