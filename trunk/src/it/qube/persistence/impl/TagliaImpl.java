package it.qube.persistence.impl;

import it.mengoni.persistence.dao.Condition;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import it.mengoni.persistence.dto.PoLazyProperties;
import it.mengoni.persistence.dto.PoProperties;
import it.qube.persistence.DaoFactory;
import it.qube.persistence.dto.Gelati;
import it.qube.persistence.dto.Taglia;

import java.util.List;

import org.javatuples.Tuple;
import org.javatuples.Unit;

public class TagliaImpl extends AbstractPersistentObject implements Taglia {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer idTaglia;
	private String desrizione;
	private Double prezzo;

	public TagliaImpl() {
	}

	@Override
	public Integer getIdTaglia() {
		return idTaglia;
	}

	@Override
	public void setIdTaglia(Integer idTaglia) {
		this.idTaglia = idTaglia;
	}

	@Override
	public String getDescrizione() {
		return desrizione;
	}

	@Override
	public void setDescrizione(String desrizione) {
		this.desrizione = desrizione;
	}

	@Override
	public Double getPrezzo() {
		return prezzo;
	}

	@Override
	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	private transient PoProperties<Gelati> listGelati = new PoLazyProperties<Gelati>();

	public List<Gelati> getListGelati() {
		return listGelati.getValue(DaoFactory.getGelatiDao(), new Condition("TAGLIA = ?", idTaglia));
	}

	public void setListGelati(List<Gelati> listGelati) {
		this.listGelati.setValue(listGelati);
	}

	@Override
	protected Tuple newKey() {
		return new Unit<Integer>(idTaglia);
	}

	@Override
	public String getDisplayLabel() {
		return getDescrizione();
	}
}
