package it.qube.model.dto;

import java.io.Serializable;


public class UtenteDto implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2561263427422952644L;
	
	private String username;
	private String password;
	private String nome;
	private String cognome;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
}
