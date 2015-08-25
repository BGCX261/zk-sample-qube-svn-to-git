package it.qube.renderer;


import it.qube.persistence.dto.Taglia;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class ComboTagliaRenderer implements ComboitemRenderer {

	@Override
    public void render(final Comboitem item,final Object data)
    	throws Exception {
		Taglia g =(Taglia) data;
    	item.setLabel(g.getDescrizione());
    	item.setDescription("€: " + g.getPrezzo().toString());
    	item.setValue(g);
		 //item.setImage("info.png");
	 }

}
