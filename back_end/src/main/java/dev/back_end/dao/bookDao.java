package dev.back_end.dao;

import dev.back_end.model.Sach;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class bookDao extends DBContext {
    public bookDao bookDao() {
        return new bookDao();
    }

    public List<Sach> getAllBooks() {
        List<Sach> list = null;
        try (PreparedStatement pre = connect().prepareStatement("SELECT * FROM Sach")) {
            ResultSet rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Sach(rs.getString("MaSach"), rs.getString("Anh"), rs.getString("TenSach"), rs.getString("TenTacGia"), rs.getString("MaTheLoai"), rs.getString("MaNhaXuatBan"), rs.getDouble("Gia"), rs.getInt("SoLuong"), rs.getString("MoTa")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean editBook(Sach sach) {
        String query = "UPDATE Sach SET Anh = ?, TenSach = ?, TenTacGia = ?, MaTheLoai = ?, MaNhaXuatBan = ?, Gia = ?, SoLuong = ?, MoTa = ? WHERE MaSach = ?";
        try (PreparedStatement pre = connect().prepareStatement(query)) {
            pre.setString(1, sach.getAnh());
            pre.setString(2, sach.getTenSach());
            pre.setString(3, sach.getTenTacGia());
            pre.setString(4, sach.getMaTheLoai());
            pre.setString(5, sach.getMaNhaXuatBan());
            pre.setDouble(6, sach.getGia());
            pre.setInt(7, sach.getSoLuong());
            pre.setString(8, sach.getMoTa());
            pre.setString(9, sach.getMaSach());
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteBook(String maSach) {
    String query = "DELETE FROM Sach WHERE MaSach = ?";
    try (PreparedStatement pre = connect().prepareStatement(query)) {
        pre.setString(1, maSach);
        return pre.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    public boolean borrowBooks(String studentId, List<String> bookIds, List<String> borrowDates, List<String> returnDates) {
        return false;
    }

    public static void main(String[] args) {
        bookDao bookDao = new bookDao();
        List<Sach> list = bookDao.getAllBooks();
        for (Sach sach : list) {
            System.out.println(sach.getTenSach());
        }
    }

   public Sach getBookById(String id) {
    String query = "SELECT * FROM Sach WHERE MaSach = ?";
    try (PreparedStatement pre = connect().prepareStatement(query)) {
        pre.setString(1, id);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            return new Sach(
                rs.getString("MaSach"),
                rs.getString("Anh"),
                rs.getString("TenSach"),
                rs.getString("TenTacGia"),
                rs.getString("MaTheLoai"),
                rs.getString("MaNhaXuatBan"),
                rs.getDouble("Gia"),
                rs.getInt("SoLuong"),
                rs.getString("MoTa")
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
}
