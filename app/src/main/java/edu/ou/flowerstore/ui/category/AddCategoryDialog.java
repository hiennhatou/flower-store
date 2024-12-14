package edu.ou.flowerstore.ui.category;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.ou.flowerstore.databinding.DialogAddCategoryBinding;

public class AddCategoryDialog extends Dialog {
    private final OnCategoryAddedListener listener;

    public interface OnCategoryAddedListener {
        void onCategoryAdded(Category category);
    }

    public AddCategoryDialog(Context context, OnCategoryAddedListener listener) {
        super(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogAddCategoryBinding binding = DialogAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addButton.setOnClickListener(v -> {
            String code = binding.categoryCode.getText().toString().trim();
            String name = binding.categoryName.getText().toString().trim();

            if (code.isEmpty() || name.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập mã và tên danh mục!", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> categoryData = new HashMap<>();
            categoryData.put("name", name);
            categoryData.put("status", true);
            // categoryData.put("thumbnail", ""); // Bỏ qua trường thumbnail, thêm sau

            FirebaseFirestore.getInstance().collection("categories")
                    .document(code)  // Sử dụng code làm document_id
                    .set(categoryData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Thêm danh mục thành công!", Toast.LENGTH_SHORT).show();
                            dismiss(); // Chỉ đóng dialog, không gọi listener
                        } else {
                            Toast.makeText(getContext(), "Thêm danh mục thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        binding.cancelButton.setOnClickListener(v -> dismiss());
    }
}