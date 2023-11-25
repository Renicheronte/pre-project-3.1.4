package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
public class AdminsController {
    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        model.addAttribute("user", userService.findOne());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("allRoles", roleService.getRoles());
        return "admin";
    }

    @GetMapping("/admin/user/{id}/edit")
    public String showCurrentUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("allRoles", roleService.getRoles());
        return "user";
    }

    @PatchMapping("/admin/user/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/new")
    public String showUserCreationPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getRoles());
        return "admin";
    }

    @PostMapping("/admin/user")
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/user/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
