package dev.back_end.dao;

import dev.back_end.model.TaiKhoan;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class taiKhoanDao extends DBContext{
    public taiKhoanDao taiKhoanDao() {
        return new taiKhoanDao();
    }
 public TaiKhoan getTaiKhoanByUserName(String username) {
    String query = "SELECT * FROM TaiKhoan WHERE MaTK = ?";
    try (PreparedStatement pre = connect().prepareStatement(query)) {
        pre.setString(1, username);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setMaTK(rs.getString("MaTK"));
            taiKhoan.setMatKhau(rs.getString("MatKhau"));
            taiKhoan.setTenHienThi(rs.getString("TenHienThi"));
            taiKhoan.setVaiTro(rs.getBoolean("VaiTro"));
            taiKhoan.setTrangThai(rs.getBoolean("TrangThai"));
            return taiKhoan;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
    public boolean checkLogin(String username, String password) {
        String query = "SELECT * FROM TaiKhoan WHERE MaTK = ? AND MatKhau = ?";
        try (PreparedStatement pre = connect().prepareStatement(query)) {
            pre.setString(1, username);
            pre.setString(2, password);
            ResultSet rs = pre.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        taiKhoanDao taiKhoanDao = new taiKhoanDao();
        System.out.println(taiKhoanDao.checkLogin("TT001","123456"));
    }
}
