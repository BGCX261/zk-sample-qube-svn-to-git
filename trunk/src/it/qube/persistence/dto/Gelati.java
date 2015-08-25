package it.qube.persistence.dto;

import it.mengoni.persistence.dto.PersistentObject;

public interface Gelati extends PersistentObject {
	public Integer getIdGelato();

	public void setIdGelato(Integer idGelato);

	public Integer getNumeroTavolo();

	public void setNumeroTavolo(Integer numeroTavolo);

	public Integer getGustoUno();

	public void setGustoUno(Integer gustoUno);

	public Integer getGustoDue();

	public void setGustoDue(Integer gustoDue);

	public Integer getGustoTre();

	public void setGustoTre(Integer gustoTre);

	public Boolean getPanna();

	public void setPanna(Boolean panna);

	public Integer getTaglia();

	public void setTaglia(Integer taglia);

	public Boolean getPagato();

	public void setPagato(Boolean pagato);

	public Gusti getToGustiUno();

	public void setToGustiUno(Gusti toGusti);

	public Gusti getToGustiDue();

	public void setToGustiDue(Gusti toGusti);

	public Gusti getToGustiTre();

	public void setToGustiTre(Gusti toGusti);

	public Taglia getToTaglia();

	public void setToTaglia(Taglia toTaglia);

	public boolean isPagato();

	public Boolean getFatto() ;

	public void setFatto(Boolean fatto);

}
