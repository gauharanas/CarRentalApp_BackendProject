package com.gauharanas.car_rental_backend.controller;

import com.gauharanas.car_rental_backend.dto.AuthenticationRequest;
import com.gauharanas.car_rental_backend.dto.AuthenticationResponse;
import com.gauharanas.car_rental_backend.dto.SignupRequest;
import com.gauharanas.car_rental_backend.service.AuthService;
import com.gauharanas.car_rental_backend.service.CustomUserDetailsService;
import com.gauharanas.car_rental_backend.service.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@RequestBody SignupRequest signupRequest){
        authService.createUser(signupRequest);
        return new ResponseEntity<>("User Signup Successfull", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createAuthennticationToken(@RequestBody AuthenticationRequest authRequest){
        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(authRequest.email(),authRequest.password()));
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.email());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
