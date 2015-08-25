package it.qube.controller;

import java.io.IOException;

import it.qube.persistence.dto.Gusti;

import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

public class GustoWindow extends Window {

	private static final long serialVersionUID = 1L;
	private Gusti gusto;

	public GustoWindow() {
	}

	public GustoWindow(String title, String border, boolean closable) {
		super(title, border, closable);
	}

	public Gusti getGusto() {
		return gusto;
	}

	public void setGusto(Gusti gusto) throws IOException {
		this.gusto = gusto;
		getChildren().clear();
		if (gusto!=null){
			if (gusto.getImage()!=null){
				Image imageContent = new AImage(gusto.getGusto(), gusto.getImage());
				org.zkoss.zul.Image image = new org.zkoss.zul.Image();
				image.setContent(imageContent);
				image.setTooltiptext(gusto.getGusto());
				appendChild(image);
			} else {
				Label label = new Label(gusto.getGusto());
				label.setTooltiptext(gusto.getDescrizione());
				appendChild(label);
			}
		}
		invalidate();
	}

}
