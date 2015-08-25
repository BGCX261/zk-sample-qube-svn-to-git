package it.qube.persistence.dto;

import java.util.List;
import it.mengoni.persistence.dto.PersistentObject;

public interface Gusti extends PersistentObject {
	public Integer getIdGusto();

	public void setIdGusto(Integer idGusto);

	public String getGusto();

	public void setGusto(String gusto);

	public String getDescrizione();

	public void setDescrizione(String descrizione);

	public Boolean getDisponibile();

	public void setDisponibile(Boolean disponibile);

	public List<Gelati> getListGelati();

	public void setListGelati(List<Gelati> toGelati);

	public byte[] getImage();

	public void setImage(byte[] image);

}
