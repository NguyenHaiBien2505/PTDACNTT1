package dev.back_end.dao;

import dev.back_end.model.NhaXuatBan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class nhaXuatBanDao extends DBContext {
    //get All
    public List<NhaXuatBan> getAll() {
        List<NhaXuatBan> list = null;
        String sql = "SELECT * FROM NhaXuatBan";
        try {
            PreparedStatement ps = connect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(new NhaXuatBan(rs.getString("MaNhaXuatBan"), rs.getString("TenNhaXuatBan")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
