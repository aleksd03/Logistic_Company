package org.informatics.service;

import org.informatics.dao.UserDao;
import org.informatics.entity.Role;
import org.informatics.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;
import java.util.regex.Pattern;

public class AuthService {
    private final UserDao users = new UserDao();
    private static final Pattern EMAIL_RX =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    public User register(String firstName, String lastName, String email, String rawPassword, Role role) {

        if (isBlank(firstName) || isBlank(lastName) || isBlank(email) || isBlank(rawPassword) || role == null) {
            throw new IllegalArgumentException("All fields are required.");
        }
        email = email.trim().toLowerCase();
        if (!EMAIL_RX.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (rawPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters.");
        }

        if (users.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        String hash = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        User user = new User();
        user.setFirstName(firstName.trim());
        user.setLastName(lastName.trim());
        user.setEmail(email);
        user.setPasswordHash(hash);
        user.setRole(role);
        users.save(user);

        return user;
    }

    public Optional<User> login(String email, String rawPassword) {
        if (email == null || rawPassword == null) return Optional.empty();
        email = email.trim().toLowerCase();

        var opt = users.findByEmail(email);
        if (opt.isEmpty()) return Optional.empty();

        var user = opt.get();

        if (!user.isActive()) return Optional.empty();

        boolean ok = BCrypt.checkpw(rawPassword, user.getPasswordHash());
        return ok ? Optional.of(user) : Optional.empty();
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}

