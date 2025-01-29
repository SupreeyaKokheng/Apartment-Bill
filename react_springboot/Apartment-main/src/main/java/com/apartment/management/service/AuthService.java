package com.apartment.management.service;

import com.apartment.management.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    public boolean authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> user.getPassword().equals(password)) // ตรวจสอบ password
                .orElse(false); // กรณี username ไม่พบ
    }
}
