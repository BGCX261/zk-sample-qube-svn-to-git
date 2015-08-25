package it.qube.persistence.impl;

import it.mengoni.persistence.dto.AbstractPersistentObject;
import it.mengoni.persistence.dto.PoLazyProperty;
import it.mengoni.persistence.dto.PoProperty;
import it.qube.persistence.DaoFactory;
import it.qube.persistence.dto.Gelati;
import it.qube.persistence.dto.Gusti;
import it.qube.persistence.dto.Taglia;

import org.javatuples.Tuple;
import org.javatuples.Unit;

public class GelatiImpl extends AbstractPersistentObject implements Gelati {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer idGelato;
	private Integer numeroTavolo;
	private Integer gustoUno;
	private Integer gustoDue;
	private Integer gustoTre;
	private Boolean panna = false;
	private Integer taglia;
	private Boolean pagato = false;
	private Boolean fatto = false;

	public GelatiImpl() {
	}

	@Override
	public Integer getIdGelato() {
		return idGelato;
	}

	@Override
	public void setIdGelato(Integer idGelato) {
		this.idGelato = idGelato;
		key = null;
	}

	@Override
	public Integer getNumeroTavolo() {
		return numeroTavolo;
	}

	@Override
	public void setNumeroTavolo(Integer numeroTavolo) {
		this.numeroTavolo = numeroTavolo;
	}

	@Override
	public Integer getGustoUno() {
		return gustoUno;
	}

	@Override
	public void setGustoUno(Integer gustoUno) {
		this.gustoUno = gustoUno;
	}

	@Override
	public Integer getGustoDue() {
		return gustoDue;
	}

	@Override
	public void setGustoDue(Integer gustoDue) {
		this.gustoDue = gustoDue;
	}

	@Override
	public Integer getGustoTre() {
		return gustoTre;
	}

	@Override
	public void setGustoTre(Integer gustoTre) {
		this.gustoTre = gustoTre;
	}

	@Override
	public Boolean getPanna() {
		return panna;
	}

	@Override
	public void setPanna(Boolean panna) {
		this.panna = panna;
	}

	@Override
	public Integer getTaglia() {
		return taglia;
	}

	@Override
	public void setTaglia(Integer taglia) {
		this.taglia = taglia;
	}

	@Override
	public Boolean getPagato() {
		return pagato;
	}

	@Override
	public void setPagato(Boolean pagato) {
		this.pagato = pagato;
	}

	private transient PoProperty<Gusti> toGusti1 = new PoLazyProperty<Gusti>();
	private transient PoProperty<Gusti> toGusti2 = new PoLazyProperty<Gusti>();
	private transient PoProperty<Gusti> toGusti3 = new PoLazyProperty<Gusti>();

	public Gusti getToGustiUno() {
		return toGusti1.getValue(DaoFactory.getGustiDao(), gustoUno);
	}

	public void setToGustiUno(Gusti toGusti) {
		this.toGusti1.setValue(toGusti);
		if (toGusti1!=null)
			gustoUno = toGusti.getIdGusto();
		else
			gustoUno = null;
	}

	public Gusti getToGustiDue() {
		return toGusti2.getValue(DaoFactory.getGustiDao(), gustoDue);
	}

	public void setToGustiDue(Gusti toGusti) {
		this.toGusti2.setValue(toGusti);
		if (toGusti2!=null)
			gustoDue = toGusti.getIdGusto();
		else
			gustoDue = null;
	}

	public Gusti getToGustiTre() {
		return toGusti3.getValue(DaoFactory.getGustiDao(), gustoTre);
	}

	public void setToGustiTre(Gusti toGusti) {
		this.toGusti3.setValue(toGusti);
		if (toGusti3!=null)
			gustoTre = toGusti.getIdGusto();
		else
			gustoTre = null;
	}

	private transient PoProperty<Taglia> toTaglia = new PoLazyProperty<Taglia>();

	public Taglia getToTaglia() {
		return toTaglia.getValue(DaoFactory.getTagliaDao(), taglia);
	}

	public void setToTaglia(Taglia toTaglia) {
		this.toTaglia.setValue(toTaglia);
		if (toTaglia!=null)
			taglia = toTaglia.getIdTaglia();
		else
			taglia = null;
	}

	@Override
	protected Tuple newKey() {
		return new Unit<Integer>(idGelato);
	}

	@Override
	public boolean isPagato() {
		return pagato;
	}

	public Boolean getFatto() {
		return fatto;
	}

	public void setFatto(Boolean fatto) {
		this.fatto = fatto;
	}

	@Override
	public String getDisplayLabel() {
		return getNumeroTavolo() + " " + getToTaglia().getDescrizione();
	}

}