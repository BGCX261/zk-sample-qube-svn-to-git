package it.qube.persistence.dto;

import java.util.List;
import it.mengoni.persistence.dto.PersistentObject;

public interface Taglia extends PersistentObject {
	public Integer getIdTaglia();

	public void setIdTaglia(Integer idTaglia);

	public String getDescrizione();

	public void setDescrizione(String desrizione);

	public Double getPrezzo();

	public void setPrezzo(Double prezzo);

	public List<Gelati> getListGelati();

	public void setListGelati(List<Gelati> toGelati);
}
