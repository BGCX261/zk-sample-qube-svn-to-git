package it.qube.renderer;

import it.qube.persistence.DaoFactory;
import it.qube.persistence.dto.Gelati;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Label;

public class FattoListener implements EventListener{

	private Gelati gelato;
	private Label label;

	public FattoListener(Gelati gelato, Label label) {
		super();
		this.gelato = gelato;
		this.label = label;
	}

	@Override
	public void onEvent(Event arg0) throws Exception {
		gelato.setFatto(true);
		DaoFactory.getGelatiDao().update(gelato);
		label.setValue("SI");
		label.setStyle("color:green;text-align:center");
	}

}