package com.dc24.tranning.config;


import java.io.UnsupportedEncodingException;

import java.util.Date;
import java.util.Properties;

import javax.mail.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Mail {

    @Autowired
    private JavaMailSender mailSender;
    private final String PORT = "587";
    private final String HOST = "smtp.mailtrap.io";
    private final String USERNAME = "a48385f1f308d4";
    private final String PASSWORD = "60349a3ee49c87";
    private final String EMAIL = "a48385f1f308d4-60349a3ee49c87@inbox.mailtrap.io";

    private final boolean AUTH = true;
    private final boolean STARTTLS = true;


    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        Message msg = new MimeMessage(setSession(setProperties()));
        msg.setSentDate(new Date());
        msg.setSubject("Here's the link to reset your password");
        msg.setFrom(new InternetAddress(EMAIL, false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

        msg.setContent(
                "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>", "text/html");



        Transport.send(msg);
    }

    private Session setSession(Properties props) {
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
        return session;
    }

    private Properties setProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", STARTTLS);

        return props;
    }
}
