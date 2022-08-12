package com.premiernoobs.rakhbokoi.Class.Static;

import android.util.Log;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailStatic {

    private static String email = "rakhbo.koi123@gmail.com";
    private static String password = "yasin 123456789";
    private static String emailOtpSubject = "Verify Your Email Address";
    private static String emailOtpText = "Hello,<br>We're happy you signed up for our app. To start exploring the app & enjoy all the features, please confirm your email address.<br><br>Your Verification Code : ";

    public EmailStatic() {
    }

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }

    public static String getEmailOtpSubject() {
        return emailOtpSubject;
    }

    public static String getEmailOtpText() {
        return emailOtpText;
    }

    public boolean sendEmail(String email, String subject, String text){

        boolean isSuccessful = false;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                new Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(getEmail(), getPassword());
                    }
                });

        try{

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(getEmail()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(text);
            message.setContent(text, "text/html; charset=utf-8");
            Transport.send(message);

            isSuccessful = true;

        }catch (MessagingException e){
            // Invalid Addresses
            Log.d("Verify", e.getMessage());
        }

        return isSuccessful;

    }

}
