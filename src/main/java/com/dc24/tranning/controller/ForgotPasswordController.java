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
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@CrossOrigin(origins = { "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1")
public class ForgotPasswordController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        UsersEntity user = customUserDetailsService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> processForgotPassword(HttpServletRequest request, @RequestBody ForgotPasswordDTO emailDTO, Model model) {
        String email = emailDTO.getEmail();
        String token = RandomString.make(30);

        try {
            customUserDetailsService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            Mail mailer = new Mail();
            mailer.sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return new ResponseEntity<>(model.getAttribute("message"), HttpStatus.OK);
    }
    @PostMapping("/reset_password")
    public String processResetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO, HttpServletRequest request, Model model) {
        UsersEntity token = customUserDetailsService.getByResetPasswordToken(resetPasswordDTO.getToken());
        String password = resetPasswordDTO.getPassword();
        model.addAttribute("title", "Reset your password");

        if (token == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            customUserDetailsService.updatePassword(token, password);
            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";
    }

}
