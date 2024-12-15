package edu.ou.flowerstore.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.NumberFormat;
import java.util.Locale;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.ActivityCartBinding;
import edu.ou.flowerstore.ui.makeorder.MakeOrderActivity;

public class CartActivity extends AppCompatActivity {
    static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "vn"));
    ActivityCartBinding binding;
    CartViewModel viewModel;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartAdapter = new CartAdapter(viewModel.getCartItemsLiveData().getValue());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.recyclerViewCartItems.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewCartItems.setAdapter(cartAdapter);
        viewModel.getCartItemsLiveData().observe(this, data -> {
            cartAdapter.setCartItems(data);
            cartAdapter.notifyDataSetChanged();
            if (data != null && data.size() > 0)
                binding.btnProceedToCheckout.setEnabled(true);
            else
                binding.btnProceedToCheckout.setEnabled(false);
            long totalPrice = data.stream().map(cart -> cart.getQuantity() * cart.getPrice()).reduce(0L, Long::sum);
            binding.tvTotalCost.setText(currencyFormat.format(totalPrice));
        });
        binding.btnProceedToCheckout.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, MakeOrderActivity.class));
        });
    }
}