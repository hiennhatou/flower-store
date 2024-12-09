package edu.ou.flowerstore.ui.cart;

import androidx.annotation.Nullable;

public class CartItem {
    private String id;
    private long cartId;
    private String image;
    private String name;
    private long price;
    private int quantity;

    public CartItem(String id, String image, String name, long price, int quantity, long cartId) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.id = id;
        this.quantity = quantity;
        this.cartId = cartId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        return (int) (id.hashCode() * image.hashCode() * name.hashCode() * price);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof CartItem)) return false;
        CartItem anotherCartItem = (CartItem) obj;
        if (!name.equals(anotherCartItem.name)) return false;
        if (!image.equals(anotherCartItem.image)) return false;
        if (!id.equals(anotherCartItem.id)) return false;
        return price == anotherCartItem.price;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }
}
