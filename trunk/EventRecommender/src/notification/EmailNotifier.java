package notification;

import java.util.List;

import javax.mail.MessagingException;

import music.Event;

import schedulers.Schedulable;

public class EmailNotifier implements Schedulable{

	EmailSender sender;
	List<Event> events;
	
	public EmailNotifier(EmailSender sender, List<Event> events) {
		this.sender = sender;
		this.events = events;
	}
	public boolean run() {
		String subject = "EventRecommender: New Events";
		NotificationMessage msg = new NotificationMessage(events);
		try {
			sender.send(subject, msg.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
}
