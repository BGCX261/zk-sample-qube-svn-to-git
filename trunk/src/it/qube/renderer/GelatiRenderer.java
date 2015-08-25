package it.qube.renderer;


import it.qube.persistence.dto.Gelati;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

public class GelatiRenderer extends GenericRowRenderer {

    public void render(final Row row, final java.lang.Object data) {
    	Gelati gelato = (Gelati) data;
		gustoLabel(gelato.getToGustiUno()).setParent(row);
		gustoLabel(gelato.getToGustiDue()).setParent(row);
		gustoLabel(gelato.getToGustiTre()).setParent(row);
        new Label(gelato.getToTaglia().getPrezzo().toString()).setParent(row);
    }

}
