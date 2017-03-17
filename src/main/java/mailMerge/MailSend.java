package mailMerge;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by user on 07.09.2016.
 */
public class MailSend {
    private static final Properties prop = new Properties();
    public static void mailSend(String currencyUSD,Double buy,Double sale) throws IOException {
        prop.load(new FileInputStream("config.properties"));
        String toRecipient = prop.getProperty("Recipients");
       final String password = prop.getProperty("passwordEmail");
       final String username = prop.getProperty("username");
        String from = prop.getProperty("from");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new GMailAuthenticator(username, password));

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toRecipient));
            message.setSubject("Currency course");
            message.setText("Currancy course:"
                    +"\n" + currencyUSD+" - " + "buy: "+ buy +" sale: "+sale
                    +"\n"
                    + "https://docs.google.com/spreadsheets/d/1UgMpez0Q-3DZS3f76gqzEwoV2zRsiueWhCup9Y_-bOo/pubchart?oid=402304657&format=interactive"
                    );

            Transport.send(message);

            System.out.println("Messages sended");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
