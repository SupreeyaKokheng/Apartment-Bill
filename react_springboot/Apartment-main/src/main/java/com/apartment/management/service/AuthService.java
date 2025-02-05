
package com.apartment.management.service;

import com.apartment.management.model.User;
import com.apartment.management.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public boolean authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    System.out.println("🔹 ค้นหาผู้ใช้: " + username);
                    System.out.println("🔹 รหัสผ่านที่รับมา: " + password);
                    System.out.println("🔹 รหัสผ่านที่เก็บใน DB: " + user.getPassword());

                    boolean isMatch = passwordEncoder.matches(password, user.getPassword());
                    System.out.println("🔍 passwordEncoder.matches: " + isMatch);
                    return isMatch;
                })
                .orElseGet(() -> {
                    System.out.println("🚨 ไม่พบผู้ใช้ในระบบ!");
                    return false;
                });
    }


    public String generateJwtToken(String username) {
        return jwtService.generateToken(username);
    }
}