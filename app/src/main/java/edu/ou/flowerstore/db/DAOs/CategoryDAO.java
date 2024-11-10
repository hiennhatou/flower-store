package edu.ou.flowerstore.db.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.ou.flowerstore.db.entities.CategoryEntity;

@Dao
public interface CategoryDAO {
    @Insert
    void insert(CategoryEntity ...categories);

    @Query("SELECT * FROM categories")
    LiveData<List<CategoryEntity>> getAllCategories();

    @Delete
    void delete(CategoryEntity category);
}
