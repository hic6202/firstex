package com.castis.filecollector.entity;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractLog implements Serializable {
	private static final long serialVersionUID = -4901896309862382305L;
	
	protected long id;
	protected String domainId;
	protected Long originalId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public Long getOriginalId() {
		return originalId;
	}

	public void setOriginalId(Long originalId) {
		this.originalId = originalId;
	}

	public abstract Date getBaseDate();	
}
