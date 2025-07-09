package com.gildedrose.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
    
    @GetMapping("/calendar")
    public String calendar() {
        return "calendar";
    }
}