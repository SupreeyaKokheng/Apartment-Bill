package com.apartment.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
		// BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		// // รหัสผ่านที่ต้องการทดสอบ
		// String rawPassword = "password123";
		// String storedPassword = "$2a$10$X.YzLPquD/Vn7ZpH5ZuR8u7J1dO6c7jVhOvPbH6j1pH6/F.";

		// // ทดสอบการ match
		// boolean isMatch = encoder.matches(rawPassword, storedPassword);
		// System.out.println("🔍 passwordEncoder.matches: " + isMatch);

		// // ทดสอบเข้ารหัสรหัสผ่านใหม่ ว่ามันแปลงแล้วจับคู่กันได้ไหม
	
		// String encodedPassword = encoder.encode(rawPassword);

		// System.out.println("🔐 Encoded Password: " + encodedPassword);

		
	}

}
