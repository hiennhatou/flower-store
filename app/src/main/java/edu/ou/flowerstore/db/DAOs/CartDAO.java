package edu.ou.flowerstore.db.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.ou.flowerstore.db.entities.CartEntity;

@Dao
public interface CartDAO {
    @Query("select * from carts")
    LiveData<List<CartEntity>> getAllInCart();

    @Query("select * from carts where id=:id")
    LiveData<CartEntity> getInCartById(long id);

    @Query("select * from carts where id in (:id)")
    LiveData<CartEntity> getInCartByIds(long ...id);

    @Insert
    void insertInCart(CartEntity ...carts);

    @Delete
    void deleteIn(CartEntity cart);

    @Update
    void updateCart(CartEntity cart);
}
