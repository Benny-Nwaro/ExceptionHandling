package com.example.lms.socialMedia;

import com.example.lms.users.UserEntity;
import com.example.lms.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SocialMediaProfileService {
    @Autowired
    private SocialMediaProfileRepository repository;
    @Autowired
    private UserRepository userRepository;

    public SocialMediaProfileEntity addProfile(UUID userId, SocialMediaProfileDTO dto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        SocialMediaProfileEntity profile = new SocialMediaProfileEntity();
        profile.setUser(user);
        profile.setPlatform(dto.getPlatform());
        profile.setProfileUrl(dto.getProfileUrl());

        return repository.save(profile);
    }

    public List<SocialMediaProfileEntity> getProfilesByUser(UUID userId) {
        return repository.findByUserId(userId);
    }
}

