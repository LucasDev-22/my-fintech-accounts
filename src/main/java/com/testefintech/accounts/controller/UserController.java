package com.testefintech.accounts.controller;

import com.testefintech.accounts.dto.UserUpdateRequest;
import com.testefintech.accounts.model.User;
import com.testefintech.accounts.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestParam String email, @RequestBody UserUpdateRequest request) {
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (request.name() != null && !request.name().isEmpty()) {
            user.setName(request.name());
        }
        if (request.phone() != null && !request.phone().isEmpty()) {
            user.setPhone(request.phone());
        }
        if (request.profileImage() != null && !request.profileImage().isEmpty()) {
            user.setProfileImage(request.profileImage());
        }

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUser(@RequestParam String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return ResponseEntity.ok(user);
    }
}
