package GUI;

import java.awt.Container;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Application.Preferences;

import db.Database;
import db.DatabaseException;

public class Start extends JFrame{

	public static void main(String args[]) {
		ListPanel ls;
		Preferences config = new Preferences("config.txt");
		config.readPreferences();
		String datPath = config.getDatabasePath();
		try
		{
			Database db = new Database(datPath);	
			ls = new ListPanel(db);
		}
		catch(DatabaseException e)
		{
			System.out.println("Eh ben");
			return;
		}

		OptionPanel op = new OptionPanel();
		JFrame frame = new JFrame();
		frame.setTitle("Music Event Recommender");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = frame.getContentPane();
		cp.setLayout(new BoxLayout(cp,BoxLayout.Y_AXIS));
		
		JPanel p1 = new JPanel();
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Events",ls.panel);
		tabbedPane.addTab("Options",op.mpanel);
	    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        p1.add(tabbedPane);
		
		cp.add(p1);
		
		frame.setVisible(true);
		frame.pack();
	}
}
