package scanners;

import java.io.File;
import java.io.FileFilter;

public class MP3FileFilter implements FileFilter{

	String[] extensions = {".mp3"};
	
	/**
	 * Accepts music files and directories
	 */
	public boolean accept(File pathname) {
		
		if (!pathname.isFile() || !pathname.canRead())
			return false;
			
		String name = pathname.getName();
		int length = name.length();
		
		/*
		 *  Only interested in names of the form name.ext
		 *  where name is at least 1 character (hence 5 char minimum)
		 */
		if (name.isEmpty() || length < 5)
			return false;
		
		
		String ext = name.substring(length - 4, length);
		
		for (String e : extensions) {
			if (ext.compareToIgnoreCase(e) == 0)
				return true;
		}
		
		return false;
	}

}
