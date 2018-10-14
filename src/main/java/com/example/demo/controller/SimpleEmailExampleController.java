package com.example.demo.controller;

import com.example.demo.email.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleEmailExampleController {

    @Autowired
    public JavaMailSender emailSender;

    @ResponseBody
    @RequestMapping("/sendSimpleEmail")
    public String sendSimpleEmail() {

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(MyConstants.FRIEND_EMAIL);
        message.setSubject("Test Simple Email");
        message.setText("Hello, Im testing Simple Email");

        // Send Message!

        this.emailSender.send(message);
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setTo(email);
//        message.setSubject("We Are East2WestTours and Travels");
//        message.setText("Hello, You are register new account success ! Welcome to East2WestTours and Travels");
//        this.emailSender.send(message);

        return "Email Sent!";
    }

}
