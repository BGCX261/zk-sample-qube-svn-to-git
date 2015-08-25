package it.qube.core;


import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;


public class Config implements Serializable{

	private static final long serialVersionUID = 1L;


	private MailConfig mailConfig;

	public MailConfig getMailConfig() {
		return mailConfig;
	}

	public void setMailConfig(MailConfig mailConfig) {
		this.mailConfig = mailConfig;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}