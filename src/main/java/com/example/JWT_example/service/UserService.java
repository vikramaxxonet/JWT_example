package com.example.JWT_example.service;

import com.example.JWT_example.entity.User;
import com.example.JWT_example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "Username is already taken.";
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);
        return "User registered successfully.";
    }

    public User authenticate(String username, String userPassword) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent() && passwordEncoder.matches(userPassword, existingUser.get().getPassword())) {
            return existingUser.get();
        }
        return null;
    }

}
