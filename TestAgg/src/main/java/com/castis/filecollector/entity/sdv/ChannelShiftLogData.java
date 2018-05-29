package com.castis.filecollector.entity.sdv;

import java.io.Serializable;
import java.util.Date;

import com.castis.filecollector.entity.AbstractLog;

public class ChannelShiftLogData extends AbstractLog implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8166361129820930831L;

	private Date logDate;
	private String regionId;
	private String channelId;
	private String externalNetworkCellIdLower;
	
	
	
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
