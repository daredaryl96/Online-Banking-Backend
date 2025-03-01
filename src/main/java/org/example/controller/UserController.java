package org.example.controller;

import org.example.models.User;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Get a customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
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
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
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
