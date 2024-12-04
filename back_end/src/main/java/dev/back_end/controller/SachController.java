package dev.back_end.controller;

import dev.back_end.model.ChiTietPhieu;
import dev.back_end.model.NhaXuatBan;
import dev.back_end.model.Sach;
import dev.back_end.model.TheLoai;
import dev.back_end.utility.ConvertImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import dev.back_end.dao.bookDao;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/sach")
public class SachController {
    private static final Logger log = LogManager.getLogger(SachController.class);
    @Autowired
    private bookDao bookDao;
    @Autowired
    private dev.back_end.dao.taiKhoanDao taiKhoanDao;


    // Mapping to the borrowing page
    @GetMapping("/muon-sach")
    public String muonSach() {
        return "Muon_sach";
    }

    //Mapping to the returning page
    @GetMapping("/tra-sach")
    public String traSach() {
        return "Tra_sach";
    }

    //Get all books
    @GetMapping("/danhSachSach")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookDao.getAllBooks());
        return "Danh_sach_sach";
    }

    //add book
    @GetMapping("/tao-sach")
    public String addBookForm(Model model) {
        List<TheLoai> theLoaiList = new dev.back_end.dao.theLoaiDao().getAll();
        List<NhaXuatBan> nhaXuatBans = new dev.back_end.dao.nhaXuatBanDao().getAll();
        model.addAttribute("theLoaiList", theLoaiList);
        model.addAttribute("nhaXuatBans", nhaXuatBans);
        return "Tao_sach";
    }

    //Add book
    @PostMapping("/tao-sach")
    public String addBookForm(Model model, @RequestParam("masach") String maSach,
                              @RequestParam("tensach") String tenSach,
                              @RequestParam("tentg") String tenTacGia,
                              @RequestParam("loaisach") String loaiSach,
                              @RequestParam("manxb") String maNhaXuatBan,
                              @RequestParam("giatien") double giaTien,
                              @RequestParam("soluong") int soLuong,
                              @RequestParam("mota") String moTa,
                              @RequestParam("image") MultipartFile image) {
        Sach sach = new Sach();
        sach.setMaSach(maSach);
        sach.setTenSach(tenSach);
        sach.setTenTacGia(tenTacGia);
        sach.setMaTheLoai(loaiSach);
        sach.setMaNhaXuatBan(maNhaXuatBan);
        sach.setGia(giaTien);
        sach.setSoLuong(soLuong);
        sach.setMoTa(moTa);
        //convert img to base64
        try {
            sach.setAnh(ConvertImage.convertMultipartFileToBase64WithMimeType(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Adding book: " + sach.getAnh());
        bookDao.addBook(sach);
        return "redirect:/sach/danhSachSach";
    }


    // Edit book
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") String id, Model model) {
        Sach sach = bookDao.getBookById(id);
        model.addAttribute("sach", sach);
        List<TheLoai> theLoaiList = new dev.back_end.dao.theLoaiDao().getAll();
        List<NhaXuatBan> nhaXuatBans = new dev.back_end.dao.nhaXuatBanDao().getAll();
        model.addAttribute("theLoaiList", theLoaiList);
        model.addAttribute("nhaXuatBans", nhaXuatBans);
        return "Sua_sach";
    }

    @PostMapping("/sua-sach")
    public String editBook(@RequestParam("masach") String maSach,
                           @RequestParam("tensach") String tenSach,
                           @RequestParam("tentg") String tenTacGia,
                           @RequestParam("loaisach") String loaiSach,
                           @RequestParam("manxb") String maNhaXuatBan,
                           @RequestParam("giatien") double giaTien,
                           @RequestParam("soluong") int soLuong,
                           @RequestParam("mota") String moTa,
                           @RequestParam("image") MultipartFile image) {
        Sach sach = new Sach();
        sach.setMaSach(maSach);
        sach.setTenSach(tenSach);
        sach.setTenTacGia(tenTacGia);
        sach.setMaTheLoai(loaiSach);
        sach.setMaNhaXuatBan(maNhaXuatBan);
        sach.setGia(giaTien);
        sach.setSoLuong(soLuong);
        sach.setMoTa(moTa);
        //convert img to base64
        try {
            sach.setAnh(ConvertImage.convertMultipartFileToBase64WithMimeType(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
        bookDao.editBook(sach);
        return "redirect:/sach/danhSachSach";
    }

    // Delete book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") String id) {
        bookDao.deleteBook(id);
        return "redirect:/sach/danhSachSach";
    }

    // Handle the borrowing request
    @PostMapping("/muon-sach")
    public String borrowBooks(@RequestParam("nhapMSV") String studentId,
                              @RequestParam("masach") List<String> bookIds,
                              @RequestParam("date_muon") List<String> borrowDates,
                              @RequestParam("date_tra") List<String> returnDates,
                              Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean success = bookDao.borrowBooks(user.getUsername(), studentId, bookIds, borrowDates, returnDates);
        if (success) {
            model.addAttribute("message", "Books borrowed successfully!");
        } else {
            model.addAttribute("message", "Failed to borrow books. Please try again.");
        }
        return "Muon_sach"; // Return to the borrowing page
    }
    @GetMapping("/tra-mot-sach")
    public String traSach(Model model, @RequestParam String maPhieu, @RequestParam String maHocSinh, @RequestParam String maSach) {
        bookDao.traSach(maPhieu,maSach);
        return "redirect:/sach/loadHocSinh/"+maHocSinh+"--Tra_sach";
    }

    @GetMapping("/tra-tat-ca-sach")
    public String traTatCaSach(@RequestParam String maHocSinh) {
        bookDao.traTatCaSach(maHocSinh);
        return "redirect:/sach/loadHocSinh/"+maHocSinh+"--Tra_sach";
    }
    //load thong tin hoc sinh
    @GetMapping("/loadHocSinh/{maHocSinh}")
    public String loadHocSinh(@PathVariable("maHocSinh") String studentId, Model model) {
        log.info("Loading student information: " + studentId);
        String[] strings = studentId.split("--");
        if (taiKhoanDao.getTaiKhoanByUserName(strings[0]) == null) {
            model.addAttribute("message", "Student not found!");
            return strings[1];
        }
        model.addAttribute("student", taiKhoanDao.getTaiKhoanByUserName(strings[0]));
        if (strings[1] .equals("Tra_sach")) {
            List<ChiTietPhieu> chiTietPhieus = bookDao.getSachDangMuonBoiHocSinh(strings[0]);
            log.info(chiTietPhieus);
            if (chiTietPhieus != null) {
                model.addAttribute("sachMuon", chiTietPhieus);
            }
        }
        return strings[1];
    }

}
