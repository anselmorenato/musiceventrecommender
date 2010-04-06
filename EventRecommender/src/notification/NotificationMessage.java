package notification;

import java.util.List;

import music.Event;

public class NotificationMessage {
	List<Event> events;
	String message;
	
	public NotificationMessage(List<Event> events) {
		this.events = events;	
		generateMessage();
	}
	
	private String header() {
		String header = 
			"Here are some events that you may enjoy\n";
		return header;
	}
	
	private void generateMessage() {
		message = header();
		for (Event e : events) {
			TextEventNotification text = new TextEventNotification(e);
			message += text + "\n\n";
		}
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String toString() {
		return this.message;
	}
}
