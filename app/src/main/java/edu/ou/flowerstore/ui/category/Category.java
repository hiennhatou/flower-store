package edu.ou.flowerstore.ui.category;

public class Category {
    private String code; // Thêm trường code
    private String name;
    private boolean status;

    public Category() {
        // Default constructor for Firestore
    }

    public Category(String code, String name, boolean status) { // Cập nhật constructor
        this.code = code;
        this.name = name;
        this.status = status;
    }

    // Getters and setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}