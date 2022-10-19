package com.dc24.tranning.service;

import com.dc24.tranning.config.Mail;

import com.dc24.tranning.dto.UserDTO.UserDTO;
import com.dc24.tranning.entity.RolesEntity;
import com.dc24.tranning.entity.UsersEntity;
import com.dc24.tranning.repository.RoleRepository;
import com.dc24.tranning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RoleRepository roleRepository;

    private UserRepository userRepository;


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

    public UsersEntity updateUser(UserDTO user) {
        UsersEntity existing_user = userRepository.findUserById(user.getId());
        existing_user.setUsername(user.getUsername());
        existing_user.setEmail(user.getEmail());
        existing_user.setName(user.getName());
        RolesEntity roles = roleRepository.findByName("ROLE_SUBSCRIBER").get();
        existing_user.setRoles(Collections.singleton(roles));
        existing_user.setCompany(user.getCompany());

        return userRepository.save(existing_user);
    }

    public List<UsersEntity> updateUserInfo(UserDTO user){
        UsersEntity existing_user = userRepository.findUserById(user.getId());
        existing_user.setIntroduction(user.getIntroduction());
        existing_user.setBirthday(user.getBirthday());
        existing_user.setPhone(user.getPhone());
        existing_user.setCountry(user.getCountry());
        existing_user.setLanguage(user.getLanguage());
        existing_user.setGender(user.getGender());
        existing_user.setWebsite(user.getWebsite());
        existing_user.setName(user.getName());
        existing_user.setCompany(user.getCompany());
        RolesEntity roles = roleRepository.findByName("ROLE_SUBSCRIBER").get();
        existing_user.setRoles(Collections.singleton(roles));

        return Collections.singletonList(userRepository.save(existing_user));
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
