package it.qube.model.dto;

import java.math.BigDecimal;

public enum TagliaEnum {
	
	VASCHETTA_PICCOLA("Vaschetta piccola", new BigDecimal(4)),
	VASCHETTA_MEDIA("Vaschetta media", new BigDecimal(6)),
	VASCHETTA_GRANDE("Vaschetta grande", new BigDecimal(8)),
	
	COPPA_PICCOLA("Coppa piccola", new BigDecimal(1.5)),
	COPPA_MEDIA("Coppa media", new BigDecimal(2)),
	COPPA_GRANDE("Coppa grande", new BigDecimal(3.5)),
	
	CONO_PICCOLO("Cono piccolo", new BigDecimal(1)),
	CONO_MEDIO("Cono medio", new BigDecimal(1.5)),
	CONO_GRANDE("Cono grande", new BigDecimal(2.5));
	
	
    private final String descrizione;
    private final BigDecimal prezzo;

    TagliaEnum(String descrizione, BigDecimal prezzo) {
        this.descrizione = descrizione;
        this.prezzo = prezzo;
    }
	
	public String getDimensione(){
		return this.descrizione;
	}
	
	public BigDecimal getPrezzo(){
		return this.prezzo;
	}
	
}
