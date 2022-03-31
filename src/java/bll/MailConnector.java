package bll;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MailConnector {

    private Properties properties;
    private Properties emailProp;

    public MailConnector() throws IOException {

        emailProp = new Properties();
        emailProp.load(new FileReader("email.properties"));
    }

    public Session getSession()
    {
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
        return session;
    }

    public static void main(String[] args) throws IOException {
        MailConnector mc = new MailConnector();
        System.out.println(mc.getSession().getProperties());
    }
    }
