package dev.back_end.controller;

import dev.back_end.model.TaiKhoan;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import dev.back_end.dao.taiKhoanDao;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private taiKhoanDao taiKhoanDao;

    @GetMapping("/login")
    public String login() {
        log.info("Login page requested");
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @PostMapping("/register")
    public String register(@RequestParam String maTK, @RequestParam String tenHienThi, @RequestParam String matKhau) {
        TaiKhoan taiKhoan = new TaiKhoan(maTK, matKhau, tenHienThi,true,true);
        taiKhoanDao.save(taiKhoan);
        return "redirect:/auth/login";
    }
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        log.info("Forgot password page requested");
        return "forgot";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}