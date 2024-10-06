package com.offlixtrade.crypto.controller;

import com.offlixtrade.crypto.model.ApiGlobalResponse;
import com.offlixtrade.crypto.model.User;
import com.offlixtrade.crypto.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@RequestBody User user) {
        return new ResponseEntity<>(new ApiGlobalResponse<>(authService.register(user)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody User user) {
        return new ResponseEntity<>(new ApiGlobalResponse<>(authService.login(user)), HttpStatus.OK);
    }

    @PostMapping("/truncate")
    public ResponseEntity<?> truncateTable() {
        try {
            authService.truncateTable();
            return new ResponseEntity<>(new ApiGlobalResponse<>(null, Optional.of("Truncated")), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
