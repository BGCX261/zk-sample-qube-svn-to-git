package it.qube.persistence.dao;

import java.util.List;

import it.mengoni.exception.LogicException;
import it.qube.persistence.dto.Gusti;
import it.mengoni.persistence.dao.Dao;

public interface GustiDao extends Dao<Gusti> {
	public Gusti getByPrimaryKey(Integer idGusto) throws LogicException;

	public List<Gusti> getDisponibili() throws LogicException;
}
