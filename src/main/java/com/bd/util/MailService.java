/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bd.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author SUMAN
 */
public class MailService {
private JavaMailSenderImpl mailSender;
public void setMailSender(JavaMailSenderImpl mailSender){
mailSender = mailSender;
}
public void sendMail(String from, String to, String subject, String msg){
SimpleMailMessage message = new SimpleMailMessage();
message.setFrom(from);
message.setTo(to);
message.setSubject(subject);
message.setText(msg);
mailSender.send(message);
}
}
