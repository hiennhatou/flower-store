package edu.ou.flowerstore.ui.add_product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.models.Product;

public class add_product extends AppCompatActivity {

    private EditText etProductName, etProductDescription, etProductPrice;
    private ImageView ivProductImage;
    private Spinner spinnerCategory;
    private Button btnAddProduct, btnReset;
    private Uri productImageUri;

    private FirebaseFirestore db;
    private ArrayList<String> categoriesList;
    private ArrayAdapter<String> categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Mapping views
        etProductName = findViewById(R.id.etProductName);
        etProductDescription = findViewById(R.id.etProductDescription);
        etProductPrice = findViewById(R.id.etProductPrice);
        ivProductImage = findViewById(R.id.ivProductImage);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnReset = findViewById(R.id.btnReset);


        categoriesList = new ArrayList<>();
        categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriesList);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoriesAdapter);


        ivProductImage.setOnClickListener(v -> openGallery());


        btnReset.setOnClickListener(v -> resetFields());


        btnAddProduct.setOnClickListener(v -> addProduct());

        // Apply window insets to avoid being hidden by system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        loadCategories();
    }

    // Load categories from Firestore
    private void loadCategories() {
        //  xóa categories cũ trước khi load mới
        categoriesList.clear();

        db.collection("categories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot documents = task.getResult();
                        if (documents != null && !documents.isEmpty()) {
                            for (QueryDocumentSnapshot document : documents) {
                                String categoryName = document.getString("name");
                                if (categoryName != null) {
                                    categoriesList.add(categoryName);
                                }
                            }
                            categoriesAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(this, "No categories found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Failed to load categories: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Mở gallery
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            productImageUri = data.getData();
            ivProductImage.setImageURI(productImageUri);
        }
    }

    // Handle adding product to Firestore
    private void addProduct() {
        String productName = etProductName.getText().toString().trim();
        String productDescription = etProductDescription.getText().toString().trim();
        String productPrice = etProductPrice.getText().toString().trim();

        // Tạo mảng cho các ảnh
        ArrayList<String> images = new ArrayList<>();
        String defaultImageUrl = "https://www.example.com/default-image.jpg";
        images.add(defaultImageUrl); // Thêm ảnh mặc định nếu không có ảnh nào được chọn

        // Tạo mảng cho các danh mục dưới dạng DocumentReference
        ArrayList<DocumentReference> categories = new ArrayList<>();
        String selectedCategory = spinnerCategory.getSelectedItem().toString();
        DocumentReference categoryRef = db.collection("categories").document(selectedCategory);
        categories.add(categoryRef); // Thêm DocumentReference của danh mục vào mảng

        boolean status = true; // Sản phẩm có sẵn


        if (productName.isEmpty() || productDescription.isEmpty() || productPrice.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }


        Timestamp currentTimestamp = new Timestamp(new Date());


        if (productImageUri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference imageRef = storageRef.child("product_images/" + System.currentTimeMillis() + ".jpg");

            imageRef.putFile(productImageUri)
                    .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {

                        images.set(0, uri.toString());

                        // Tạo đối tượng Product với mảng ảnh, mảng categories là DocumentReference và Timestamp
                        Product product = new Product(
                                productName,
                                productDescription,
                                images,  // Mảng ảnh
                                categories,  // Mảng DocumentReference của categories
                                Double.parseDouble(productPrice),
                                status,
                                currentTimestamp,
                                currentTimestamp
                        );

                        // Lưu sản phẩm
                        DocumentReference newProductRef = db.collection("products").document();
                        newProductRef.set(product)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Sản phẩm đã được thêm!", Toast.LENGTH_SHORT).show();
                                    resetFields();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Lỗi khi thêm sản phẩm: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                });
                    }))
                    .addOnFailureListener(e -> Toast.makeText(this, "Lỗi khi tải ảnh: " + e.getMessage(), Toast.LENGTH_LONG).show());
        } else {
            // sử dụng ảnh mặc định khi ko có ảnh
            Product product = new Product(
                    productName,
                    productDescription,
                    images,
                    categories,
                    Double.parseDouble(productPrice),
                    status,
                    currentTimestamp,
                    currentTimestamp
            );

            // Lưu sản phẩm -> Firestore
            DocumentReference newProductRef = db.collection("products").document();
            newProductRef.set(product)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Sản phẩm đã được thêm!", Toast.LENGTH_SHORT).show();
                        resetFields();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi khi thêm sản phẩm: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        }
    }


    // Reset input fields
    private void resetFields() {
        etProductName.setText("");
        etProductDescription.setText("");
        etProductPrice.setText("");
        ivProductImage.setImageResource(R.drawable.ic_avatar_placeholder);
        spinnerCategory.setSelection(0);
    }
}
