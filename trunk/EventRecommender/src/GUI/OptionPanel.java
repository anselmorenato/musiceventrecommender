package GUI;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import Application.Preferences;

public class OptionPanel extends JPanel{
	
	private JTextField path0;
	private JTextField path1;
	private JTextField path2;
	private JTextField usernameField;
	private JTextField passwordField;
	private JSpinner spin;
	private JComboBox loc;
	private JCheckBox all;
	private JCheckBox emailNotificationEnabled;
	private JComboBox choice;
	private JComboBox choice2;
	private JButton save;
	private JButton cancel;
	
	public JPanel mpanel;
	
	private String datPath;
	private String dirPath;
	private String libPath;
	private int top;
	private int location;
	private boolean every;
	private int filesys;
	private int rec;
	private boolean emailEnabled;
	private String emailUsername;
	private String emailPassword;
	
	private String dir = "config.txt";
	private Preferences config;
	
	public OptionPanel()
	{
		config = new Preferences(dir);
		config.readPreferences();
		datPath = config.getDatabasePath();
		dirPath = config.getMusicLibraryPath();
		libPath = config.getItunesLibraryPath();
		top = config.getTopArtists();
		location = config.getLocation();
		every = config.getAllEvents();
		filesys = config.getScanFile();
		rec = config.getScanRec();
		emailEnabled = config.getEmailEnabled();
		emailUsername = config.getEmailUser();
		emailPassword = config.getEmailPassword();
		
		
		mpanel = new JPanel();
		mpanel.setLayout(new BoxLayout(mpanel,BoxLayout.Y_AXIS));
		
		JPanel p0 = new JPanel();
		p0.setLayout(new BoxLayout(p0,BoxLayout.Y_AXIS));
		path0 = new JTextField(datPath,30);
		p0.add(new JLabel("Enter the path to your database:"));
		p0.add(path0);
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1,BoxLayout.Y_AXIS));
		path1 = new JTextField(dirPath,30);
		p1.add(new JLabel("Enter the path to your music directory:"));
		p1.add(path1);	
		
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2,BoxLayout.Y_AXIS));
		path2 = new JTextField(libPath,30);
		p2.add(new JLabel("Enter the path to your XML ITunes Library:"));
		p2.add(path2);
		
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3,BoxLayout.Y_AXIS));
		p3.setBorder(BorderFactory.createTitledBorder("Recommendation"));
		JPanel p31 = new JPanel();
		SpinnerModel model = new SpinnerNumberModel(top,0,100,1);
		spin = new JSpinner(model);
		p31.add(new JLabel("Number of top artists:"));
		p31.add(spin);
		JPanel p33 = new JPanel();
		p33.add(new JLabel("Location:"));
		String[] locations = {"Montreal,Canada","Quebec,Canada","Sherbrooke,Canada","Toronto,Canada"};
		loc = new JComboBox(locations);
		loc.setSelectedIndex(location);
		p33.add(loc);
		JPanel p32 = new JPanel();
		all = new JCheckBox();
		all.setSelected(every);
		p32.add(new JLabel("Get all events not only the ones for the top artists: "));
		p32.add(all);
		p3.add(p31);
		p3.add(p33);
		p3.add(p32);
		
		JPanel p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4,BoxLayout.PAGE_AXIS));
		p4.setBorder(BorderFactory.createTitledBorder("Schedule"));
		JPanel p41 = new JPanel();
		p41.add(new JLabel("Scan music directory or library once a:"));
		String[] frequence = {"day","week","month","year"};
		choice = new JComboBox(frequence);
		choice.setSelectedIndex(filesys);
		p41.add(choice);
		JPanel p42 = new JPanel();
		p42.add(new JLabel("Scan for recommendations once a:"));
		choice2 = new JComboBox(frequence);
		choice2.setSelectedIndex(rec);
		p42.add(choice2);
		p4.add(p41);
		p4.add(p42);
		
		JPanel notification = new JPanel();
		notification.setLayout(new BoxLayout(notification,BoxLayout.Y_AXIS));
		notification.setBorder(BorderFactory.createTitledBorder("Email Notification"));
		JPanel notif_check = new JPanel();
		emailNotificationEnabled = new JCheckBox();
		emailNotificationEnabled.setSelected(emailEnabled);
		notif_check.add(new JLabel("Enable Email notification: "));
		notif_check.add(emailNotificationEnabled);
		usernameField = new JTextField(this.emailUsername,10);
		passwordField = new JPasswordField(this.emailPassword, 10);
		notification.add(notif_check);
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel,BoxLayout.Y_AXIS));
		userPanel.add(new JLabel("GMail Address:"));
		userPanel.add(usernameField);
		
		JPanel passPanel = new JPanel();
		passPanel.setLayout(new BoxLayout(passPanel,BoxLayout.Y_AXIS));
		passPanel.add(new JLabel("Password:"));
		passPanel.add(passwordField);
		notification.add(userPanel);
		notification.add(passPanel);
		
		
		JPanel p5 = new JPanel();
		save = new JButton("Save");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				saveSettings();
			}
		});
		cancel = new JButton("Cancel");		
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setBack();
			}
		});
		p5.add(save);
		p5.add(cancel);
		
		mpanel.add(p0);
		mpanel.add(p1);
		mpanel.add(p2);
		mpanel.add(p3);
		mpanel.add(p4);	
		mpanel.add(notification);
		mpanel.add(p5);
		
	}
	
	public void setBack()
	{
		config.readPreferences();
		datPath = config.getDatabasePath();
		dirPath = config.getMusicLibraryPath();
		libPath = config.getItunesLibraryPath();
		top = config.getTopArtists();
		location = config.getLocation();
		every = config.getAllEvents();
		filesys = config.getScanFile();
		rec = config.getScanRec();
		
		path0.setText(datPath);
		path1.setText(dirPath);
		path2.setText(libPath);
		spin.setValue(top);
		loc.setSelectedIndex(location);
		all.setSelected(every);
		choice.setSelectedIndex(filesys);
		choice2.setSelectedIndex(rec);
		
		emailNotificationEnabled.setSelected(emailEnabled);
		usernameField.setText(emailUsername);
		passwordField.setText(emailPassword);
	}
	
	public void saveSettings()
	{
		datPath = path0.getText();
		dirPath = path1.getText();
		libPath = path2.getText();
		top =(Integer)spin.getValue();
		location = loc.getSelectedIndex();
		every = all.isSelected();
		filesys = choice.getSelectedIndex();
		rec = choice2.getSelectedIndex();
		
		config.setDatabasePath(datPath);
		config.setMusicLibraryPath(dirPath);
		config.setItunesLibraryPath(libPath);
		config.setTopArtists(top + "");
		config.setTopArtists(location + "");
		config.setScanFile(filesys + "");
		config.setScanRec(rec + "");
	
		if(every)
			config.setAllEvents("true");
		else
			config.setAllEvents("false");
		
		config.setEmailEnabled(emailNotificationEnabled.isSelected());
		config.setEmailAddress(usernameField.getText());
		config.setEmailPass(passwordField.getText());
		
		config.writePreferences();
	}
}
