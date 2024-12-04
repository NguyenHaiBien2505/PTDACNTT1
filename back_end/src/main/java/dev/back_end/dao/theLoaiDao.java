package dev.back_end.dao;

import dev.back_end.model.TheLoai;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class theLoaiDao extends DBContext {
    public List<TheLoai> getAll() {
        List<TheLoai> list = null;
        try (PreparedStatement pre = connect().prepareStatement("SELECT * FROM TheLoai")) {
            ResultSet rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(new TheLoai(rs.getString("MaTheLoai"), rs.getString("TenTheLoai")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
