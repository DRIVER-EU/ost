package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.driver.model.AuthUser;
import eu.fp7.driver.ost.driver.form.UserForm;
import eu.fp7.driver.ost.driver.util.EmailProperties;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
public class EmailService {

    public static void sendNewSessionMail(AuthUser authUser,
                                          String password,
                                          String trialName,
                                          UserForm userForm) throws MessagingException {
        String subjectString = "You have been invited to " + trialName;
        String messageString = getUserMailMessage(authUser, password, userForm);
        sendMail(authUser.getEmail(), subjectString, messageString);
    }

    public static void sendMail(String addressList, String subjectString, String messageString
    )  {
        Address[] addresses;
        try {
            addresses = InternetAddress.parse(addressList);
        } catch (AddressException e) {
            e.printStackTrace();
            return;
        }
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", EmailProperties.TLS_ENABLE);
        prop.put("mail.smtp.host", EmailProperties.HOST);
        prop.put("mail.smtp.port", EmailProperties.PORT);
        prop.put("mail.smtp.ssl.trust", EmailProperties.HOST);

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailProperties.USER_NAME, EmailProperties.USER_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        try {
            message.setFrom(new InternetAddress(EmailProperties.ADDRESS));
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(subjectString);
            mimeBodyPart.setContent(messageString, "text/html");
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private static String getUserMailMessage(AuthUser authUser, String password, UserForm userForm) {

        return "Login: " + authUser.getLogin() + "\n" +
                "Password: " + password + "\n" +
                "Role: " + String.join(", ", userForm.getRole());
    }
}
