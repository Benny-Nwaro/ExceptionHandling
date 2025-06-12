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
                user.getGender(),
                user.getPhoneNumber(),
                user.getDateOfBirth(),
                user.getProfileImageUrl(),
                user.getPassword()
        );
    }

    public static UserEntity toEntity(UserDTO dto) {
        UserEntity user = new UserEntity();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setRole(Role.valueOf(dto.getRole())); // Assuming 'Role' is an enum
        user.setProfileBio(dto.getProfileBio());
        user.setGender(dto.getGender());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setProfileImageUrl(dto.getProfileImageUrl());
        user.setPassword(dto.getPassword());

        return user;
    }
}
