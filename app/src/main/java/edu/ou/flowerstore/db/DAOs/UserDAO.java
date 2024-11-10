package edu.ou.flowerstore.db.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.ou.flowerstore.db.entities.UserEntity;

@Dao
public interface UserDAO {
    @Insert()
    void insert(UserEntity... users);

    @Query("SELECT * FROM users")
    LiveData<List<UserEntity>> getAll();

    @Delete
    void delete(UserEntity user);
}
