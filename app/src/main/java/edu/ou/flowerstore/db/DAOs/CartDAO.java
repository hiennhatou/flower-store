package edu.ou.flowerstore.db.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertInCart(CartEntity ...carts);

    @Query("select * from carts where product_id = :id")
    CartEntity getProductByProductId(String id);

    @Query("delete from carts where product_id=:id")
    void deleteProductByProductId(String id);

    @Delete
    void delete(CartEntity cart);

    @Update
    void updateCart(CartEntity cart);

    @Query("update carts set quantity = quantity + 1 where product_id=:id")
    void increaseQuantity(String id);

    @Query("update carts set quantity = quantity + :i where product_id=:id")
    void modifyQuantity(int i, String id);

    @Transaction
    default void decreaseProductInCart(String id) {
        CartEntity cartEntity = getProductByProductId(id);
        if (cartEntity == null) return;
        if (cartEntity.getQuantity() <= 1) {
            delete(cartEntity);
        } else {
            modifyQuantity(-1, id);
        }
    }

    @Transaction
    default void increaseProductInCart(String id) {
        CartEntity cart = new CartEntity();
        cart.setProduct_id(id);
        cart.setQuantity(1);
        List<Long> cartId = insertInCart(cart);
        if (cartId.isEmpty() || cartId.get(0) == -1) {
            modifyQuantity(1, id);
        }
    }
}
