package edu.ou.flowerstore.ui.productdetail;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.AggregateField;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.ActivityProductDetailBinding;
import edu.ou.flowerstore.db.RoomDB;
import edu.ou.flowerstore.ui.cart.AddCartDialog;
import edu.ou.flowerstore.ui.cart.CartActivity;
import edu.ou.flowerstore.utils.firebase.AppFirebase;

public class ProductDetailActivity extends AppCompatActivity {
    private ActivityProductDetailBinding binding;
    private RoomDB roomDB;
    private final AppFirebase appFirebase = new AppFirebase();
    private final Locale locale = new Locale("vi", "vn");
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
    private final DecimalFormat decimalFormat = new DecimalFormat("#.#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        roomDB = FlowerStoreApplication.getInstance().getRoomDB();

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
        binding.cartBtn.setOnClickListener(v -> {
            Intent cartIntent = new Intent(this, CartActivity.class);
            startActivity(cartIntent);
        });
        fetchProduct();
    }

    private void fetchProduct() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if (id == null || id.isEmpty()) {
            failFetch(null);
            return;
        }
        appFirebase.getProductsCollection().document(id).get().addOnSuccessListener(snapshot -> {
            List<String> images = (List<String>) snapshot.get("image");
            List<DocumentReference> categories = (List<DocumentReference>) snapshot.get("categories");
            String productName = snapshot.getString("name");
            String description = snapshot.getString("description");
            Long price = snapshot.getLong("price");
            if (images == null || images.isEmpty() || categories == null || categories.isEmpty() || productName == null || description == null || price == null) {
                failFetch(id);
                return;
            }
            categories.get(0).get().addOnSuccessListener(categorySnapshot -> {
               binding.productCategory.setText(categorySnapshot.getString("name"));
            });
            snapshot.getReference().collection("reviews").aggregate(AggregateField.average("rate")).get(AggregateSource.SERVER).addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().get(AggregateField.average("rate")) != null) {
                    double result = task.getResult().get(AggregateField.average("rate"));
                    binding.rate.setText(decimalFormat.format(result));
                } else {
                    binding.rate.setText("5,0");
                }
            });
            binding.productName.setText(productName);
            binding.productDescription.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY));
            binding.productPrice.setText(currencyFormat.format(price));
            binding.addCartBtn.setOnClickListener(v -> {
                roomDB.cartDAO().increaseProductInCart(snapshot.getId());
                AddCartDialog dialog = new AddCartDialog();
                dialog.show(getSupportFragmentManager(), AddCartDialog.TAG);
            });
            Picasso.get().load(images.get(0)).into(binding.productImg);
        }).addOnFailureListener(command -> {
            failFetch(id);
        });
    }

    private void failFetch(@Nullable String id) {
        Toast.makeText(this, "Xin lỗi vì có sự cố!", Toast.LENGTH_SHORT).show();
        finish();
    }
}