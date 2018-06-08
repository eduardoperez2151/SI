package uy.edu.ucu.si.grupo8.obligatorio3.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.ucu.si.grupo8.obligatorio3.models.User;
import uy.edu.ucu.si.grupo8.obligatorio3.repositories.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    private UserRepository userService;

    public UserController(UserRepository userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<User> getUsers() {
        return userService.findAll();
    }
}