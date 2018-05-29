package com.castis.filecollector.comp;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.castis.filecollector.dao.CSVImportDAO;
import com.castis.filecollector.util.CSVFileFilter;
import com.castis.filecollector.util.CSVFileUtil;
import com.castis.filecollector.util.CSVImportException;

public class FileImportComp implements ApplicationContextAware {
	
	private static final Logger logger = Logger.getLogger(FileImportComp.class);
	
	private final static String DEFAULT_FIELD_TERMINATED_STRING = ",";
	private final static String DEFAULT_LINE_TERMINATED_STRING = "\n";
	private final static String DEFAULT_FOLDER_DIVIDE_STRING = "/";
	
	protected ApplicationContext ctx;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		this.ctx = applicationContext;
	}

	public void fileImport(String tableName, String fields, List<String> csvFilePathList,String successPath, String failPath, String fieldTerminatedString, String lineTerminatedString, long fileSizeCheckInterval) throws Exception
	{
		CSVFileUtil fileUtil = new CSVFileUtil();
		String importFailMessage = "";
		String csvFilePath = "";
		File importFile = null;
		
		// 1. validationCheck
		if(fieldTerminatedString==null||fieldTerminatedString.equals(null))
			fieldTerminatedString = DEFAULT_FIELD_TERMINATED_STRING;
		if(lineTerminatedString==null||lineTerminatedString.equals(null))
			lineTerminatedString = DEFAULT_LINE_TERMINATED_STRING;
		if(!successPath.endsWith(DEFAULT_FOLDER_DIVIDE_STRING))
			successPath = successPath+DEFAULT_FOLDER_DIVIDE_STRING;
		if(!failPath.endsWith(DEFAULT_FOLDER_DIVIDE_STRING))
			failPath = failPath+DEFAULT_FOLDER_DIVIDE_STRING;

		fileUtil.resultFolderCreate(successPath, failPath);
		
//		System.out.println("tableName " + tableName);
//		System.out.println("fields " + fields);
//		System.out.println("csvFilePathList " + csvFilePathList.toString());
		
		// 2.dao와 csvFilePath matching 
		Map<String, CSVImportDAO> daoMap = new LinkedHashMap<String, CSVImportDAO>();
		List<CSVImportDAO> daoList = getDaoList();
		
		if(daoList.size()!= csvFilePathList.size()) {
			throw new CSVImportException("daoList Size not equal csvFilePath Size");
		}
		else {
			for(int i=0;i<daoList.size();i++) {
				if(!csvFilePathList.get(i).endsWith(DEFAULT_FOLDER_DIVIDE_STRING))
					csvFilePathList.set(i, csvFilePathList.get(i)+DEFAULT_FOLDER_DIVIDE_STRING);
				daoMap.put(csvFilePathList.get(i), daoList.get(i));
			}
		}
		
		Iterator<String> csvFilePathIter = daoMap.keySet().iterator();
		
		while(csvFilePathIter.hasNext())
		{
			try {
				boolean isCSVImport = true;
				boolean isFileSizeCheck = false;
				csvFilePath = csvFilePathIter.next();
				
				importFile = null;
								
				// 3. csvFile이 있는  폴더의 csv파일을 들어온 순서대로 정렬하여 가장 마지막에 들어온 파일 1개만 가지고 온다.(CSV파일만 해당, 나머지는 무시.)
				File dir = new File(csvFilePath);
				File[] CSVFiles = dir.listFiles(new CSVFileFilter());
				
				// 3.1 csvFile이 없는 경우.
				if(CSVFiles.length==0){
					importFailMessage = "CSVFile is not Exist";
					logger.info(importFailMessage);
					isCSVImport = false;
				}
				// 3.2 csvFile이 있는 경우.
				else {
					for(int i=0;i<CSVFiles.length;i++) {
						File compareFile = null;
						
						if(importFile ==null)
							importFile = CSVFiles[i];
						else
							compareFile = CSVFiles[i];
						
						if(importFile!=null&&compareFile!=null)
							importFile = fileUtil.compareFile(importFile, compareFile);
					}
					
					// 3.3 csvFile Size 확인.
					while(!isFileSizeCheck) {
						isFileSizeCheck = fileUtil.fileSizeCheck(importFile, fileSizeCheckInterval);
					}
					
					// 3.4 successFolder Check
					if(!fileUtil.successFolderCheck(csvFilePath,successPath, tableName, importFile.getName())) {
						importFailMessage = importFile.getName()+" is in the successFolder";
						
						fileUtil.failFileMove(csvFilePath, tableName, failPath, importFile.getName(), importFailMessage);
						logger.info(importFailMessage);
						isCSVImport = false;
					}
					
					// 4 csvFile Import
					if(isCSVImport) {
						long start = System.currentTimeMillis();
						
						//4.1 csv import
						daoMap.get(csvFilePath).CSVImport(csvFilePath,importFile.getName(), tableName, fields, fieldTerminatedString, lineTerminatedString);
						
						//4.2 csv import 결과 확인.
						/*long startIdx = daoMap.get(csvFilePath).getLogTableStartId(maxLogTableId,tableName);*/
						/*int importResultCount = daoMap.get(csvFilePath).getCSVImportCount(tableName,testIdx);
						
						//4.3 history 테이블에 데이터 저장.
						daoMap.get(csvFilePath).insertCSVImportHistory(tableName, importFile.getName(), testIdx, importResultCount);*/
						
						long end = System.currentTimeMillis();
						
						logger.info(importFile.getName()+" Import Time :"+(end - start)+"ms");
					}
					
					
					// 5. csvFile FileMove(성공 시 success로 fail 시 fail로)
					fileUtil.successFileMove(csvFilePath, tableName, successPath, importFile.getName());
				}
			}
			catch (MyBatisSystemException e) {
				// TODO: handle exception
				if(e.getRootCause().getMessage().equals("Connection refused: connect")) {
					logger.error("FileCollector Exception: DB NOT Connection.");
				}
				else {
					throw new Exception(e.getCause());
				}
			}
			catch (Exception e) {
				// TODO: handle exception
				fileUtil.failFileMove(csvFilePath, tableName, failPath, importFile.getName(), e.getMessage());
				logger.error("FileCollector Exception : "+e.getMessage());
			}
		}
	}
	
	public List<CSVImportDAO> getDaoList()
	{
		List<CSVImportDAO> resultList = new ArrayList<CSVImportDAO>();

		CSVImportDAO defaultDao = (CSVImportDAO) ctx.getBean("CSVImportDAOMybatis");
		resultList.add(defaultDao);
		
		for(int i=0;i<ctx.getBeanDefinitionNames().length;i++)
		{
			String BeanName = ctx.getBeanDefinitionNames()[i];
			if(BeanName.contains("distributeCSVImportDAOMybatis"))
			{
				resultList.add((CSVImportDAO)ctx.getBean(BeanName));
			}
		}
		
		return resultList;
	}
}
