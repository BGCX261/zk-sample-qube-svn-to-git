package it.qube.persistence.dao;

import java.util.List;

import it.mengoni.exception.LogicException;
import it.qube.persistence.dto.Gelati;
import it.mengoni.persistence.dao.Dao;

public interface GelatiDao extends Dao<Gelati> {
	public Gelati getByPrimaryKey(Integer idGelato) throws LogicException;

	public List<Gelati> getByTavolo(Integer numeroTavolo) throws LogicException;

	public List<Gelati> getByTavoloDaPagare(Integer numeroTavolo) throws LogicException;

	public List<Gelati> getByTavoloPagati(Integer numeroTavolo) throws LogicException;

	public List<Gelati> getDaPagare() throws LogicException;

	public List<Gelati> getPagati() throws LogicException;

	public List<Gelati> getDaFare() throws LogicException;

	public List<Gelati> getFatti() throws LogicException;

	public List<Gelati> getByTavoloFatti(Integer numeroTavolo) throws LogicException;

	public List<Gelati> getByTavoloDaFare(Integer numeroTavolo) throws LogicException;

	public List<Gelati> getBy(Integer numeroTavolo, Boolean pagati, Boolean daFare) throws LogicException;

}
