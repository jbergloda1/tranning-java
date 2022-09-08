package com.dc24.tranning.service;

import com.dc24.tranning.entity.UsersEntity;
import com.dc24.tranning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public UsersEntity getUser(UsersEntity user) {
        UsersEntity userEntity = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        return userEntity;
    }
    public boolean getUserByUsername(String username, String password) {
        boolean username_present;
        boolean password_present;
        try {
            username_present = userRepository.findTopByUsername(username) != null ? true : false;
            password_present = userRepository.findTopByPassword(password) != null ? true : false;
        } catch(NonUniqueResultException nre) {
            return true;
        }
        return username_present && password_present;
    }

    public boolean findUserByUsername(String username) {
        boolean username_present;
        try {
            username_present = userRepository.findTopByUsername(username) != null ? true : false;
        } catch(NonUniqueResultException nre) {
            return true;
        }
        return username_present;
    }

    public void saveUser(UsersEntity user) {
        userRepository.save(user);
    }
}
