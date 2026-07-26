package com.demo.intern_demo.controller;

import com.demo.intern_demo.model.User;
import com.demo.intern_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.demo.intern_demo.exception.DuplicateEmailException;
import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Page<User> getAllUsers(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "5")  int size) {
        return userRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email is already in use");
        }
        return userRepository.save(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (user.getId() == null || !userRepository.existsById(user.getId())) {
            return null;
        }
        userRepository.findByEmail(user.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(user.getId())) {
                throw new DuplicateEmailException("Email is already in use");
            }
        });
        return userRepository.save(user);
    }

    @DeleteMapping
    public User deleteUser(@RequestBody User user) {
        if (user.getId() == null || !userRepository.existsById(user.getId())) {
            return null;
        }
        userRepository.delete(user);
        return user;
    }
}
