package it.qube.core;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MailConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private String serverSMTP;
	private int porta = 25;
	private String user;
	private String password;
	private String from;
	private String charset;

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getServerSMTP() {
		return serverSMTP;
	}

	public void setServerSMTP(String serverSMTP) {
		this.serverSMTP = serverSMTP;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
