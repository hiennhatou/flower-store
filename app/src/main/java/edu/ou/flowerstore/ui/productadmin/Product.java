package edu.ou.flowerstore.ui.productadmin;

public class Product {
    private String maSanPham;
    private String tenSP;
    private double soLuong;
    private double gia;
    private String hinhAnh;
    private String moTa;
    private String maLoai;


    public Product(){}
    // Constructor đầy đủ
    public Product(String maSanPham, String tenSP, double soLuong, double gia, String hinhAnh, String moTa, String maLoai) {
        this.maSanPham = maSanPham;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
        this.moTa = moTa;
        this.maLoai = maLoai; // Lưu mã loại
    }

    // Getter và Setter
    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(double soLuong) {
        this.soLuong = soLuong;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }
}