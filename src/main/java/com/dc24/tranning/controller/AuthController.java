package com.dc24.tranning.controller;

import com.dc24.tranning.config.JwtConfig.JwtTokenUtil;
import com.dc24.tranning.dto.JwtDto.JwtRequest;
import com.dc24.tranning.dto.JwtDto.JwtResponse;
import com.dc24.tranning.entity.UsersEntity;
import com.dc24.tranning.repository.UserRepository;
import com.dc24.tranning.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
@CrossOrigin(origins = { "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        UsersEntity user = userRepository.checkEnabled(authenticationRequest.getUsername());
        if(user.getEnabled() || user.getEnabled() == null){
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            return new ResponseEntity("User Disabled", HttpStatus.NOT_ACCEPTABLE);
        }




    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception(e.getMessage());
        } catch (BadCredentialsException e) {
            throw new Exception(e.getMessage());
        }
    }
}
