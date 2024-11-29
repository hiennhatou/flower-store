package edu.ou.flowerstore.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import edu.ou.flowerstore.utils.adapters.OverviewProductAdapter;
import edu.ou.flowerstore.utils.firebase.collections.ProductCollection;

public class HomeViewModel extends ViewModel {
    private ArrayList<OverviewProductAdapter.OverviewProduct> products = new ArrayList<>();
    private MutableLiveData<List<OverviewProductAdapter.OverviewProduct>> liveDataProducts = new MutableLiveData<>(products);

    ProductCollection productCollection = new ProductCollection();

    public void loadProducts() {
        productCollection.getAllProducts().addOnSuccessListener(result -> {
            result.getDocuments().forEach(snapshot -> {
                String name = snapshot.getString("name");
                String thumbnail = ((List<String>) snapshot.get("image")).get(0);
                Long price = snapshot.getLong("price") != null ? snapshot.getLong("price") : Long.valueOf(0);
                String id = snapshot.getId();
                products.add(new OverviewProductAdapter.OverviewProduct(id, name, price, thumbnail, 2.5, true));
            });
            liveDataProducts.setValue(products);
        });
    }

    public MutableLiveData<List<OverviewProductAdapter.OverviewProduct>> getLiveDataProducts() {
        return liveDataProducts;
    }

    public ArrayList<OverviewProductAdapter.OverviewProduct> getProducts() {
        return products;
    }
}
