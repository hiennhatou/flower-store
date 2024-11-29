package edu.ou.flowerstore.utils.firebase.collections;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CategoryCollection {
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public Task<QuerySnapshot> getAllCategories() {
        return firestore.collection("categories").get();
    }
}
