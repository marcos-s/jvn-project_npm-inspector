package com.marcos.starter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")

public class MessageController {
    @GetMapping("/hello")
    public String hello() {
        return "HAHAHAHHAHAHAHAHAHA";
    }
}
