package edu.ou.flowerstore.ui.orders;

public class Product {
    private String name;
    private String description;
    private String price;
    private String imageUrl;
    private int quantity;

    public Product(String name, String description, String price, String imageUrl, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public int getQuantity() { return quantity; }
}