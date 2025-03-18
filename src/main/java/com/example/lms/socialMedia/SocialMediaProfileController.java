package com.example.lms.socialMedia;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/social-media")
public class SocialMediaProfileController {

    private final SocialMediaProfileService service;

    public SocialMediaProfileController(SocialMediaProfileService service) {
        this.service = service;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<SocialMediaProfileEntity> addProfile(
            @PathVariable UUID userId, @RequestBody SocialMediaProfileDTO dto) {
        return ResponseEntity.ok(service.addProfile(userId, dto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SocialMediaProfileEntity>> getProfiles(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getProfilesByUser(userId));
    }
}
