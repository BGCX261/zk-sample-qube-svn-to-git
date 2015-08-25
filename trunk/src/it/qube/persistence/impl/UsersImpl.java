package it.qube.persistence.impl;

import it.mengoni.persistence.dao.Condition;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import it.mengoni.persistence.dto.PoLazyProperties;
import it.mengoni.persistence.dto.PoProperties;
import it.qube.persistence.DaoFactory;
import it.qube.persistence.dto.HashedLogin;
import it.qube.persistence.dto.ResetLogin;
import it.qube.persistence.dto.Users;

import java.util.List;

import org.javatuples.Tuple;
import org.javatuples.Unit;

public class UsersImpl extends AbstractPersistentObject implements Users {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String firstname;
	private String lastname;
	private String email;
	private String role;
	private HashedLogin login = new HashedLogin();

	public UsersImpl() {
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getRole() {
		return role;
	}

	@Override
	public void setRole(String role) {
		this.role = role;
	}

	public HashedLogin getLogin() {
		return login;
	}

	public void setLogin(HashedLogin login) {
		this.login = login;
	}

	private transient PoProperties<ResetLogin> listResetLogin = new PoLazyProperties<ResetLogin>();

	public List<ResetLogin> getListResetLogin() {
		return listResetLogin.getValue(DaoFactory.getResetLoginDao(), new Condition("USERNAME = ?", username));
	}

	public void setListResetLogin(List<ResetLogin> listResetLogin) {
		this.listResetLogin.setValue(listResetLogin);
	}

	@Override
	protected Tuple newKey() {
		return new Unit<String>(username);
	}

	@Override
	public boolean isAdmin() {
		return "A".equalsIgnoreCase(role);
	}

	@Override
	public String getDisplayLabel() {
		return getFirstname() + " " + getLastname();
	}
}
