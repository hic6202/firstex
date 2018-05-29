package com.castis.filecollector.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CSVController {
	
	@Value("#{fileCollectorProperty['csvFile.path']}")
	private String tempPath;
	
	@RequestMapping(value="/csvExport")
	@ResponseBody
	public void csvExport(@RequestParam String tableName, int insertSize){
		
	}
	
	@RequestMapping(value="/csvImport")
	@ResponseBody
	public void csvImport(@RequestParam String fileName, String tableName, String seperateString){
		if(seperateString==null||seperateString.equals(null))
		{
			seperateString = "|";
		}
		fileName = tempPath+fileName+".csv";
		try {
			/*csvImportDAO.CSVImporter(fileName, tableName, seperateString);*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
