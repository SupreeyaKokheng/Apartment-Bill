package com.apartment.management.dto; // ✅ ตรวจสอบ package

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO { // ✅ ชื่อคลาสต้องตรงกับชื่อไฟล์

    private String username;
    private String password;

    public LoginRequestDTO() {}

    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
