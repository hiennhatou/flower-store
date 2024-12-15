package edu.ou.flowerstore.ui.category;

public class Category {
    private String code;  // Mã danh mục, sử dụng làm ID của tài liệu Firestore
    private String name;
    private boolean status;

    public Category() {
        // Constructor mặc định cho Firestore
    }

    public Category(String code, String name, boolean status) {
        this.code = code;
        this.name = name;
        this.status = status;
    }


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
