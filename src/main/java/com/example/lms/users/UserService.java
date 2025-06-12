package com.example.lms.users;

import java.io.IOException;
import com.example.lms.exceptions.ErrorResponse;
import com.example.lms.security.CloudinaryService;
import com.example.lms.utils.FileStorageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;
    private final CloudinaryService cloudinaryService;



    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder,
                       FileStorageService fileStorageService, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
        this.cloudinaryService = cloudinaryService;

    }

    public List<UserDTO> getAllUsers() {
        List<UserEntity> users =  userRepository.findAll();
        return users.stream().map(UserMapper :: toDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                ()-> new ErrorResponse.NoSuchUserExistsException("User does not exist"));
        return UserMapper.toDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(UserMapper::toDTO).orElseThrow(
                ()-> new ErrorResponse.NoSuchUserExistsException("User does not exist"));
    }

    public Optional<UserDTO> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).map(UserMapper::toDTO);
    }
    @Override
    public UserEntity loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public List<UserDTO> getAllInstructors() {
        List<UserEntity> instructors = userRepository.findByRole(Role.INSTRUCTOR);
        return instructors.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO saveUser(UserEntity user) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new ErrorResponse.UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }

    @Transactional
    public UserDTO updateUserProfile(UUID userId, UserDTO updatedUser) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found.");
        }

        UserEntity user = optionalUser.get();

        // Update user details
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setProfileBio(updatedUser.getProfileBio());

        if (updatedUser.getGender() != null) {
            user.setGender(updatedUser.getGender());
        }

        user.setPhoneNumber(updatedUser.getPhoneNumber());

        if (updatedUser.getDateOfBirth() != null) {
            user.setDateOfBirth(updatedUser.getDateOfBirth());
        }

        user.setProfileImageUrl(updatedUser.getProfileImageUrl());

        // Save updated user
        userRepository.save(user);

        return UserMapper.toDTO(user);
    }

    public UserDTO uploadProfileImage(UUID userId, MultipartFile file) throws IOException {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        UserEntity user = userOpt.get();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String folder = "users/" + userId + "/profile";

        String imageUrl = cloudinaryService.uploadFile(file, folder, fileName);
        user.setProfileImageUrl(imageUrl);
        userRepository.save(user);

        return UserMapper.toDTO(user);
    }

    public String getProfileImageUrl(UUID userId) {
        return userRepository.findById(userId)
                .map(UserEntity::getProfileImageUrl)
                .orElse(null);
    }


    public void deleteUser(UUID id) {
        UserEntity existingUser = userRepository.findById(id).orElseThrow(
                () -> new ErrorResponse.NoSuchUserExistsException("User does not exist"));
        userRepository.deleteById(id);
    }
}

