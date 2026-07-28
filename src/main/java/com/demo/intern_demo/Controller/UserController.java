package com.demo.intern_demo.controller;

import com.demo.intern_demo.exception.DuplicateEmailException;
import com.demo.intern_demo.model.User;
import com.demo.intern_demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /** Create – POST /users */
    @PostMapping
    public String createUser(@Valid @ModelAttribute User user,
                             BindingResult result,
                             RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error",
                result.getFieldErrors().get(0).getDefaultMessage());
            return "redirect:/";
        }
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new DuplicateEmailException("Email is already in use");
            }
            userRepository.save(user);
            redirectAttrs.addFlashAttribute("success", "User added successfully");
        } catch (DuplicateEmailException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }

    /** Update – POST /users/{id} */
    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute User user,
                             BindingResult result,
                             RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error",
                result.getFieldErrors().get(0).getDefaultMessage());
            return "redirect:/?editId=" + id;
        }
        if (!userRepository.existsById(id)) {
            redirectAttrs.addFlashAttribute("error", "User not found");
            return "redirect:/";
        }
        try {
            userRepository.findByEmail(user.getEmail()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new DuplicateEmailException("Email is already in use");
                }
            });
            user.setId(id);
            userRepository.save(user);
            redirectAttrs.addFlashAttribute("success", "User updated successfully");
        } catch (DuplicateEmailException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }

    /** Delete – POST /users/{id}/delete */
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        if (!userRepository.existsById(id)) {
            redirectAttrs.addFlashAttribute("error", "User not found");
            return "redirect:/";
        }
        userRepository.deleteById(id);
        redirectAttrs.addFlashAttribute("success", "User deleted");
        return "redirect:/";
    }
}
