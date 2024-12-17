package edu.ou.flowerstore.ui.admin.categories;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.ou.flowerstore.utils.firebase.AppFirebase;

public class AdminCategoryViewModel extends ViewModel {
    private CollectionReference categoryCollection = new AppFirebase().getCategoriesCollection();
    private List<AdminCategoryAdapter.CategoryItem> categoryItems = new ArrayList<>();
    private AdminCategoryAdapter adapter = new AdminCategoryAdapter(categoryItems);
    private Context context;

    public AdminCategoryViewModel() {
        adapter.setOnDelete(categoryItem -> {
            categoryCollection.document(categoryItem.id).delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    categoryItems.remove(categoryItem);
                    adapter.notifyDataSetChanged();
                    if (context != null)
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_LONG).show();
                }
            });
        });

        adapter.setOnClickView(categoryItem -> {
            Intent intent = new Intent(context, AdminCategoryDetailActivity.class);
            intent.putExtra("id", categoryItem.id);
            context.startActivity(intent);
        });

        loadData();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private void loadData() {
        categoryCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                for (DocumentSnapshot category : snapshot.getDocuments()) {
                    categoryItems.add(new AdminCategoryAdapter.CategoryItem(category.getId(), category.getString("name")));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public AdminCategoryAdapter getAdapter() {
        return adapter;
    }
}
