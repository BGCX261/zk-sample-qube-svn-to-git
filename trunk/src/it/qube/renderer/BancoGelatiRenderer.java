package it.qube.renderer;

import it.qube.persistence.dto.Gelati;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

public class BancoGelatiRenderer extends GenericRowRenderer {

	public void render(final Row row, final java.lang.Object data) {
		final Gelati g1 = (Gelati) data;
		intLabel(g1.getNumeroTavolo()).setParent(row);
		new Label(""+g1.getToTaglia().getDescrizione()).setParent(row);
		gustoLabel(g1.getToGustiUno()).setParent(row);
		gustoLabel(g1.getToGustiDue()).setParent(row);
		gustoLabel(g1.getToGustiTre()).setParent(row);
		Label l = siNoLabel(g1.getPanna());
		l.setStyle("text-align:center");
		l.setParent(row);

		Label y = siNoLabel(g1.getFatto());
		if(g1.getFatto()){
			y.setStyle("color:green;text-align:center");
		}else{
			y.setStyle("color:blue;text-align:center; cursor:pointer");
			y.addEventListener(Events.ON_CLICK, new FattoListener(g1, y));
		}
		y.setParent(row);

		Label x = siNoLabel(g1.getPagato());
		if(g1.isPagato()){
			x.setStyle("color:green;text-align:center");
		}else{
			x.setStyle("color:red;text-align:center");
		}
		x.setParent(row);
	}
}