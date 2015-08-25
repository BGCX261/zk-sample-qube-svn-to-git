package it.qube.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GelatoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7497256077254964250L;
	
	private Long idGelato;
	private Integer numeroTavolo;
	private GustoEnum gustoUno;
	private GustoEnum gustoDue;
	private GustoEnum gustoTre;
	private Boolean panna;
	private TagliaEnum taglia;
	private Boolean pagato;
	
	
	public GelatoDTO() {
		this.idGelato = new Date().getTime(); 
		pagato = false;
	}
	
	
	public Long getIdGelato() {
		return idGelato;
	}

	public Integer getNumeroTavolo() {
		return numeroTavolo;
	}
	public void setNumeroTavolo(Integer numeroTavolo) {
		this.numeroTavolo = numeroTavolo;
	}
	public GustoEnum getGustoUno() {
		return gustoUno;
	}
	public void setGustoUno(GustoEnum gustoUno) {
		this.gustoUno = gustoUno;
	}
	public GustoEnum getGustoDue() {
		return gustoDue;
	}
	public void setGustoDue(GustoEnum gustoDue) {
		this.gustoDue = gustoDue;
	}
	public GustoEnum getGustoTre() {
		return gustoTre;
	}
	public void setGustoTre(GustoEnum gustoTre) {
		this.gustoTre = gustoTre;
	}
	public Boolean getPanna() {
		return panna;
	}
	public void setPanna(Boolean panna) {
		this.panna = panna;
	}
	public BigDecimal getPrezzo() {		
		BigDecimal p = BigDecimal.ZERO;
		if(panna){
			p = p.add(BigDecimal.ONE);
		}
		p = p.add(getTaglia().getPrezzo());
		return p;
	}
	public TagliaEnum getTaglia() {
		return taglia;
	}
	public void setTaglia(TagliaEnum taglia) {
		this.taglia = taglia;
	}


	public Boolean getPagato() {
		return pagato;
	}


	public void setPagato(Boolean pagato) {
		this.pagato = pagato;
	}
	
	

}
