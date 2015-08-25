package it.qube.persistence.dao;
import it.mengoni.exception.LogicException;
import it.mengoni.persistence.dao.Dao;
import it.qube.persistence.dto.ResetLogin;
public interface ResetLoginDao extends Dao<ResetLogin> {
public ResetLogin getByPrimaryKey(String id) throws LogicException;
}
