package it.qube.persistence.impl;

import it.mengoni.persistence.dto.AbstractPersistentObject;
import it.mengoni.persistence.dto.PoLazyProperty;
import it.mengoni.persistence.dto.PoProperty;
import it.qube.persistence.DaoFactory;
import it.qube.persistence.dto.ResetLogin;
import it.qube.persistence.dto.Users;

import java.sql.Timestamp;
import java.util.Date;

import org.javatuples.Tuple;
import org.javatuples.Unit;

public class ResetLoginImpl extends AbstractPersistentObject implements ResetLogin {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private Timestamp expire;

	public ResetLoginImpl() {
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
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
	public Timestamp getExpire() {
		return expire;
	}

	@Override
	public void setExpire(Timestamp expire) {
		this.expire = expire;
	}

	private transient PoProperty<Users> toUsers = new PoLazyProperty<Users>();

	public Users getToUsers() {
		return toUsers.getValue(DaoFactory.getUsersDao(), username);
	}

	public void setToUsers(Users toUsers) {
		this.toUsers.setValue(toUsers);
	}

	@Override
	protected Tuple newKey() {
		return new Unit<String>(id);
	}

	@Override
	public boolean isExpired() {
		return expire!=null && expire.after(new Date());
	}

	@Override
	public String getDisplayLabel() {
		return getToUsers().getDisplayLabel();
	}

}
