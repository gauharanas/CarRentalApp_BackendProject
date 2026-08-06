package com.gauharanas.car_rental_backend.service;

import com.gauharanas.car_rental_backend.dto.SignupRequest;
import com.gauharanas.car_rental_backend.model.User;
import com.gauharanas.car_rental_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
    }

    public User createUser(SignupRequest signupRequest) {
        // You can add a check here to see if a user with the email already exists
        User user = new User();
        user.setName(signupRequest.name());
        user.setEmail(signupRequest.email());
        user.setRole(signupRequest.role());
        user.setPassword(passwordEncoder.encode(signupRequest.password())); // Hash the password
        return userRepository.save(user);
    }

}
