package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.NewUserValidator;

import javax.validation.Valid;

@Controller
public class AdminsController {
    private final UserService userService;
    private final RoleService roleService;
    private final NewUserValidator newUserValidator;

    @Autowired
    public AdminsController(UserService userService, RoleService roleService, NewUserValidator newUserValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.newUserValidator = newUserValidator;
    }

    @GetMapping("/admin/user")
    public String showAllUsers(Model model) {
        model.addAttribute("user", userService.findAll());
        return "show_users";
    }

    @GetMapping("/admin/{id}")
    public String showCurrentUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "show_user";
    }

    @GetMapping("/admin/create-new-user")
    public String showUserCreationPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", roleService.getRoles());
        return "create_new_user";
    }

    @PostMapping("/admin/user")
    public String saveNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        newUserValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "create_new_user";
        }
        userService.save(user);
        return "redirect:user";
    }

    @GetMapping("/admin/{id}/edit")
    public String showUserUpdatePage(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("role", roleService.getRoles());
        return "edit_user";
    }

    @PatchMapping("/admin/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit_user";
        }
        userService.update(user);
        return "redirect:user";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:user";
    }
}
