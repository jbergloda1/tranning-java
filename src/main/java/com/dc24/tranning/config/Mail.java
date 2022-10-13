package com.dc24.tranning.config;


import java.io.UnsupportedEncodingException;

import java.util.Date;
import java.util.Properties;

import javax.mail.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.dc24.tranning.constant.MailConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Mail {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        Message msg = new MimeMessage(setSession(setProperties()));
        msg.setSentDate(new Date());
        msg.setSubject("Here's the link to reset your password");
        msg.setFrom(new InternetAddress(MailConstant.Project_EMAIL, false));
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
                return new PasswordAuthentication(MailConstant.MY_EMAIL, MailConstant.MY_PASSWORD);
            }
        });
        return session;
    }

    private Properties setProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.port", MailConstant.PORT);
        props.put("mail.smtp.host", MailConstant.HOST);
        props.put("mail.smtp.auth", MailConstant.AUTH);
        props.put("mail.smtp.starttls.enable", MailConstant.STARTTLS);

        return props;
    }
}
