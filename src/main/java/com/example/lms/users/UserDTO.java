package com.example.lms.users;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String profileBio;
    private String gender;
    private String phoneNumber;
    private String dateOfBirth;
    @JsonIgnore
    private String password;

    public UserDTO() {
    }

    // Constructor
    public UserDTO(UUID id, String firstName, String lastName, String email, String role,
                   String profileBio, String gender, String phoneNumber, String dateOfBirth, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.profileBio = profileBio;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

    // Getters & Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getProfileBio() {
        return profileBio;
    }
    public void setProfileBio(String profileBio) {
        this.profileBio = profileBio;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

