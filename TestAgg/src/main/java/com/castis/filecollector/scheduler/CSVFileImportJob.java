package com.castis.filecollector.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.castis.filecollector.comp.FileImportComp;

public class CSVFileImportJob extends QuartzJobBean {
	
	private static final Logger logger = Logger.getLogger(CSVFileImportJob.class);
	
	private String csvFilePath; //csv파일경로
	private String successFilePath; //csv파일 import 성공 시 이동할 파일경로
	private String failFilePath; // csv파일 import 실패 시  이동할 파일경로
	private String tableName; // import 할 파일경로
	private String fields; // csv파일 필드 순서.
	private String fieldTerminatedString; // csv파일 필드 구분 문자열
	private String lineTerminatedString; // csv파일 라인 구분 문자열
	
	public void setFieldTerminatedString(String fieldTerminatedString) {
		this.fieldTerminatedString = fieldTerminatedString;
	}

	public void setLineTerminatedString(String lineTerminatedString) {
		this.lineTerminatedString = lineTerminatedString;
	}

	private long fileSizeCheckInterval;
	
	public void setFields(String fields) {
		this.fields = fields;
	}

	private FileImportComp fileImportComp;
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setFileSizeCheckInterval(long fileSizeCheckInterval) {
		this.fileSizeCheckInterval = fileSizeCheckInterval;
	}

	public FileImportComp getFileImportComp() {
		return fileImportComp;
	}

	public void setFileImportComp(FileImportComp fileImportComp) {
		this.fileImportComp = fileImportComp;
	}

	public void setCsvFilePath(String csvFilePath) {
		this.csvFilePath = csvFilePath;
	}

	public void setSuccessFilePath(String successFilePath) {
		this.successFilePath = successFilePath;
	}

	public void setFailFilePath(String failFilePath) {
		this.failFilePath = failFilePath;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		// 1. file import 수행.
		try {
			long start = System.currentTimeMillis();
			
			List<String> csvFileList = getCSVFileList(csvFilePath);
			logger.info("FileCollectorScheduler " +context.getJobDetail().getKey().getName()+" START");
			fileImportComp.fileImport(tableName,fields,csvFileList,successFilePath,failFilePath, fieldTerminatedString, lineTerminatedString, fileSizeCheckInterval);
			
			//tjost
			
			long end = System.currentTimeMillis();
			logger.info("FileCollectorScheduler " +context.getJobDetail().getKey().getName()+" END SUCCESS :"+ (end - start)+"ms");
			
		} catch (Exception e) {
			logger.error("FileCollectorScheduler FAILED EXCEPTION : " +context.getJobDetail().getKey().getName()+e);
		}
	}
	
	public List<String> getCSVFileList(String csvFilePath)
	{
		List<String> resultList = new ArrayList<String>();
		
		StringTokenizer token = new StringTokenizer(csvFilePath, ",");
		
		while (token.hasMoreTokens()) {
			String path = token.nextToken();
			resultList.add(path);
		}
		
		return resultList;
	}

}
