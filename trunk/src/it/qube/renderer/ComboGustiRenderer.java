package it.qube.renderer;


import it.qube.persistence.dto.Gusti;

import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class ComboGustiRenderer implements ComboitemRenderer {

	@Override
    public void render(final Comboitem item,final Object data)
    	throws Exception {
		Gusti g =(Gusti) data;
    	item.setLabel(g.getGusto());
    	item.setTooltiptext(g.getDescrizione());
    	item.setValue(g);
    	if (g.getImage()!=null){
    		Image image = new AImage(g.getGusto(), g.getImage());
    		item.setImageContent(image);
    	}
	 }

}
