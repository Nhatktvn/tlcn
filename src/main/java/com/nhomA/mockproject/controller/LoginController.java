package com.nhomA.mockproject.controller;

import com.nhomA.mockproject.dto.LoginDTO;
import com.nhomA.mockproject.util.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/login")
@RestController
@CrossOrigin
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public LoginController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        //Authenticate
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());
        //Generate JWT TOKEN
        final String token = JwtUtils.generateToken(userDetails.getUsername());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
