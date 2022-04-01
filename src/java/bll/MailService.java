// package bll;
//
//
// import javax.activation.DataHandler;
// import javax.activation.DataSource;
// import javax.activation.FileDataSource;
// import javax.mail.*;
// import javax.mail.internet.InternetAddress;
// import javax.mail.internet.MimeBodyPart;
// import javax.mail.internet.MimeMessage;
// import javax.mail.internet.MimeMultipart;
// import java.awt.*;
// import java.io.*;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Properties;
// import java.util.logging.Level;
// import java.util.logging.Logger;
//
// public class MailService {
//
//     private MailConnector mailConnect;
//
//     public MailService() throws IOException {
//         mailConnect = new MailConnector();
//     }
//
//     public void sendMail(String to, String from, String subject, String body, List<File> attachments) throws MessagingException, IOException {
//
//
//         try {
//             Message message = new MimeMessage(Session.getInstance(System.getProperties()));
//             message.setFrom(new InternetAddress(from));
//             message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//             message.setSubject(subject);
//             // create the message part
//             MimeBodyPart content = new MimeBodyPart();
//             // fill message
//             content.setText(body);
//             Multipart multipart = new MimeMultipart();
//             multipart.addBodyPart(content);
//             // add attachments
//             for(File file : attachments) {
//                 MimeBodyPart attachment = new MimeBodyPart();
//                 DataSource source = new FileDataSource(file);
//                 attachment.setDataHandler(new DataHandler(source));
//                 attachment.setFileName(file.getName());
//                 multipart.addBodyPart(attachment);
//             }
//             // integration
//             message.setContent(multipart);
//             // store file
//             message.writeTo(new FileOutputStream(new File("C:\\Users\\kaspe\\Desktop\\mail.eml")));
//         } catch (MessagingException ex) {
//             System.out.println("error");
//         }
//     }
//
//     public static void main(String[] args) throws IOException, MessagingException {
//         File file = new File(".design/ticketprint/A4 - 1.png");
//         List<File> files = new ArrayList<>();
//         files.add(file);
//         MailService ms = new MailService();
//         ms.sendMail("kasperpro9@gmail.com","ticketguru12@gmail.com", "test", "just a test",files);
//         Desktop.getDesktop().open(new File("C:\\Users\\kaspe\\Desktop\\mail.eml"));
//     }
// }
