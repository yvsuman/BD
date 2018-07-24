/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bd.util;

import java.security.SecureRandom;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author SUMAN
 */
public class VerificationGenerator {
    Properties props = new Properties(); 
    final String from="bdusernoreply";
    final String password="";
    Session session;//Get the session object
   
    private void getAuthentication()
    {
        props.put("mail.smtp.host", "smtp"); 
		props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true"); 
        props.put("mail.smtp.port", "xx");     
        session = Session.getDefaultInstance(props,new javax.mail.Authenticator() { 
        @Override
        protected PasswordAuthentication getPasswordAuthentication() { 
        return new PasswordAuthentication(from,password); 
        } 
        });    
    }   
    
    public String sendPassword(String user,String email,String password) {
        getAuthentication();
        try { 
            MimeMessage message = new MimeMessage(session); 
            message.setFrom(new InternetAddress(from)); 
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(email)); 
            message.setSubject(" Details"); 
            message.setText(
            "Hello"+" " +user +"," + "\n\n"
                + "Please find your password details \n\n"
                +"Password:"+password +"\n\n"
                 +"Thank you" )
                ;
           Transport.send(message);
           return "Password sent to registered mail";
         

     } catch (MessagingException e) {throw new RuntimeException(e);} 
          
    }
    
}
