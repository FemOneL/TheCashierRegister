package com.epam.cashierregister.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Class for sending emails
 */
public class EmailSenderService {
    static Logger LOG = LogManager.getLogger(EmailSenderService.class);
    private final String email = "tfemyak@gmail.com";
    private final String password = "your_app_password";

    /**
     * send email with new password in recipient email
     *
     * @param recipientEmail
     * @param newPassword
     * @throws MessagingException
     */
    public void sendPassword(String recipientEmail, String newPassword) throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true); //TLS
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        prop.put("mail.pop3s.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email));

        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Password changed");
        String msg = "Your new password: " + newPassword;
        message.setText(msg);
        LOG.debug("try to send email to {}", recipientEmail);
        Transport.send(message);
        LOG.info("sending successfully!!");

    }
}
