package com.castis.filecollector.util;

import java.io.File;
import java.util.StringTokenizer;

public class CSVFileUtil{
	
	public boolean resultFolderCreate(String successPath, String failPath) {
		File successResultFolder = new File(successPath);
		File failPathResultFolder = new File(failPath);
		
		if(!successResultFolder.exists()) {
			successResultFolder.mkdir();
		}
		
		if(!failPathResultFolder.exists()) {
			failPathResultFolder.mkdir();
		}
		
		return true;
	}
	
	// file 2개를 비교하여 최근에 수정 된 날짜 기준으로 오래 된 것을 return
	public File compareFile(File f1, File f2) {
		
		if (f1.lastModified() > f2.lastModified())
			return f2;
		if (f1.lastModified() == f2.lastModified())
			return f2;
		return f1;
	}
	
	//fileSize 비교.
	public boolean fileSizeCheck(File f1, long fileSizeCheckInterval) throws Exception
	{
		long beforeFileSize = getFileSize(f1);
		
		Thread.sleep(fileSizeCheckInterval);
		
		long afterFileSize = getFileSize(f1);
		
		if(beforeFileSize==afterFileSize)
			return true;
		else
			return false;
	}
	
	// fileSize(byte 단위)
	public long getFileSize(File f1) throws Exception
	{
		if(f1.exists())
			return f1.length();
		else
			throw new Exception("file not Exist");
	}
	
	// success Folder에 파일이 있을 시 import는 안하도록 설정.
	public boolean successFolderCheck(String csvFilePath, String successFilePath, String tableName, String fileName)
	{
		String csvFileFolderName = "";
		StringTokenizer token = new StringTokenizer(csvFilePath, "/");
		
		while (token.hasMoreTokens()) {
			csvFileFolderName = token.nextToken();
		}
		
		File fileToMove = new File(successFilePath+csvFileFolderName+"_"+tableName+"_"+fileName);
		if(fileToMove.exists())
			return false;
		
		return true;
	}
	
	//success Folder로 파일 이동
	public boolean successFileMove(String csvFilePath, String tableName, String successFilePath, String fileName) throws Exception
	{
		String csvFileFolderName = "";
		StringTokenizer token = new StringTokenizer(csvFilePath, "/");
		
		while (token.hasMoreTokens()) {
			csvFileFolderName = token.nextToken();
		}
		
		File file = new File(csvFilePath+fileName);
		File fileToMove = new File(successFilePath+csvFileFolderName+"_"+tableName+"_"+fileName);
		if(fileToMove.exists())
			return false;
		
		boolean isMoved = file.renameTo(fileToMove);
		
		if(isMoved)
			return true;
		else
			throw new Exception(fileName + "fileMove Fail");
	}
	
	//fail Folder로 파일 이동
	public void failFileMove(String csvFilePath, String tableName, String failFilePath, String fileName, String message) throws Exception
	{
		String csvFileFolderName = "";
		StringTokenizer token = new StringTokenizer(csvFilePath, "/");
		
		while (token.hasMoreTokens()) {
			csvFileFolderName = token.nextToken();
		}
		
		File file = new File(csvFilePath+fileName);
		File fileToMove = new File(failFilePath+"fail_"+csvFileFolderName+"_"+tableName+"_"+fileName);
		
		file.renameTo(fileToMove);
	}
	
	//test
	public void removeFile(String filePath) {
		File f1 = new File(filePath);
		f1.delete();
	}
}
