package com.example.lms.users;

import com.example.lms.exceptions.ErrorResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getAllUsers() {
        List<UserEntity> users =  userRepository.findAll();
        return users.stream().map(UserMapper :: toDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
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

    public UserDTO saveUser(UserEntity user) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new ErrorResponse.UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }


    public void deleteUser(Long id) {
        UserEntity existingUser = userRepository.findById(id).orElseThrow(
                () -> new ErrorResponse.NoSuchUserExistsException("User does not exist"));
        userRepository.deleteById(id);
    }
}

