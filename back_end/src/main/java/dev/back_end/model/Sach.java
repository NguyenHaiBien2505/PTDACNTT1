package dev.back_end.model;

public class Sach {
    private String maSach;
    private String anh;
    private String tenSach;
    private String tenTacGia;
    private String maTheLoai;
    private String maNhaXuatBan;
    private double gia;
    private int soLuong;
    private String moTa;

    public Sach() {
    }

    public Sach(String maSach, String anh, String tenSach, String tenTacGia, String maTheLoai, String maNhaXuatBan, double gia, int soLuong, String moTa) {
        this.maSach = maSach;
        this.anh = anh;
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
        this.maTheLoai = maTheLoai;
        this.maNhaXuatBan = maNhaXuatBan;
        this.gia = gia;
        this.soLuong = soLuong;
        this.moTa = moTa;
    }

    // Getters and Setters
    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getMaNhaXuatBan() {
        return maNhaXuatBan;
    }

    public void setMaNhaXuatBan(String maNhaXuatBan) {
        this.maNhaXuatBan = maNhaXuatBan;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}