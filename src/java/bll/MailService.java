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

    private MailConnector mailConnect;

    public MailService() throws IOException {
        mailConnect = new MailConnector();
    }

    public void sendMail() throws MessagingException, IOException {


        Message message = new MimeMessage(mailConnect.getSession());
        message.setSubject("just a proof of concept");

        Address adressTO = new InternetAddress("kasperpro9@gmail.com");
        message.setRecipient(Message.RecipientType.TO, adressTO);

        MimeMultipart multiPart = new MimeMultipart();

        MimeBodyPart attachment = new MimeBodyPart();
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        attachment.attachFile(".design/ticketprint/A4 - 1.png");
        messageBodyPart.setContent("<h1>Email test from mailService!</h1>", "tekst/html");
        multiPart.addBodyPart(messageBodyPart);
        multiPart.addBodyPart(attachment);

        message.setContent(multiPart);

        Transport.send(message);
    }

    public static void main(String[] args) throws IOException, MessagingException {
        MailService ms = new MailService();
        ms.sendMail();
    }
}
