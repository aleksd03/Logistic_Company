package org.informatics;

import org.informatics.service.AuthService;

public class Main {
    public static void main(String[] args) {
        var auth = new AuthService();

        auth.login("ivan123@asd.com", "ivan123456")
                .ifPresentOrElse(
                        user -> System.out.println("Login OK: " + user.getEmail() + " (" + user.getRole() + ")"),
                        () -> System.out.println("Invalid email or password")
                );





//        AuthService authService = new AuthService();
//
//        try {
//            User newUser = authService.register(
//                    "Peter",
//                    "Nicolov",
//                    "peter123@asd.com",
//                    "peter123456",
//                    Role.EMPLOYEE
//            );
//
//            System.out.println("Successfully registered: " + newUser);
//
//        } catch (IllegalArgumentException exception) {
//            System.out.println("Registration failed: " + exception.getMessage());
//        }



//        UserDao repository = new UserDao();
//
//        String email = "peter123@asd.com";
//        String rawPassword = "peter123456";
//        String hash = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
//
//        User user = new User();
//        user.setFirstName("Peter");
//        user.setLastName("Nicolov");
//        user.setEmail(email);
//        user.setPasswordHash(hash);
//        user.setRole(Role.EMPLOYEE);
//
//        repository.save(user);
//        System.out.println("Saved user: " + email);
//
//        repository.findByEmail(email).ifPresentOrElse(
//                found -> System.out.println("Found: " + found + " " + found.getLastName() + " (" + found.getRole() + ")"),
//                () -> System.out.println("Not found: " + email)
//        );
    }
}
