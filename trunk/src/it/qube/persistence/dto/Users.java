package it.qube.persistence.dto;

import it.mengoni.persistence.dto.PersistentObject;

import java.util.List;

public interface Users extends PersistentObject {
	public String getUsername();

	public void setUsername(String username);

	public String getFirstname();

	public void setFirstname(String firstname);

	public String getLastname();

	public void setLastname(String lastname);

	public String getEmail();

	public void setEmail(String email);

	public String getRole();

	public void setRole(String role);

	public HashedLogin getLogin();

	public void setLogin(HashedLogin login);

	public List<ResetLogin> getListResetLogin();

	public void setListResetLogin(List<ResetLogin> toResetLogin);

	public boolean isAdmin();
}
