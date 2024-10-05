package com.offlixtrade.crypto.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/health")
    public String getHello(HttpServletRequest servletRequest){
        return "OK";
}
}
