package edu.ou.flowerstore.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "carts", indices = {@Index(value = { "product_id" }, unique = true)})
public class CartEntity extends BaseEntity {
    @ColumnInfo()
    int quantity;

    @ColumnInfo()
    String product_id;
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
