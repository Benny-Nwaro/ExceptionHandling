package com.example.lms.socialMedia;

import com.example.lms.users.UserEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "social_media_profiles")
public class SocialMediaProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String platform;
    private String profileUrl;

    public SocialMediaProfileEntity() {
    }

    public SocialMediaProfileEntity(UUID id, UserEntity user, String platform, String profileUrl) {
        this.id = id;
        this.user = user;
        this.platform = platform;
        this.profileUrl = profileUrl;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}

