package it.qube.persistence.dto;

import java.sql.Timestamp;
import it.mengoni.persistence.dto.PersistentObject;

public interface ResetLogin extends PersistentObject {
	public String getId();

	public void setId(String id);

	public String getUsername();

	public void setUsername(String username);

	public Timestamp getExpire();

	public void setExpire(Timestamp expire);

	public Users getToUsers();

	public void setToUsers(Users toUsers);

	public boolean isExpired();

}
