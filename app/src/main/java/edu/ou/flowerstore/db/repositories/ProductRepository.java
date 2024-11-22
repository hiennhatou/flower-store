package edu.ou.flowerstore.db.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.ou.flowerstore.db.DAOs.ProductDAO;
import edu.ou.flowerstore.db.RoomDB;
import edu.ou.flowerstore.db.entities.ProductEntity;

public class ProductRepository {
    private final ProductDAO productDAO;
    private LiveData<List<ProductEntity>> products;

    public ProductRepository(Application application) {
        RoomDB db = RoomDB.getDatabase(application);
        productDAO = db.getProductDAO();
    }

    public LiveData<List<ProductEntity>> getAllUsers() {
        if (products == null)
            products = productDAO.getAllProducts();

        return products;
    }

    public void insertUser(ProductEntity products) {
        RoomDB.dbWriteExecutor.execute(() -> {
            productDAO.insert(products);
        });
    }
}
