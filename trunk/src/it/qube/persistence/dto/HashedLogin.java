package it.qube.persistence.dto;


import it.qube.core.ApplicationError;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HashedLogin implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final String UTF_8 = "UTF-8";
	private static final String SHA_256 = "SHA-256";
	private static final Logger logger = LoggerFactory.getLogger(HashedLogin.class);

	private String hashedPassword;
	private String salt;

	/**
	 * Costruttore a distruzione di password, genera un salt casuale, produce l'hash e lo memorizza.
	 * La password in ingresso viene lasciata al garbage collector.
	 * @param password
	 */
	public HashedLogin(String password) {
		super();
		salt = UUID.randomUUID().toString();
		hashedPassword = generateHash(salt + password);
	}

	public void setPassword(String password) {
		salt = UUID.randomUUID().toString();
		hashedPassword = generateHash(salt + password);
	}

	public HashedLogin() {
		super();
	}

	public HashedLogin(String hashedPassword, String salt) {
		super();
		this.hashedPassword = hashedPassword;
		this.salt = salt;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public String getSalt() {
		return salt;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String generateHash(String input) {
		try {
			MessageDigest sha = MessageDigest.getInstance(SHA_256);
			byte[] hashedBytes = sha.digest(input.getBytes(UTF_8));
			return org.apache.commons.codec.digest.DigestUtils.shaHex(hashedBytes);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error", e);
			throw new ApplicationError("No Such Algorithm Exception", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Error", e);
			throw new ApplicationError("Unsupported Encoding Exception", e);
		}
	}

	/**
	 * Controlla che la password corrisponda
	 * @param password
	 * @return true se la password è giusta
	 */
	public boolean match(String password) {
		String tmp = generateHash(salt + password);
		return tmp.equals(hashedPassword);
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}