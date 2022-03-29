package bll;


import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class MailService {

    public EmailAttachment setTicketAtch(String rootPath, String eventName)
    {
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(rootPath);
        attachment.setDescription("a demo ticket for a half build ticketguru");

        return attachment;
    }

    public MultiPartEmail setEmail(String emailReciever) throws EmailException {
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
        email.addTo(emailReciever);
        email.setFrom("ticketguru12@gmail.com");
        email.setSmtpPort(587);
        email.setAuthentication("ticketguru12@gmail.com", "9Hc0OCi2f");
        email.setSubject("email service tests");
        email.setTLS(true);
        email.setMsg("bare en hurtig test");
        return email;
    }

    public void sendEmail(EmailAttachment atch, MultiPartEmail email) throws EmailException {
        email.attach(atch);
        email.send();
    }

    public static void main(String[] args) throws EmailException {
        MailService ms = new MailService();
        EmailAttachment atch =  ms.setTicketAtch(".design/ticketprint/A4 - 1.png", "test1");
        MultiPartEmail email = ms.setEmail("kasperpro9@gmail.com");
        ms.sendEmail(atch, email);
    }
}
