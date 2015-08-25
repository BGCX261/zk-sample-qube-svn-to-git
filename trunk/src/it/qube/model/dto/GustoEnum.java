package it.qube.model.dto;



public enum GustoEnum {
	
	NESSUNO(""),
	PERA("pera"),
	ARANCIO("arancio"),
	LIMONE("limone"),
	PESCA("pesca"),
	MIRTILLO("mirtillo"),
	LAMPONE("lampone"),
	FRAGOLA("fragola");
	
    private final String codice;

    GustoEnum(String codice) {
        this.codice = codice;
    }
	
	
	public String getCodice(){
		return this.codice;
	}
	
}
