package org.project.unitech.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.unitech.dto.LoginDTO;
import org.project.unitech.dto.UserDTO;
import org.project.unitech.dto.response.AuthResponse;
import org.project.unitech.dto.response.MessageResponse;
import org.project.unitech.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        return ResponseEntity.ok().body(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {
        String accessToken;
        try {
            accessToken = userService.authenticateUser(loginDTO);
        } catch (Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new MessageResponse("PIN or Password is incorrect!"));
        }
        return ResponseEntity.ok().body(new AuthResponse("User authenticated successfully!", accessToken));
    }
}
