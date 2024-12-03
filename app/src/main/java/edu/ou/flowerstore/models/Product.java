package edu.ou.flowerstore.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import java.util.ArrayList;

public class Product {
    private String name;
    private String description;
    private ArrayList<String> image;
    private ArrayList<DocumentReference> categories; // Mảng các danh mục dưới dạng DocumentReference
    private double price;
    private boolean status;
    private Timestamp created_date;
    private Timestamp updated_date;

    // Constructor
    public Product(String name, String description, ArrayList<String> image,
                   ArrayList<DocumentReference> categories, double price, boolean status,
                   Timestamp created_date, Timestamp updated_date) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.categories = categories;
        this.price = price;
        this.status = status;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    // Getters và Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public ArrayList<DocumentReference> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<DocumentReference> categories) {
        this.categories = categories;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }

    public Timestamp getUpdated_date() {
        return updated_date;
    }

    public void setUpdatedDate(Timestamp updated_date) {
        this.updated_date = updated_date;
    }
}
