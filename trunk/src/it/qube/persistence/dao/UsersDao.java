package it.qube.persistence.dao;

import it.qube.persistence.dto.Users;
import it.mengoni.exception.LogicException;
import it.mengoni.persistence.dao.Dao;

public interface UsersDao extends Dao<Users> {
	public Users getByPrimaryKey(String username) throws LogicException;

	public Users getUserByEmail(String email) throws LogicException;

	public void updateLogin(String username, String password) throws LogicException;
}
