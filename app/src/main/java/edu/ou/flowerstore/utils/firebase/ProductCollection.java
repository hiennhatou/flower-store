package edu.ou.flowerstore.utils.firebase;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.ou.flowerstore.db.entities.ProductEntity;

public class ProductCollection {
    private final FirebaseFirestore firestore;
    private final CollectionReference collection;
    public ProductCollection() {
        this.firestore = FirebaseFirestore.getInstance();
        this.collection = firestore.collection("products");
    }

    public Task<DocumentReference> insert(ProductEntity product) {
        Map<String, Object> productMap = new HashMap<>();
        productMap.put("name", product.name);
        productMap.put("description", product.description);
        productMap.put("price", product.price);
        productMap.put("status", product.status);
        productMap.put("thumbnail", product.thumbnail);
        productMap.put("category_id", product.categoryId);

        return collection.add(productMap);
    }
}
