package com.castis.filecollector.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Repository("CSVImportDAOMybatis")
public class CSVImportDAOMybatis extends SqlSessionTemplate implements CSVImportDAO{
	
	@Autowired
	public CSVImportDAOMybatis(@Qualifier("importSqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional(rollbackFor={Exception.class}, value = "importTransactionManager", isolation=Isolation.READ_COMMITTED)
	public void CSVImport(String csvFilePath, String fileName, String tableName, String fields, String fieldTerminatedString, String lineTerminatedString) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		long idx = 0;
				
		paramMap.put("fileName", csvFilePath+fileName);
		paramMap.put("tableName", tableName);
		paramMap.put("fields", fields);
		paramMap.put("fieldTerminatedString", fieldTerminatedString);
		paramMap.put("lineTerminatedString", lineTerminatedString);
		paramMap.put("idx", idx);
		
		this.insert("com.castis.filecollector.dao.CSVImportDAOMybatis.csvImport",paramMap);
		
		long startIdx = (Long) paramMap.get("idx");
		long count = getCSVImportCount(tableName, startIdx);
		
		insertCSVImportHistory(tableName, fileName, startIdx, count);
	}

	@Override
	public long getTableLastIdValue(String tableName) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("tableName", tableName);
		return 0;
	}

	@Override
	public long isTableDataExist(String tableName) {
		// TODO Auto-generated method stub
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("tableName", tableName);
		return 0;
	}

	@Override
	public int getCSVImportCount(String tableName, long startIdx) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("tableName", tableName);
		paramMap.put("startIdx", startIdx);
		
		int count = this.selectOne("com.castis.filecollector.dao.CSVImportDAOMybatis.getCSVImportCount",paramMap);
		
		return count;
	}

	@Override
	public void insertCSVImportHistory(String type, String fileName,
			long startIdx, long count) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("type", type);
		paramMap.put("fileName", fileName);
		paramMap.put("startIdx", startIdx);
		paramMap.put("count", count);
		
		System.out.println("type " + type);
		System.out.println("fileName "+fileName);
		System.out.println("startIdx "+startIdx);
		System.out.println("count "+count);
		
		this.insert("com.castis.filecollector.dao.CSVImportDAOMybatis.insertCSVImportHistory",paramMap);
	}

	@Override
	public long getLogTableStartId(Long maxLogTableId,String tableName) {
		// TODO Auto-generated method stub
		//맨 처음 csv Import의 시작 ID는 무조건 1
		if(maxLogTableId==1) {
			return 1;
		}		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tableName", tableName);
		paramMap.put("maxLogTableId", maxLogTableId);
		
		//1. csvhistory 테이블과 조인하여 시작하는 idx값을 찾는다.
		Long result = selectOne("com.castis.filecollector.dao.CSVImportDAOMybatis.getLogTableStartId",paramMap);
		
		if(result==null||result.equals(null)) {
			// 2. csvhistory 테이블에 데이터가 없는 경우 로그테이블의 maxId값을 시작값으로 한다.
				result = (long) 1;
				return result;
		}
		
		return result;
	}

	@Override
	public long getLogTableMaxId(String tableName) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tableName", tableName);
		
		Long result = selectOne("com.castis.filecollector.dao.CSVImportDAOMybatis.getLogTableMaxId",paramMap);
		if(result ==null||result.equals(null)) {
			result = (long) 1; 
		}
		
		return result;
	}
}
