package notification;

import javax.mail.MessagingException;

public interface EmailSender {
	public void send(String subject, String message) throws MessagingException;
}
