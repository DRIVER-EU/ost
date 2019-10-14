package pl.com.itti.app.driver.service;

import co.perpixel.security.model.AuthUser;
import org.springframework.stereotype.Service;
import pl.com.itti.app.driver.form.UserForm;
import pl.com.itti.app.driver.util.EmailProperties;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.List;
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
