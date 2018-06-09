package uy.edu.ucu.si.grupo8.obligatorio3.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uy.edu.ucu.si.grupo8.obligatorio3.dtos.ResponseAPI;
import uy.edu.ucu.si.grupo8.obligatorio3.dtos.UserDTO;
import uy.edu.ucu.si.grupo8.obligatorio3.services.UserService;
import uy.edu.ucu.si.grupo8.obligatorio3.transport.PasswordPawnedRestClient;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;
    private final PasswordPawnedRestClient passwordPawnedRestClient;

    public UserController(final UserService userService, final PasswordPawnedRestClient passwordPawnedRestClient) {
        this.userService = userService;
        this.passwordPawnedRestClient = passwordPawnedRestClient;
    }

    @GetMapping(value = "/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseAPI> getAllUsers() throws URISyntaxException {

        passwordPawnedRestClient.a();
        final List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok(ResponseAPI.ok(users));
    }

    @PostMapping(value = "/")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseAPI> signUp(@RequestBody final UserDTO user) {
        final UserDTO createdUser = userService.singUp(user);
        return ResponseEntity.ok(ResponseAPI.ok(createdUser));
    }


    @PatchMapping(value = "/")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseAPI> UpdateUser(@RequestBody final UserDTO user) {
        final UserDTO createdUser = userService.singUp(user);
        return ResponseEntity.ok(ResponseAPI.ok(createdUser));
    }
}