package com.dc24.tranning.controller;

import com.dc24.tranning.config.Mail;
import com.dc24.tranning.dto.ForgotPasswordDTO;
import com.dc24.tranning.dto.ResetPasswordDTO;
import com.dc24.tranning.entity.UsersEntity;
import com.dc24.tranning.service.CustomUserDetailsService;
import com.dc24.tranning.service.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@CrossOrigin(origins = { "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1")
public class ForgotPasswordController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/reset_password")
    public ResponseEntity<?> showResetPasswordForm(@Param(value = "token") String token, Model model) {
        UsersEntity user = customUserDetailsService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return new ResponseEntity<>(model.getAttribute("message"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> processForgotPassword(HttpServletRequest request, @RequestParam(name = "email") String email, Model model) {
        String token = RandomString.make(30);
        try {
            customUserDetailsService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            Mail mailer = new Mail();
            mailer.sendEmail("Here's the link to reset your password", email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
            return new ResponseEntity<>(model.getAttribute("error"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
            return new ResponseEntity<>(model.getAttribute("error"), HttpStatus.BAD_GATEWAY);
        }

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    @PostMapping("/reset_password")
    public ResponseEntity<?> processResetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO, HttpServletRequest request, Model model) {
        UsersEntity token = customUserDetailsService.getByResetPasswordToken(resetPasswordDTO.getToken());
        String createPassword = resetPasswordDTO.getCreatePassword();
        String confirmPassword = resetPasswordDTO.getConfirmPassword();
        model.addAttribute("title", "Reset your password");

        if(!(Arrays.equals(createPassword.toCharArray(), confirmPassword.toCharArray()))){
            model.addAttribute("message", "Passwords do not match.");
            return new ResponseEntity<>(model.getAttribute("message"), HttpStatus.BAD_REQUEST);
        }

        if (token == null) {
            model.addAttribute("message", "Invalid Token");
            return new ResponseEntity<>(model.getAttribute("message"), HttpStatus.BAD_REQUEST);
        } else {
            customUserDetailsService.updatePassword(token, resetPasswordDTO.getCreatePassword());
            model.addAttribute("message", "You have successfully changed your password.");
        }

        return new ResponseEntity<>(model.getAttribute("message"), HttpStatus.OK);
    }

}
