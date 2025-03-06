package com.example.lms.users;

public class UserMapper {
    public static UserDTO toDTO(UserEntity user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().toString(),
                user.getProfileBio(),
                user.getGender().name(),
                user.getPhoneNumber(),
                user.getDateOfBirth().toString(),
                user.getPassword()
        );
    }
}

