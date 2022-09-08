package com.dc24.tranning.controller;


import com.dc24.tranning.entity.UsersEntity;
import com.dc24.tranning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = { "http://localhost:3000"})
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    private UsersEntity getCurrentUser(@RequestBody UsersEntity user) {
        return userService.getUser(user);
    }

    @GetMapping("/login/{username}/{password}")
    private boolean findUserByUsername(@PathVariable String username, @PathVariable String password) {
        return userService.getUserByUsername(username, password);
    }

    @PostMapping("/createUser")
    private boolean addUser(@RequestBody UsersEntity user) {
        boolean user_exits = userService.findUserByUsername(user.getUsername());
        if(user_exits) {
            return false;
        }
        userService.saveUser(user);
        return true;
    }
}
