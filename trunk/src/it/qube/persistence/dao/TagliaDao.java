package it.qube.persistence.dao;

import it.mengoni.exception.LogicException;
import it.qube.persistence.dto.Taglia;
import it.mengoni.persistence.dao.Dao;

public interface TagliaDao extends Dao<Taglia> {
	public Taglia getByPrimaryKey(Integer idTaglia) throws LogicException;
}
