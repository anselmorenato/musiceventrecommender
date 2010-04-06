package notification;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class GMailSender implements EmailSender{

    private final String hostname = "smtp.gmail.com";
    private final int port = 465;
    private String user;
    private String password;
    
    public GMailSender(String user, String password) {
    	this.user = user;
    	this.password = password;
    }

    public void send(String subject, String sMessage) throws MessagingException {
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", hostname);
        props.put("mail.smtps.auth", "true");
        // props.put("mail.smtps.quitwait", "false");

        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();
		
		transport.connect(hostname, port, user, password);
		
		MimeMessage message = new MimeMessage(mailSession);
	    message.setSubject(subject);
	    message.setContent(sMessage, "text/plain");

	    message.addRecipient(Message.RecipientType.TO, new InternetAddress(user));

        
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
       
		transport.close();
    }
}