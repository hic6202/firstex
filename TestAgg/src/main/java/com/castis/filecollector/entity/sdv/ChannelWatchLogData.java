package com.castis.filecollector.entity.sdv;

import java.io.Serializable;
import java.util.Date;

import com.castis.filecollector.entity.AbstractLog;

public class ChannelWatchLogData extends AbstractLog implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1417933061107629236L;
	private Date logDate;
	private String externalNetworkCellIdUpper;
	private String channelId;
	private String externalNetworkCellIdLower; 
	private String macAddress;
	private String intervals;

	public String getIntervals() {
		return intervals;
	}



	public String getExternalNetworkCellIdUpper() {
		return externalNetworkCellIdUpper;
	}



	public void setExternalNetworkCellIdUpper(String externalNetworkCellIdUpper) {
		this.externalNetworkCellIdUpper = externalNetworkCellIdUpper;
	}



	public void setIntervals(String intervals) {
		this.intervals = intervals;
	}



	public Date getLogDate() {
		return logDate;
	}



	public void setLogDate(Date logDate) {
		this.logDate = logDate;
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



	@Override
	public Date getBaseDate() {
		// TODO Auto-generated method stub
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
