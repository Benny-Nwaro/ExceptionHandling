package com.example.lms.users;

import com.example.lms.exceptions.ErrorResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                ()-> new ErrorResponse.NoSuchUserExistsException("User does not exist"));
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                ()-> new ErrorResponse.NoSuchUserExistsException("User does not exist"));
    }

    public Optional<UserEntity> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public UserEntity saveUser(UserEntity user) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new ErrorResponse.UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }

        return userRepository.save(user);
    }


    public void deleteUser(Long id) {
        UserEntity existingUser = userRepository.findById(id).orElseThrow(
                () -> new ErrorResponse.NoSuchUserExistsException("User does not exist"));
        userRepository.deleteById(id);
    }
}

