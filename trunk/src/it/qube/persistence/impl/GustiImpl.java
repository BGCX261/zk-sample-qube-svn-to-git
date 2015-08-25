package it.qube.persistence.impl;

import it.mengoni.persistence.dao.Condition;
import org.javatuples.Tuple;
import java.util.List;
import it.qube.persistence.dto.Gusti;
import it.qube.persistence.dto.Gelati;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import org.javatuples.Unit;
import it.qube.persistence.DaoFactory;
import it.mengoni.persistence.dto.PoProperties;
import it.mengoni.persistence.dto.PoLazyProperties;

public class GustiImpl extends AbstractPersistentObject implements Gusti {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer idGusto;
	private String gusto;
	private String descrizione;
	private Boolean disponibile;
	private byte[] image;

	public GustiImpl() {
	}

	@Override
	public Integer getIdGusto() {
		return idGusto;
	}

	@Override
	public void setIdGusto(Integer idGusto) {
		this.idGusto = idGusto;
	}

	@Override
	public String getGusto() {
		return gusto;
	}

	@Override
	public void setGusto(String gusto) {
		this.gusto = gusto;
	}

	@Override
	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public Boolean getDisponibile() {
		return disponibile;
	}

	@Override
	public void setDisponibile(Boolean disponibile) {
		this.disponibile = disponibile;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	private transient PoProperties<Gelati> listGelati = new PoLazyProperties<Gelati>();

	public List<Gelati> getListGelati() {
		return listGelati.getValue(DaoFactory.getGelatiDao(), new Condition("GUSTO_UNO = ?", idGusto));
	}

	public void setListGelati(List<Gelati> listGelati) {
		this.listGelati.setValue(listGelati);
	}

	@Override
	protected Tuple newKey() {
		return new Unit<Integer>(idGusto);
	}

	@Override
	public String getDisplayLabel() {
		return getDescrizione();
	}
}
