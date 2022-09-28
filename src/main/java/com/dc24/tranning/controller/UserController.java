package com.dc24.tranning.controller;


import com.dc24.tranning.dto.SignUpDto;
import com.dc24.tranning.dto.UserDTO.UserDTO;
import com.dc24.tranning.entity.RolesEntity;
import com.dc24.tranning.entity.UsersEntity;
import com.dc24.tranning.repository.RoleRepository;
import com.dc24.tranning.repository.UserRepository;
import com.dc24.tranning.service.CustomUserDetailsService;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.dc24.tranning.Utils.MergingLists.mergeList;
import static com.dc24.tranning.Utils.MergingObjects.mergeObject;


@CrossOrigin(origins = { "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private CustomUserDetailsService userDetailsService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("process success getAll");
        try {
            logger.info("User Info");
            List<UsersEntity> user = userDetailsService.getAllUsers();
            List<UserDTO> userDtoList = mergeList(user, UserDTO.class);
            return new ResponseEntity<>(userDtoList, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersEntity> findUserById(@PathVariable int id) {
        logger.info("logging process findOne");
        try {
            logger.info("id user info");
            UsersEntity user = userDetailsService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@NotNull @RequestBody SignUpDto signUpDto){
        logger.info("logging process register");
        try {

            if(userRepository.existsByUsername(signUpDto.getUsername())){
                return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
            }
            if(userRepository.existsByEmail(signUpDto.getEmail())){
                return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
            }

            UsersEntity user = new UsersEntity();
            user.setUsername(signUpDto.getUsername());
            user.setEmail(signUpDto.getEmail());
            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            RolesEntity roles = roleRepository.findByName("ROLE_SUBSCRIBER").get();
            user.setRoles(Collections.singleton(roles));

            userRepository.save(user);

            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> updateUser(@RequestBody UsersEntity user)
    {
        logger.info("logging process update");
        try {
            logger.info("update user info");
            return new ResponseEntity<>(userDetailsService.updateUser(user), HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error("error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        return userDetailsService.deleteUser(id);
    }
}