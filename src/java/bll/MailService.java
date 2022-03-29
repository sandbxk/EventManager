package bll;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MailService {

    private Properties emailProp;

    public MailService() throws IOException {

        emailProp = new Properties();
        emailProp.load(new FileReader("email.properties"));

    }

    public void sendMail() throws MessagingException, IOException {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.transport.protocol", "smtp");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailProp.getProperty("username"), emailProp.getProperty("password"));
            }
        });

        Message messge = new MimeMessage(session);
        messge.setSubject("just a proof of concept");

        Address adressTO = new InternetAddress("kasperpro9@gmail.com");
        messge.setRecipient(Message.RecipientType.TO, adressTO);

        MimeMultipart multiPart = new MimeMultipart();

        MimeBodyPart attachment = new MimeBodyPart();
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        attachment.attachFile(".design/ticketprint/A4 - 1.png");
        messageBodyPart.setContent("<h1>Email test from mailService!</h1>", "tekst/html");
        multiPart.addBodyPart(messageBodyPart);
        multiPart.addBodyPart(attachment);

        messge.setContent(multiPart);

        Transport.send(messge);
    }

    public static void main(String[] args) throws IOException, MessagingException {
        MailService ms = new MailService();
        ms.sendMail();
    }
}
