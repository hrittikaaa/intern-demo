package com.demo.intern_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String helloPage(Model model) {
        model.addAttribute("message", "Hello, World!");
        return "hello";   // → templates/hello.html
    }

    /* Plain-text REST endpoint */
    @GetMapping(value = "/api/hello", produces = "text/plain")
    @ResponseBody
    public String sayHello() {
        return "Hello, World!";
    }
}