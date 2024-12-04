package dev.back_end.dao;

import dev.back_end.model.Sach;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


public boolean borrowBooks(String maThuThu, String studentId, List<String> bookIds, List<String> borrowDates, List<String> returnDates) {
    String insertPhieuMuonTra = "INSERT INTO PhieuMuonTra (MaPhieu, MaThuThu, MaDocGia) VALUES (?, ?, ?)";
    String insertChiTietPhieu = "INSERT INTO ChiTietPhieu (MaSach, MaPhieu, NgayMuon, NgayTra, TrangThai) VALUES (?, ?, ?, ?, ?)";

    Connection conn = null;
    PreparedStatement psPhieuMuonTra = null;
    PreparedStatement psChiTietPhieu = null;

    try {
        conn = connect();
        conn.setAutoCommit(false);

        String maPhieu = UUID.randomUUID().toString().substring(0,20);

        // Insert into PhieuMuonTra
        psPhieuMuonTra = conn.prepareStatement(insertPhieuMuonTra);
        psPhieuMuonTra.setString(1, maPhieu);
        psPhieuMuonTra.setString(2, maThuThu); // Replace with actual MaThuThu
        psPhieuMuonTra.setString(3, studentId);
        psPhieuMuonTra.executeUpdate();

        // Insert into ChiTietPhieu
        psChiTietPhieu = conn.prepareStatement(insertChiTietPhieu);
        for (int i = 0; i < bookIds.size(); i++) {
            psChiTietPhieu.setString(1, bookIds.get(i));
            psChiTietPhieu.setString(2, maPhieu);
            psChiTietPhieu.setDate(3, java.sql.Date.valueOf(borrowDates.get(i)));
            psChiTietPhieu.setDate(4, java.sql.Date.valueOf(returnDates.get(i)));
            psChiTietPhieu.setString(5, "Đang mượn");
            psChiTietPhieu.addBatch();
        }
        psChiTietPhieu.executeBatch();

        conn.commit();
        return true;
    } catch ( SQLException e) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        return false;
    } finally {
        try {
            if (psPhieuMuonTra != null) psPhieuMuonTra.close();
            if (psChiTietPhieu != null) psChiTietPhieu.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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

    public void addBook(Sach sach) {
        String query = "INSERT INTO Sach (MaSach, Anh, TenSach, TenTacGia, MaTheLoai, MaNhaXuatBan, Gia, SoLuong, MoTa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pre = connect().prepareStatement(query)) {
            pre.setString(1, sach.getMaSach());
            pre.setString(2, sach.getAnh());
            pre.setString(3, sach.getTenSach());
            pre.setString(4, sach.getTenTacGia());
            pre.setString(5, sach.getMaTheLoai());
            pre.setString(6, sach.getMaNhaXuatBan());
            pre.setDouble(7, sach.getGia());
            pre.setInt(8, sach.getSoLuong());
            pre.setString(9, sach.getMoTa());
            pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
