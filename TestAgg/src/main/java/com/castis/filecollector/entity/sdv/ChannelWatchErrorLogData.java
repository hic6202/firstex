package com.castis.filecollector.entity.sdv;

import java.io.Serializable;
import java.util.Date;

import com.castis.filecollector.entity.AbstractLog;

public class ChannelWatchErrorLogData extends AbstractLog implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6803088233192026010L;
	private Date logDate;
	private String regionId;
	private String channelId;
	private String externalNetworkCellIdLower;
	private String macAddress;
	private String errorCode;
	private int count;
	
	
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

	
	public String getChannelId() {
		return channelId;
	}


	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}



	public String getExternalNetworkCellIdLower() {
		return externalNetworkCellIdLower;
	}


	public void setExternalNetworkCellIdLower(String externalNetworkCellIdLower) {
		this.externalNetworkCellIdLower = externalNetworkCellIdLower;
	}



	public String getMacAddress() {
		return macAddress;
	}


	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
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
