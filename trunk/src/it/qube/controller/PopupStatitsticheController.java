package it.qube.controller;

import it.qube.persistence.DaoFactory;
import it.qube.persistence.dto.Gelati;
import it.qube.persistence.dto.Gusti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Chart;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimplePieModel;


public class PopupStatitsticheController extends GenericController {

	private static final long serialVersionUID = 611138954254029496L;
	private Chart mychart;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		List<Gelati> elencoGelati = DaoFactory.getGelatiDao().getAll();
		PieModel model = new SimplePieModel();
		Map<Gusti, Integer> modello = new HashMap<Gusti, Integer>();
		for(Gelati gelato: elencoGelati) {
			addToModel(modello, gelato.getToGustiUno());
			addToModel(modello, gelato.getToGustiDue());
			addToModel(modello, gelato.getToGustiTre());
		}
		Set<Gusti> keySet = modello.keySet();
		for(Gusti key: keySet){
			if (key.getGusto()!=null)
				model.setValue(key.getGusto(), modello.get(key));
		}
        mychart.setModel(model);
        mychart.setVisible(true);
	}

	private void addToModel(Map<Gusti, Integer> modello, Gusti gusto){
		if (gusto==null)
			return;
		if (modello.get(gusto) == null) {
			modello.put(gusto, 1);
		} else {
			modello.put(gusto, modello.get(gusto) + 1);
		}

	}
}
