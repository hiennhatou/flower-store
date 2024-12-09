package edu.ou.flowerstore.utils.firebase.documents;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class ProductDocument {
    private String id;
    private String name;
    private long price;
    private boolean status;
    private String description;
    private Set<String> categories;
    private Set<String> images;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<CategoryDocument> fetchedCategories;
    

    public ProductDocument() {}

    public ProductDocument(String id) {
        this.id = id;
    }

    public ProductDocument(String id, String name, long price, Set<String> images, LocalDateTime createdDate, boolean status) {
        this.id = id;
        setName(name);
        setPrice(price);
        setImages(images);
        setCreatedDate(createdDate);
        setStatus(status);
    }

    public ProductDocument(String id, String name, String description, long price, boolean status, Set<String> images, Set<String> categories, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this(id, name, price, images, createdDate, status);
        setDescription(description);
        setCategories(categories);
        setUpdatedDate(updatedDate);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public boolean isStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public Set<String> getImages() {
        return images;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public List<CategoryDocument> getFetchedCategories() {
        return fetchedCategories;
    }

    public void setFetchedCategories(List<CategoryDocument> fetchedCategories) {
        this.fetchedCategories = fetchedCategories;
    }
}
