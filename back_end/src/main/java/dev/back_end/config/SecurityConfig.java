package dev.back_end.config;

import dev.back_end.model.TaiKhoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import dev.back_end.dao.taiKhoanDao;
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    taiKhoanDao taiKhoanDao;
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                ).formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/sach/danhSachSach")
                        .permitAll()
                ).csrf(AbstractHttpConfigurer::disable)
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .permitAll()
                );
        return httpSecurity.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Logic lấy thông tin user từ database
                TaiKhoan tk = taiKhoanDao.getTaiKhoanByUserName(username);
            if (username.equals(tk.getMaTK())) {
                return User.withUsername(tk.getMaTK())
                        .password("{noop}"+tk.getMatKhau()) // Dùng "{noop}" để không mã hóa
                        .roles(tk.isVaiTro() ? "DOCGIA" : "THUTHU")
                        .build();
            }
            throw new UsernameNotFoundException("User not found");
        };
    }

}
