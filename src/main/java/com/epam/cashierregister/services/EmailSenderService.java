package com.epam.cashierregister.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Class for sending emails
 */
public class EmailSenderService {
    static Logger LOG = LogManager.getLogger(EmailSenderService.class);
    private static final String EMAIL = "tfemyak@gmail.com";
    private static final String PASSWORD = "arywgyctnkfmypef";

    /**
     * send email with new password in recipient email
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
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL, PASSWORD);
                    }
                });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL));

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
