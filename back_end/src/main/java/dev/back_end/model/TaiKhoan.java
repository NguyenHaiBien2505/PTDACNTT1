package dev.back_end.model;


import java.io.Serializable;
//to save in session
public class TaiKhoan implements Serializable {
    private static final long serialVersionUID = 1L;
    private String maTK;
    private String matKhau;
    private String tenHienThi;
    private boolean vaiTro = true;
    private boolean trangThai = true;

    public TaiKhoan() {
    }

    public TaiKhoan(String maTK, String matKhau, String tenHienThi, boolean vaiTro, boolean trangThai) {
        this.maTK = maTK;
        this.matKhau = matKhau;
        this.tenHienThi = tenHienThi;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

    // Getters and Setters
    public String getMaTK() {
        return maTK;
    }

    public void setMaTK(String maTK) {
        this.maTK = maTK;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }

    public void setTenHienThi(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}