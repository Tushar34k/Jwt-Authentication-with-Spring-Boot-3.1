package com.basic.auth.impl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.auth.impl.model.JwtRequest;
import com.basic.auth.impl.model.JwtResponse;
import com.basic.auth.impl.security.JwtHelper;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService; // Custom user service for loading user data

    @Autowired
    private AuthenticationManager manager; // AuthenticationManager to authenticate the user

    @Autowired
    private JwtHelper helper; // Helper class to generate JWT tokens

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        // Validate and authenticate the user
        this.doAuthenticate(request.getEmail(), request.getPassword());

        // Load user details after authentication
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        // Generate JWT token
        String token = this.helper.generateToken(userDetails);

        // Create response with the JWT token
        JwtResponse response = new JwtResponse(token, userDetails.getUsername());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        try {
            // Attempt authentication
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, password);
            manager.authenticate(authenticationToken);
        } catch (BadCredentialsException ex) {
            // Explicitly throw exception for incorrect credentials
            throw new BadCredentialsException("Invalid email or password");
        }
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
}
