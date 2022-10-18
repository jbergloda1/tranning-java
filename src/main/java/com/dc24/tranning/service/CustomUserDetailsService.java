package com.dc24.tranning.service;

import com.dc24.tranning.config.Mail;

import com.dc24.tranning.dto.SignUpDto;
import com.dc24.tranning.entity.RolesEntity;
import com.dc24.tranning.entity.UsersEntity;
import com.dc24.tranning.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    private UsersEntity user;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UsersEntity user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    public List<UsersEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UsersEntity getUserById(int id) {
        return userRepository.findUserById(id);
    }

    public UsersEntity getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void sendVerificationEmail(UsersEntity user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
        Mail mailer = new Mail();
        mailer.sendEmail("Here's the link to verify your account", toAddress, verifyURL);
    }
    public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    public boolean verifyRegister(String verificationCode) {
        UsersEntity user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.getEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }

    }

    public UsersEntity updateUser(UsersEntity user) {
        UsersEntity existing_user = userRepository.findUserById(user.getId());
        existing_user.setEmail(user.getEmail());
        existing_user.setUsername(user.getUsername());
        return userRepository.save(existing_user);
    }

    public String deleteUser(int id) {
        userRepository.deleteById(id);
        return " removed user " + id  + " completed";
    }



    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<RolesEntity> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        UsersEntity user = userRepository.findUserByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any user with the email " + email);
        }
    }

    public UsersEntity getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(UsersEntity user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
