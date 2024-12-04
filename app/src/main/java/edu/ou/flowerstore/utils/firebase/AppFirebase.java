package edu.ou.flowerstore.utils.firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppFirebase {
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public CollectionReference getProductsCollection() {
        return firestore.collection("products");
    }

    public CollectionReference getCategoriesCollection() {
        return firestore.collection("categories");
    }

    public CollectionReference getUsersCollection() {
        return firestore.collection("users");
    }

    public FirebaseFirestore getFirestore() {
        return firestore;
    }
}
