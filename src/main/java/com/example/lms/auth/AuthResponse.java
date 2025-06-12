package com.example.lms.auth;

/**
 * Record representing the authentication response, containing the JWT token.
 *
 * @param token The generated JWT.
 */
record AuthResponse(String token) {
}
