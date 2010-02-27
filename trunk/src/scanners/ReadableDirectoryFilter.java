package scanners;

import java.io.File;
import java.io.FileFilter;

public class ReadableDirectoryFilter implements FileFilter {

	public boolean accept(File pathname) {
		if(pathname.isDirectory() && pathname.canRead())
			return true;
		
		return false;
	}

}
