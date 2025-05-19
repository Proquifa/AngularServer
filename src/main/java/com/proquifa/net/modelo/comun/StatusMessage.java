package com.proquifa.net.modelo.comun;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "StatusMessage")
public class StatusMessage {
	
	private Integer status;
	private String message;
	private Object current;
	
	public StatusMessage() {
	}

	@JsonProperty(value = "status_code")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@JsonProperty(value = "message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty(value = "current")
	public Object getCurrent() {
		return current;
	}
	
	public void setCurrent(Object current) {
		this.current = current;
	}
}
