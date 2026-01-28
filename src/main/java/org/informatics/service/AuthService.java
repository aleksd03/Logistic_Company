package org.informatics.service;

import org.informatics.dao.UserDao;
import org.informatics.entity.enums.Role;
import org.informatics.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Service responsible for authentication and user registration.
 * Handles validation, password hashing, and login verification.
 */
public class AuthService {

    // DAO used for accessing User persistence layer
    private final UserDao users = new UserDao();

    // Simple email validation regex
    private static final Pattern EMAIL_RX =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    /**
     * Registers a new user in the system.
     * Performs validation, hashes the password, and persists the user.
     */
    public User register(String firstName, String lastName, String email, String rawPassword, Role role) {

        // Basic null / blank validation
        if (isBlank(firstName) || isBlank(lastName) || isBlank(email)
                || isBlank(rawPassword) || role == null) {
            throw new IllegalArgumentException("All fields are required.");
        }

        // Normalize email for consistent storage and lookup
        String normalizedEmail = email.trim().toLowerCase();
        if (!EMAIL_RX.matcher(normalizedEmail).matches()) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        // Enforce minimum password length
        if (rawPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters.");
        }

        // Ensure email uniqueness
        if (users.findByEmail(normalizedEmail).isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        // Hash password using BCrypt with cost factor 12
        String hash = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));

        // Create and populate User entity
        User u = new User();
        u.setFirstName(firstName.trim());
        u.setLastName(lastName.trim());
        u.setEmail(normalizedEmail);
        u.setPasswordHash(hash);
        u.setRole(role);
        u.setActive(true);

        // Persist user
        users.save(u);
        return u;
    }

    /**
     * Attempts to authenticate a user with email and password.
     * Returns Optional.empty() on failure.
     */
    public Optional<User> login(String email, String rawPassword) {

        // Reject blank credentials early
        if (isBlank(email) || isBlank(rawPassword)) {
            return Optional.empty();
        }

        // Normalize email before lookup
        String normalizedEmail = email.trim().toLowerCase();
        Optional<User> u = users.findByEmail(normalizedEmail);

        // Fail if user does not exist or is inactive
        if (u.isEmpty()) return Optional.empty();
        if (!u.get().isActive()) return Optional.empty();

        // Verify password hash
        boolean ok = BCrypt.checkpw(rawPassword, u.get().getPasswordHash());

        // Return user only if authentication succeeds
        return ok ? u : Optional.empty();
    }

    /**
     * Utility method for checking null or blank strings.
     */
    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}

