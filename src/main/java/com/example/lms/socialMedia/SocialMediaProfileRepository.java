package com.example.lms.socialMedia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SocialMediaProfileRepository extends JpaRepository<SocialMediaProfileEntity, UUID> {
    List<SocialMediaProfileEntity> findByUserId(UUID userId);
}

