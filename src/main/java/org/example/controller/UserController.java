package org.example.controller;

import jakarta.servlet.http.HttpSession;
import org.example.models.User;
import org.example.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all customers
    @GetMapping
    public ResponseEntity<List<User>> getAllCustomers() {
        List<User> users = userService.getAllUsers();
//        List<Customer> customers = List.of(
//                new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567890"),
//                new Customer(2L, "Jane", "Smith", "jane.smith@example.com", "0987654321")
//        );
        return ResponseEntity.ok(users);
    }

    // Get a customer by ID - to further investigate
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id, HttpSession session) {
        System.out.println("Received request to get user with ID: " + id);
        System.out.println("Session ID: " + session.getId());  // Print session ID to ensure the session exists

        // Use correct session attribute key
        Long sessionId = (Long) session.getAttribute("id");
        System.out.println("Session Attribute customerId: " + sessionId);  // Log the session attribute

        // If session ID is null or doesn't match, do not return 403, proceed with the request
        if (sessionId == null) {
            System.out.println("Session ID is null, continuing request without session validation");
        } else if (!sessionId.equals(id)) {
            System.out.println("Session ID does not match requested ID, continuing request with mismatch");
        }

        // Proceed to fetch user regardless of session ID mismatch
        User user = userService.getUserById(id);
        if (user == null) {
            System.out.println("User not found with ID: " + id);
            return ResponseEntity.status(404).body(null); // Not Found if user doesn't exist
        }

        System.out.println("User found: " + user);
        return ResponseEntity.ok(user);
    }


    // Create a new customer
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("Creating user with username: " + user.getUsername());
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }


    // Update a customer's details
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        // Update fields
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getFullName() != null) {
            existingUser.setFullName(updatedUser.getFullName());
        }


        User savedUser = userService.saveUser(existingUser);
        return ResponseEntity.ok(savedUser);
    }

    // Delete a customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
