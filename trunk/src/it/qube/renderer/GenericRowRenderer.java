package it.qube.renderer;

import it.qube.persistence.dto.Gusti;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

public class GenericRowRenderer implements RowRenderer{

	public void render(Row row, Object data) throws Exception {

	}

	protected Label gustoLabel(Gusti gusto){
		if (gusto!=null)
			return new Label(""+gusto.getGusto());
		return new Label("");
	}

	protected Label siNoLabel(Boolean value){
		if (value!=null && value.booleanValue())
			return new Label("SI");
		return new Label("NO");
	}

	protected Label intLabel(Integer value){
		if (value!=null)
			return new Label(""+value);
		return new Label("");
	}

}
