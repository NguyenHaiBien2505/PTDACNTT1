package dev.back_end.controller;

import dev.back_end.model.Sach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import dev.back_end.dao.bookDao;
import java.util.List;

@Controller
@RequestMapping("/sach")
public class SachController {
    @Autowired
    private bookDao bookDao;
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
    //Add book
    @GetMapping("/tao-sach")
    public String addBookForm() {
        return "Tao_sach";
    }
    // Edit book
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") String id, Model model) {
        Sach sach = bookDao.getBookById(id);
        model.addAttribute("sach", sach);
        return "Edit_sach";
    }

    @PostMapping("/edit")
    public String editBook(@ModelAttribute Sach sach) {
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
    @PostMapping
    public String borrowBooks(@RequestParam("nhapMSV") String studentId,
                              @RequestParam("masach") List<String> bookIds,
                              @RequestParam("date_muon") List<String> borrowDates,
                              @RequestParam("date_tra") List<String> returnDates,
                              Model model) {
        boolean success = bookDao.borrowBooks(studentId, bookIds, borrowDates, returnDates);
        if (success) {
            model.addAttribute("message", "Books borrowed successfully!");
        } else {
            model.addAttribute("message", "Failed to borrow books. Please try again.");
        }
        return "Muon_sach"; // Return to the borrowing page
    }

}
