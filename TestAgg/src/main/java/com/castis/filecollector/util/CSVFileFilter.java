package com.castis.filecollector.util;

import java.io.File;
import java.io.FilenameFilter;

public class CSVFileFilter implements FilenameFilter{

	@Override
	public boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		return name.toLowerCase().endsWith(".csv");
	}

}
