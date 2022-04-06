 package bll;


 import javax.activation.DataHandler;
 import javax.activation.DataSource;
 import javax.activation.FileDataSource;
 import javax.mail.*;
 import javax.mail.internet.InternetAddress;
 import javax.mail.internet.MimeBodyPart;
 import javax.mail.internet.MimeMessage;
 import javax.mail.internet.MimeMultipart;
 import java.awt.*;
 import java.io.*;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Properties;
 import java.util.logging.Level;
 import java.util.logging.Logger;

 public class MailService
 {

     private MailConnector mailConnect;

     public MailService() throws IOException {
         mailConnect = new MailConnector();
     }

     public void sendMail(String to, File attachments) throws MessagingException, IOException {

         String subject = "welcome and thanks for buying this ticket";
         String body = "please hold onto this ticket, since it is your ticket for a good time," + "\n" + "you can print the ticket out or just show it on your phone";
         try {
             Message message = new MimeMessage(Session.getInstance(System.getProperties()));
             message.setFrom(new InternetAddress("ticketguru12@gmail.com"));
             message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
             message.setSubject(subject);
             // create the message part
             MimeBodyPart content = new MimeBodyPart();
           // fill message
           content.setText(body);
             Multipart multipart = new MimeMultipart();
             multipart.addBodyPart(content);
             // add attachments
                 MimeBodyPart attachment = new MimeBodyPart();
                 DataSource source = new FileDataSource(attachments);
                 attachment.setDataHandler(new DataHandler(source));
                 attachment.setFileName(attachments.getName());
                 multipart.addBodyPart(attachment);
             // integration
             message.setContent(multipart);
             // store file
             message.writeTo(new FileOutputStream(new File(".design/mails/" + to + ".eml")));
         } catch (MessagingException ex) {System.out.println("error");
         }
     }

     public static void main(String[] args) throws IOException, MessagingException {
         File file = new File(".design/ticketprint/A4 - 1.png");
         MailService ms = new MailService();
         ms.sendMail("Kasperpro9@gmail.com", file);
     }
 }
