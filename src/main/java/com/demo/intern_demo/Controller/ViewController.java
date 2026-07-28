package com.demo.intern_demo.controller;

import com.demo.intern_demo.model.User;
import com.demo.intern_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false)   Long editId,
            Model model) {

        Page<User> userPage = userRepository.findAll(
                PageRequest.of(page, size, Sort.by("id")));

        model.addAttribute("users",         userPage.getContent());
        model.addAttribute("currentPage",   userPage.getNumber());
        model.addAttribute("totalPages",    userPage.getTotalPages());
        model.addAttribute("totalElements", userPage.getTotalElements());
        model.addAttribute("isFirst",       userPage.isFirst());
        model.addAttribute("isLast",        userPage.isLast());
        model.addAttribute("pageSize",      size);

        User editUser = (editId != null)
                ? userRepository.findById(editId).orElse(new User())
                : new User();
        model.addAttribute("editUser", editUser);

        return "index";
    }

    @GetMapping("/endpoints")
    public String endpoints() {
        return "endpoints";
    }

    /** Read-only view of all users — linked from the Endpoints page */
    @GetMapping("/users")
    public String users(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {

        Page<User> userPage = userRepository.findAll(
                PageRequest.of(page, size, Sort.by("id")));

        model.addAttribute("users",         userPage.getContent());
        model.addAttribute("currentPage",   userPage.getNumber());
        model.addAttribute("totalPages",    userPage.getTotalPages());
        model.addAttribute("totalElements", userPage.getTotalElements());
        model.addAttribute("isFirst",       userPage.isFirst());
        model.addAttribute("isLast",        userPage.isLast());
        model.addAttribute("pageSize",      size);

        return "users";
    }
}
