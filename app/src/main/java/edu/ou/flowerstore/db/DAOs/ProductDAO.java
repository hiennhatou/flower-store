package edu.ou.flowerstore.db.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.ou.flowerstore.db.entities.ProductEntity;

@Dao
public interface ProductDAO {
    @Insert
    void insert(ProductEntity...products);

    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> getAllProducts();

    @Delete
    void delete(ProductEntity category);
}
