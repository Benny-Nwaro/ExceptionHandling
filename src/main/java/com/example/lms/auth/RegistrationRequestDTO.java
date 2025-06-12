package com.example.lms.auth;

import com.example.lms.users.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for user registration requests.
 * Encapsulates the information required from a user during the registration process.
 */
public class RegistrationRequestDTO {

    /**
     * The first name of the user.
     * Must not be blank.
     */
    @NotBlank(message = "First name is required")
    private String firstName;

    /**
     * The last name of the user.
     * Must not be blank.
     */
    @NotBlank(message = "Last name is required")
    private String lastName;

    /**
     * The email address of the user.
     * Must not be blank and must be a valid email format.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    /**
     * The password chosen by the user.
     * Must not be blank and must be at least 6 characters long.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    /**
     * The role assigned to the user during registration.
     * Defaults to {@link Role#STUDENT} if not explicitly provided.
     */
    private Role role;

    /**
     * The phone number of the user.
     * Must not be blank and must match the specified phone number format (10-15 digits).
     */
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;

    // Default constructor
    public RegistrationRequestDTO() {
        this.role = Role.STUDENT;
    }

    // Parameterized constructor
    public RegistrationRequestDTO(String firstName, String lastName, String email, String password, Role role, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
