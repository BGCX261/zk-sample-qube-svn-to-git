package it.qube.controller;

import it.mengoni.exception.LogicException;
import it.mengoni.exception.SystemError;
import it.qube.persistence.DaoFactory;
import it.qube.persistence.dao.GelatiDao;
import it.qube.persistence.dto.Gelati;
import it.qube.renderer.GelatiRenderer;

import java.math.BigDecimal;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class PopupCassaController extends GenericController {

	/**
	 *
	 */
	private static final long serialVersionUID = 611138954254029396L;
	private Grid gelatiCassaGrid;
	private Textbox totale;
	private Groupbox groupboxTotale;
	private Integer numeroTavolo;
	private Window popupTotali;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		groupboxTotale.setVisible(false);

	}

	public void calcola(Integer i) throws Exception {
		numeroTavolo = i;
		popupTotali.setTitle("Tavolo n° " + numeroTavolo);
		List<Gelati> gelatiPerTavolo = DaoFactory.getGelatiDao().getByTavoloDaPagare(numeroTavolo);
		if (gelatiPerTavolo != null) {
			BigDecimal somma = new BigDecimal(0);
			for (Gelati gelato : gelatiPerTavolo) {
				if(!gelato.isPagato() ){
					somma = somma.add(new BigDecimal(gelato.getToTaglia().getPrezzo()));
				}
			}
			ListModel listModel = new ListModelList(gelatiPerTavolo);
			gelatiCassaGrid.setRowRenderer(new GelatiRenderer());
			gelatiCassaGrid.setModel(listModel);
			if(gelatiPerTavolo.isEmpty()){
				//TODO... capire perchè funziona solo se cerco per un tavolo presente e poi uno non presente
				gelatiCassaGrid.setEmptyMessage("Non sono presenti gelati da pagare per questo tavolo");
				groupboxTotale.setVisible(false);
			} else {
				totale.setReadonly(true);
				totale.setValue(somma.toString());
				groupboxTotale.setVisible(true);
			}
		}

	}

	public void onClick$pagato(Event event) throws SystemError, LogicException{
		GelatiDao dao = DaoFactory.getGelatiDao();
		List<Gelati> elencoGelati = dao.getByTavoloDaPagare(numeroTavolo);
		if (elencoGelati != null) {
			for (Gelati gelato : elencoGelati) {
				gelato.setPagato(true);
				dao.update(gelato);
			}
		}
		popupTotali.detach();
	}
}
