package edu.ou.flowerstore.ui.cart;

public class CartItem {
    private int image;
    private String name;
    private int price;
    private int rate;

    public CartItem(int image, String name, int price, int rate) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.rate = rate;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
