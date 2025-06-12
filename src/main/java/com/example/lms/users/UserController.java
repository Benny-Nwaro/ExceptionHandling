package com.example.lms.users;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/by-email")
    public ResponseEntity <UserDTO> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> updateUserProfile(
            @PathVariable UUID userId,
            @RequestBody UserDTO updatedUser) {

        UserDTO userDTO = userService.updateUserProfile(userId, updatedUser);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserEntity user) {
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @PostMapping("/{userId}/uploadProfileImage")
    public ResponseEntity<UserDTO> uploadProfileImage(
            @PathVariable UUID userId,
            @RequestParam("file") MultipartFile file) {
        try {
            UserDTO userDto = userService.uploadProfileImage(userId, file);
            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}/profileImage")
    public ResponseEntity<String> getProfileImage(@PathVariable UUID userId) {
        String imageUrl = userService.getProfileImageUrl(userId);
        return (imageUrl != null) ? ResponseEntity.ok(imageUrl) : ResponseEntity.notFound().build();
    }

    @GetMapping("/instructors")
    public ResponseEntity<List<UserDTO>> getAllInstructors() {
        List<UserDTO> instructors = userService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }
}
