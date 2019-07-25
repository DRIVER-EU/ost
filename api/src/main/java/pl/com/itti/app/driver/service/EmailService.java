package pl.com.itti.app.driver.service;

import co.perpixel.security.model.AuthUser;
import org.springframework.stereotype.Service;
import pl.com.itti.app.driver.form.UserForm;
import pl.com.itti.app.driver.util.EmailProperties;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Service
public class EmailService {

    public static void send(AuthUser authUser,
                            String password,
                            String trialName,
                            UserForm userForm) throws MessagingException {

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
        message.setFrom(new InternetAddress(EmailProperties.ADDRESS));

        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(authUser.getEmail()));
        message.setSubject("You have been invited to " + trialName);

        String msg = getMessage(authUser, password, userForm);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    private static String getMessage(AuthUser authUser, String password, UserForm userForm) {

        return "Login: " + authUser.getLogin() + "\n" +
                "Password: " + password + "\n" +
                "Role: " + String.join(", ", userForm.getRole());
    }
}
