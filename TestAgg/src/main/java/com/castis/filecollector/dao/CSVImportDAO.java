package com.castis.filecollector.dao;




public interface CSVImportDAO {
	
	public void CSVImport(String csvFilePath, String fileName, String tableName, String fields, String fieldTerminatedString, String lineTerminatedString) throws Exception;
	
	public long getTableLastIdValue(String tableName);
	
	public long isTableDataExist(String tableName);
	
	
	/* history 관련 DAO */
	
	public int getCSVImportCount(String tableName, long startIdx);
	
	public void insertCSVImportHistory(String type, String fileName, long startIdx, long count) throws Exception;
	
	public long getLogTableStartId(Long maxLogTableId, String tableName);
	
	public long getLogTableMaxId(String tableName);
}
