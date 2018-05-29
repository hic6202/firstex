package com.castis.filecollector.entity.sdv;

import java.io.Serializable;
import java.util.Date;

import com.castis.filecollector.entity.AbstractLog;

public class SDVResourceLogData extends AbstractLog implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4269133178258616099L;

	
	private Date logDate;
	private String regionId;
	private String externalNetworkCellIdLower;
	private long cellCurBandwidth;
	private int tsId;
	private long tsidCurBandwidth;
	
	
	public int getTsId() {
		return tsId;
	}



	public void setTsId(int tsId) {
		this.tsId = tsId;
	}



	public long getTsidCurBandwidth() {
		return tsidCurBandwidth;
	}



	public void setTsidCurBandwidth(int tsidCurBandwidth) {
		this.tsidCurBandwidth = tsidCurBandwidth;
	}



	public Date getLogDate() {
		return logDate;
	}



	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}



	public String getRegionId() {
		return regionId;
	}



	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}



	public String getExternalNetworkCellIdLower() {
		return externalNetworkCellIdLower;
	}



	public void setExternalNetworkCellIdLower(String externalNetworkCellIdLower) {
		this.externalNetworkCellIdLower = externalNetworkCellIdLower;
	}



	public long getCellCurBandwidth() {
		return cellCurBandwidth;
	}



	public void setCellCurBandwidth(int cellCurBandwidth) {
		this.cellCurBandwidth = cellCurBandwidth;
	}



	@Override
	public Date getBaseDate() {
		return logDate;
	}
	
	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (Exception e) {
			System.out.println("can't clone object");
		}
		return obj;
	}
}
