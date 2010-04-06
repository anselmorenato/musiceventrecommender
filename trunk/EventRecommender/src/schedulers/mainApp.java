package schedulers;

import java.util.Calendar;
import java.util.Timer;

public class mainApp {
	public static void main(String[] args)
	{
		Timer timer = new Timer();
		Calendar date = Calendar.getInstance();

		timer.schedule(
				new Scheduler(),
				date.getTime(),
				1000 * 60 * 60 * 24 * 1
		);
	}
}
