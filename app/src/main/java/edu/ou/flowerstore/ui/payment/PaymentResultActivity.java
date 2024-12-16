package edu.ou.flowerstore.ui.payment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.MainActivity;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.ActivityPaymentResultBinding;
import edu.ou.flowerstore.ui.orders.CustomerOrderDetailActivity;
import edu.ou.flowerstore.utils.firebase.AppFirebase;
import edu.ou.flowerstore.utils.zalopay.ResponseCreateZalopayOrderBody;
import edu.ou.flowerstore.utils.zalopay.ZaloPayUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.ZaloPaySDK;

public class PaymentResultActivity extends AppCompatActivity {
    private ActivityPaymentResultBinding binding;
    private Intent intent;
    private String orderId;
    private String transId;
    private FlowerStoreApplication application;
    private AppFirebase appFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentResultBinding.inflate(getLayoutInflater());
        intent = getIntent();
        if (intent == null) finish();
        Uri data = intent.getData();
        if (data == null) finish();
        orderId = data.getQueryParameter("order_id");
        transId = data.getQueryParameter("trans_id");
        if (orderId == null || transId == null || orderId.isEmpty() || transId.isEmpty()) finish();
        application = FlowerStoreApplication.getInstance();
        appFirebase = application.getAppFirebase();

        ZaloPaySDK.getInstance().onResult(getIntent());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.backToHome.setOnClickListener(v -> {
            Intent newIntent = new Intent(PaymentResultActivity.this, MainActivity.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newIntent);
            finish();
        });

        binding.toOrderDetail.setOnClickListener(v -> {
            Intent newIntent = new Intent(PaymentResultActivity.this, CustomerOrderDetailActivity.class);
            newIntent.putExtra("order_id", orderId);
            startActivity(newIntent);
        });

        ZaloPayUtil.queryTransStatus(transId).enqueue(new Callback<ResponseCreateZalopayOrderBody>() {
            @Override
            public void onResponse(Call<ResponseCreateZalopayOrderBody> call, Response<ResponseCreateZalopayOrderBody> response) {
                if (response.isSuccessful()) {
                    ResponseCreateZalopayOrderBody body = response.body();
                    binding.iconImg.setImageDrawable(ResourcesCompat.getDrawable(getResources(), body != null && body.getReturnCode() == 1 ? R.drawable.ic_check : R.drawable.ic_x, null));
                    binding.iconImg.setColorFilter(Color.parseColor(body != null && body.getReturnCode() == 1 ? "#8CE07C" : "#c94f4f"));
                    binding.message.setText(body != null && body.getReturnCode() == 1 ? "Thanh toán thành công" : "Đã xảy ra lỗi khi thanh toán");
                } else {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateZalopayOrderBody> call, Throwable t) {
                finish();
            }
        });
    }
}